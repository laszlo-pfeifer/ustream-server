/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tv.ustream.ustreamtest.service;

import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tv.ustream.ustreamtest.exception.AlreadyExistException;
import tv.ustream.ustreamtest.exception.NotExistException;
import tv.ustream.ustreamtest.repository.RepositoryPersistence;
import tv.ustream.ustreamtest.model.Repository;

/**
 *
 * @author pfeiferlaszlo
 */

@Service
public class RepositoryServiceImpl implements RepositoryService{
    
    @Autowired
    private RepositoryPersistence repositoryPersistence;

    @Override
    public Repository createRepository(String name, String creator) {
        if (name == null || name.isEmpty() || creator == null || creator.isEmpty()) {
            throw new IllegalArgumentException();
        }
        Repository existingRepository = repositoryPersistence.get(name);
        if (existingRepository != null) {
            throw new AlreadyExistException();
        }
        Repository repository = new Repository();
        repository.setAccessCounter(0);
        repository.setCreationDate(new Date());
        repository.setName(name);
        repository.setCreator(creator);
        repositoryPersistence.save(repository);
        return repository;
    }

    @Override
    public void deleteRepository(String name) {
        if (name == null || name.isEmpty()) {
            throw new IllegalArgumentException();
        }
        Repository repository = repositoryPersistence.get(name);
        if (repository == null) {
            throw new NotExistException();
        }
        repositoryPersistence.delete(name);
    }

    @Override
    public Repository getRepository(String name) {
        if (name == null || name.isEmpty()) {
            throw new IllegalArgumentException();
        }
        Repository repository = repositoryPersistence.get(name);
        if (repository == null) {
            throw new NotExistException();
        }
        int increasedViewCount = repository.getAccessCounter() + 1;
        repository.setAccessCounter(increasedViewCount);
        repositoryPersistence.save(repository);
        return repository;
    }

    @Override
    public List<Repository> getRepositories(int threshold) {
        if (threshold < 0) {
            throw new IllegalArgumentException();
        }
        return repositoryPersistence.getRepositoriesAboveThreshold(threshold);
    }
    
}
