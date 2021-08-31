package com.stackroute.orderservice.model.product;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.Binary;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Photo {

    private String title;

    private String type;

    private Binary image;
}