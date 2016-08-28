/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tv.ustream.ustreamtest.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Date;

/**
 *
 * @author pfeiferlaszlo
 */

@JsonIgnoreProperties(ignoreUnknown = true)
public class Repository {
    private String name;
    @JsonProperty(value = "creation_date")
    private Date creationDate;
    private String creator;
    @JsonProperty(value = "access_counter")
    private int accessCounter;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public int getAccessCounter() {
        return accessCounter;
    }

    public void setAccessCounter(int accessCounter) {
        this.accessCounter = accessCounter;
    }
    
}
