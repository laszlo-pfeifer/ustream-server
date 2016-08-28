/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tv.ustream.ustreamtest.controller;

import java.util.ArrayList;
import java.util.List;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.hasSize;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import tv.ustream.ustreamtest.model.Repository;
import tv.ustream.ustreamtest.service.RepositoryService;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 *
 * @author pfeiferlaszlo
 */
public class RepositoryControllerTest {
    
    private static final String AUTHOR_NAME = "test-creator";
    private static final String REPOSITORY_NAME = "test-name";
    private static final String RETURN_NAME = "return-name";
    
    private MockMvc mockMvc;
    
    @Mock
    private RepositoryService repositoryService;
    
    @InjectMocks
    private RepositoryController repositoryController;
    
    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(repositoryController).build();
    }

    @Test
    public void testCreateRepository() throws Exception {
        Repository repository = new Repository();
        repository.setName(RETURN_NAME);
        Mockito.when(repositoryService.createRepository(REPOSITORY_NAME, AUTHOR_NAME)).thenReturn(repository);
        
        // Send request
        mockMvc.perform(post("/repository").content("{\"name\":\"" + REPOSITORY_NAME + "\",\"creator\":\"" + AUTHOR_NAME + "\"}").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is(RETURN_NAME)));
        
        Mockito.verify(repositoryService).createRepository(REPOSITORY_NAME, AUTHOR_NAME);
    }

    @Test
    public void testDeleteRepository() throws Exception {
        // Send request
        mockMvc.perform(delete("/repository/" + REPOSITORY_NAME))
                .andExpect(status().isOk());
        
        Mockito.verify(repositoryService).deleteRepository(REPOSITORY_NAME);
    }

    @Test
    public void testGetRepository() throws Exception {
        Repository repository = new Repository();
        repository.setName(RETURN_NAME);
        Mockito.when(repositoryService.getRepository(REPOSITORY_NAME)).thenReturn(repository);
        
        // Send request
        mockMvc.perform(get("/repository/" + REPOSITORY_NAME))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is(RETURN_NAME)));
        
        Mockito.verify(repositoryService).getRepository(REPOSITORY_NAME);
    }

    @Test
    public void testGetRepositories() throws Exception {
        int threshold = 0;
        Repository repository = new Repository();
        repository.setName(RETURN_NAME);
        List<Repository> repositories = new ArrayList<>();
        repositories.add(repository);
        Mockito.when(repositoryService.getRepositories(threshold)).thenReturn(repositories);
        
        // Send request
        mockMvc.perform(get("/repository?threshold=" + threshold))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].name", is(RETURN_NAME)));
                
        Mockito.verify(repositoryService).getRepositories(threshold);
    }
    
}
