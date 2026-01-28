package com.practice.onlinedonation.Exception;

import org.springframework.scheduling.support.SimpleTriggerContext;

public class ResourceNotFoundException extends RuntimeException{
    String resourceName;
    String field;
    String fieldName;
    Long id;
    public ResourceNotFoundException(String resourceName, String field, long id ){
        super(String.format("%s not found with %s: %d" , resourceName,field,id));
        this.resourceName = resourceName;
        this.field = field;
        this.id = id;
    }


}
