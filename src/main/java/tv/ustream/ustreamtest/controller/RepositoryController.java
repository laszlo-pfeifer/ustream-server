/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tv.ustream.ustreamtest.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import tv.ustream.ustreamtest.exception.AlreadyExistException;
import tv.ustream.ustreamtest.exception.NotExistException;
import tv.ustream.ustreamtest.model.Repository;
import tv.ustream.ustreamtest.service.RepositoryService;

/**
 *
 * @author pfeiferlaszlo
 */

@RestController()
@RequestMapping("/repository")
public class RepositoryController {
    
    @Autowired
    private RepositoryService repositoryService;
    
    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<Repository> createRepository(@RequestBody CreateRepositoryDTO repositoryDTO) {
        Repository repository = repositoryService.createRepository(repositoryDTO.getName(), repositoryDTO.getCreator());
        return ResponseEntity.ok().body(repository);
    }
    
    @RequestMapping(path = "/{name}", method = RequestMethod.DELETE)
    public ResponseEntity<Void> deleteRepository(@PathVariable String name) {
        repositoryService.deleteRepository(name);
        return ResponseEntity.ok().build();
    }
    
    @RequestMapping(path = "/{name}", method = RequestMethod.GET)
    public ResponseEntity<Repository> getRepository(@PathVariable String name) {
        Repository repository = repositoryService.getRepository(name);
        return ResponseEntity.ok().body(repository);
    }
    
    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<List<Repository>> getRepositories(@RequestParam int threshold) {
        List<Repository> repositories = repositoryService.getRepositories(threshold);
        return ResponseEntity.ok().body(repositories);
    }
    
    @ExceptionHandler(NotExistException.class)
    @ResponseStatus(code = HttpStatus.BAD_REQUEST, reason = "There are no repository with this name!")
    public void notExistExceptionHandler() {
    }
    
    @ExceptionHandler(AlreadyExistException.class)
    @ResponseStatus(code = HttpStatus.BAD_REQUEST, reason = "There is already a repository with this name!")
    public void alreadyExistExceptionHandler() {
    }
    
    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(code = HttpStatus.BAD_REQUEST, reason = "Name and creator cannot be null or empty!")
    public void illegalArgumentExceptionHandler() {
    }
}
