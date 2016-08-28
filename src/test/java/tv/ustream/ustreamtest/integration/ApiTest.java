/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tv.ustream.ustreamtest.integration;

import org.junit.Before;
import org.junit.Test;

/**
 *
 * @author pfeiferlaszlo
 */
public class ApiTest {
    
    private static final String TEST_NAME_NOT_EXISTING = "test-name-not-existing";
    private static final String NAME_REPO_1 = "repo-1";
    private static final String NAME_REPO_2 = "repo-2";
    private static final String NAME_REPO_3 = "repo-3";
    private static final String AUTHOR = "user-1";
    
    private RepositoryApi repositoryApi;
    
    @Before
    public void setUp() {
        repositoryApi = new RepositoryApi();   
    }
    
    @Test
    public void testGetRepositoryWhenNotExist() throws Exception {
        repositoryApi.getRepositoryWhenNotExist(TEST_NAME_NOT_EXISTING);
    }
    
    @Test
    public void testCreateAndDeleteRepository() throws Exception {
        repositoryApi.createRepository(NAME_REPO_1, AUTHOR);
        repositoryApi.deleteRepositroy(NAME_REPO_1);
    }
    
    @Test
    public void NotExist() throws Exception {
        repositoryApi.deleteRepositroyNotExist(NAME_REPO_1);
    }
    
    @Test
    public void testCreateRepositoryAlreadyExist() throws Exception {
        repositoryApi.createRepository(NAME_REPO_1, AUTHOR);
        repositoryApi.createRepositoryAlreadyExist(NAME_REPO_1, AUTHOR);
        repositoryApi.deleteRepositroy(NAME_REPO_1);
    }
    
    @Test
    public void testGetRepository() throws Exception {
        repositoryApi.createRepository(NAME_REPO_2, AUTHOR);
        repositoryApi.getRepository(NAME_REPO_2);
        repositoryApi.deleteRepositroy(NAME_REPO_2);
    }
    
    @Test
    public void testGetRepositories() throws Exception {
        repositoryApi.createRepository(NAME_REPO_3, AUTHOR);
        repositoryApi.getRepository(NAME_REPO_3);
        repositoryApi.getRepository(NAME_REPO_3);
        repositoryApi.getRepositories(1);
        repositoryApi.deleteRepositroy(NAME_REPO_3);
    }
   
}
