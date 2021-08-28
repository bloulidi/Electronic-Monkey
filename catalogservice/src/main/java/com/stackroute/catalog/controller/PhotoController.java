package com.stackroute.catalog.controller;

import com.stackroute.catalog.model.Photo;
import com.stackroute.catalog.model.Product;
import com.stackroute.catalog.service.PhotoService;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Base64;

@Slf4j
@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping(path = "photos")
public class PhotoController {
/*
    @Autowired
    private PhotoService photoService;

    @GetMapping("{id}")
    public ResponseEntity<String> getPhoto(@PathVariable String id) {
        Photo photo = photoService.getPhoto(id);
        log.info("Get photo: id = " + photo.getId() + ", title = " + photo.getTitle());
        JSONObject item = new JSONObject();
        item.put("image", Base64.getEncoder().encodeToString(photo.getImage().getData()));
        return new ResponseEntity<String>(item.toString(), HttpStatus.OK);
    }

    @PostMapping("add")
    public ResponseEntity<String> addPhoto(@RequestParam("image") MultipartFile image) throws IOException {
        String id = photoService.addPhoto(image.getOriginalFilename(), image);
        log.info("Add photo: id = " + id + ", filename = " + image.getOriginalFilename());
        JSONObject item = new JSONObject();
        item.put("id", id);
        item.put("filename", image.getOriginalFilename());
        return new ResponseEntity<String>(item.toString(), HttpStatus.OK);
    }*/
}