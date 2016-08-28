/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tv.ustream.ustreamtest.repository;

import java.util.List;
import tv.ustream.ustreamtest.model.Repository;

/**
 *
 * @author pfeiferlaszlo
 */
public interface RepositoryPersistence {

    public void save(Repository repository);

    public void delete(String name);

    public Repository get(String name);

    public List<Repository> getRepositoriesAboveThreshold(int threshold);
    
}
