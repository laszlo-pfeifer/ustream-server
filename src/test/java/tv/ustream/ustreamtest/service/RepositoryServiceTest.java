/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tv.ustream.ustreamtest.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import org.mockito.InjectMocks;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import tv.ustream.ustreamtest.exception.AlreadyExistException;
import tv.ustream.ustreamtest.exception.NotExistException;
import tv.ustream.ustreamtest.model.Repository;
import tv.ustream.ustreamtest.repository.RepositoryPersistence;

/**
 *
 * @author pfeiferlaszlo
 */
public class RepositoryServiceTest {
    
    private static final String AUTHOR_NAME = "test-creator";
    private static final String REPOSITORY_NAME = "test-name";
    private static final String RETURN_NAME = "return-name";
    
    @Mock
    private RepositoryPersistence repositoryPersistence;
    
    @InjectMocks
    private RepositoryService underTest;
    
    @Before
    public void setUp() {
        underTest = new RepositoryServiceImpl();
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testCreateRepository() {
        // GIVEN
        Mockito.when(repositoryPersistence.get(REPOSITORY_NAME)).thenReturn(null);
        // WHEN
        Repository result = underTest.createRepository(REPOSITORY_NAME, AUTHOR_NAME);
        // THEN
        Mockito.verify(repositoryPersistence).get(REPOSITORY_NAME);
        Mockito.verify(repositoryPersistence).save(Matchers.any(Repository.class));
        assertEquals(REPOSITORY_NAME, result.getName());
        assertEquals(AUTHOR_NAME, result.getCreator());
        assertEquals(0, result.getAccessCounter());
        assertNotNull(result.getCreationDate());
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void testCreateRepositoryWhenNameIsNull() {
        // WHEN
        Repository result = underTest.createRepository(null, AUTHOR_NAME);
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void testCreateRepositoryWhenNameIsEmpty() {
        // WHEN
        Repository result = underTest.createRepository("", AUTHOR_NAME);
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void testCreateRepositoryWhenAuthorIsNull() {
        // WHEN
        Repository result = underTest.createRepository(REPOSITORY_NAME, null);
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void testCreateRepositoryWhenAuthorIsEmpty() {
        // WHEN
        Repository result = underTest.createRepository(REPOSITORY_NAME, "");
    }
    
    @Test(expected = AlreadyExistException.class)
    public void testCreateRepositoryWhenNameAlreadyExist() {
        // GIVEN
        Repository repository = new Repository();
        repository.setName(RETURN_NAME);
        repository.setCreator(AUTHOR_NAME);
        Mockito.when(repositoryPersistence.get(REPOSITORY_NAME)).thenReturn(repository);
        // WHEN
        Repository result = underTest.createRepository(REPOSITORY_NAME, AUTHOR_NAME);
        // THEN
        Mockito.verify(repositoryPersistence).get(REPOSITORY_NAME);
    }

    @Test
    public void testGetRepository() {
        // GIVEN
        Date now = new Date();
        Repository repository = new Repository();
        repository.setName(RETURN_NAME);
        repository.setCreator(AUTHOR_NAME);
        repository.setAccessCounter(2);
        repository.setCreationDate(now);
        Mockito.when(repositoryPersistence.get(REPOSITORY_NAME)).thenReturn(repository);
        // WHEN
        Repository result = underTest.getRepository(REPOSITORY_NAME);
        // THEN
        Mockito.verify(repositoryPersistence).get(REPOSITORY_NAME);
        Mockito.verify(repositoryPersistence).save(repository);
        assertEquals(RETURN_NAME, result.getName());
        assertEquals(AUTHOR_NAME, result.getCreator());
        assertEquals(3, result.getAccessCounter());
        assertEquals(now, result.getCreationDate());
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void testGetRepositoryWhenNameIsNull() {
        // WHEN
        Repository result = underTest.getRepository(null);
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void testGetRepositoryWhenNameIsEmpty() {
        // WHEN
        Repository result = underTest.getRepository("");
    }

    @Test(expected = NotExistException.class)
    public void testGetRepositoryWhenRepoIsNotExist() {
        // GIVEN
        Repository repository = new Repository();
        repository.setName(RETURN_NAME);
        repository.setCreator(AUTHOR_NAME);
        Mockito.when(repositoryPersistence.get(REPOSITORY_NAME)).thenReturn(null);
        // WHEN
        Repository result = underTest.getRepository(REPOSITORY_NAME);
        // THEN
        Mockito.verify(repositoryPersistence).get(REPOSITORY_NAME);
    }
    
    @Test
    public void testDeleteRepository() {
        // GIVEN
        Repository repository = new Repository();
        repository.setName(RETURN_NAME);
        Mockito.when(repositoryPersistence.get(REPOSITORY_NAME)).thenReturn(repository);
        // WHEN
        underTest.deleteRepository(REPOSITORY_NAME);
        // THEN
        Mockito.verify(repositoryPersistence).get(REPOSITORY_NAME);
        Mockito.verify(repositoryPersistence).delete(REPOSITORY_NAME);
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void testDeleteRepositoryWhenNameIsNull() {
        // GIVEN
        // WHEN
        underTest.deleteRepository(null);
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void testDeleteRepositoryWhenNameIsEmpty() {
        // WHEN
        underTest.deleteRepository("");
    }
    
    @Test(expected = NotExistException.class)
    public void testDeleteRepositoryWhenRepoIsNotExist() {
        // GIVEN
        Repository repository = new Repository();
        repository.setName(RETURN_NAME);
        Mockito.when(repositoryPersistence.get(REPOSITORY_NAME)).thenReturn(null);
        // WHEN
        underTest.deleteRepository(REPOSITORY_NAME);
        // THEN
        Mockito.verify(repositoryPersistence).get(REPOSITORY_NAME);
    }

    @Test
    public void testGetRepositories() {
        // GIVEN
        int threshold = 0;
        Date now = new Date();
        Repository repository = new Repository();
        repository.setName(RETURN_NAME);
        repository.setCreator(AUTHOR_NAME);
        repository.setAccessCounter(2);
        repository.setCreationDate(now);
        List<Repository> repositories = new ArrayList<>();
        repositories.add(repository);
        Mockito.when(repositoryPersistence.getRepositoriesAboveThreshold(threshold)).thenReturn(repositories);
        // WHEN
        List<Repository> result = underTest.getRepositories(threshold);
        // THEN
        Mockito.verify(repositoryPersistence).getRepositoriesAboveThreshold(threshold);
        assertEquals(1, result.size());
        assertEquals(RETURN_NAME, result.get(0).getName());
        assertEquals(AUTHOR_NAME, result.get(0).getCreator());
        assertEquals(2, result.get(0).getAccessCounter());
        assertEquals(now, result.get(0).getCreationDate());
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void testGetRepositoriesWhenThresholdIsSmallerThanZero() {
        // GIVEN
        int threshold = -1;
        // WHEN
        List<Repository> result = underTest.getRepositories(threshold);
    }
    
}
