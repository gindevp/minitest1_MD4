package com.codegym.model;

import org.springframework.web.multipart.MultipartFile;

public class UploadProduct {
    private Integer id;
    private String name;
    private Integer price;
    private MultipartFile img;

    public UploadProduct() {
    }

    public UploadProduct(Integer id, String name, Integer price, MultipartFile img) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.img = img;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public MultipartFile getImg() {
        return img;
    }

    public void setImg(MultipartFile img) {
        this.img = img;
    }
}
