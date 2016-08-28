/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tv.ustream.ustreamtest.service;

import java.util.List;
import tv.ustream.ustreamtest.model.Repository;

/**
 *
 * @author pfeiferlaszlo
 */
public interface RepositoryService {

    public Repository createRepository(String name, String creator);

    public void deleteRepository(String name);

    public Repository getRepository(String name);

    public List<Repository> getRepositories(int threshold);
    
}
