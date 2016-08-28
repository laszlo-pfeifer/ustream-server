/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tv.ustream.ustreamtest.repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import tv.ustream.ustreamtest.model.Repository;

/**
 *
 * @author pfeiferlaszlo
 */

@org.springframework.stereotype.Repository
public class ReposityPersistanceImpl implements RepositoryPersistence {
    
    private final Map<String, Repository> repositories = new HashMap<>();

    @Override
    public void save(Repository repository) {
        repositories.put(repository.getName(), repository);
    }

    @Override
    public void delete(String name) {
        repositories.remove(name);
    }

    @Override
    public Repository get(String name) {
        return repositories.get(name);
    }

    @Override
    public List<Repository> getRepositoriesAboveThreshold(int threshold) {
        return repositories.values().stream().filter((Repository r) -> {
            return r.getAccessCounter() > threshold;
        }).collect(Collectors.toList());
    }
    
}
