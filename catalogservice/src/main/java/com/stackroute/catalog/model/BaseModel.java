package com.stackroute.catalog.model;

import java.util.Date;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;

@Setter
@Getter
public abstract class BaseModel {

    @Id
    protected String id;

    protected Date createdAt;

    protected Date updatedAt;
}