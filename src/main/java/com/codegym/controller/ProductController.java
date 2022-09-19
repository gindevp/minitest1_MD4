package com.codegym.controller;

import com.codegym.model.Product;
import com.codegym.model.UploadProduct;
import com.codegym.service.IProductSevice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.io.File;
import java.io.IOException;
import java.util.List;

@Controller

public class ProductController {
    @Value("${file-upload}")
    private String fileUpload;
    @Autowired
    private IProductSevice productSevice;
    @GetMapping("/products")
    public ModelAndView listProduct(){
        List<Product> products= productSevice.findAll();
        ModelAndView modelAndView= new ModelAndView("/product/list");
        modelAndView.addObject("products", products);
        return  modelAndView;
    }
    @GetMapping("/create")
    public ModelAndView showCreateForm(){
        ModelAndView modelAndView= new ModelAndView("/product/create");
        modelAndView.addObject("product", new Product());
        return modelAndView;
    }
    @PostMapping("/create")
    public ModelAndView createProduct(@ModelAttribute UploadProduct uploadProduct){
        MultipartFile multipartFile= uploadProduct.getImg();
        String filename= multipartFile.getOriginalFilename();
        try{
            FileCopyUtils.copy(uploadProduct.getImg().getBytes(), new File(fileUpload+ filename));
            System.out.println("thanh cong");
        } catch (IOException e) {
            e.printStackTrace();
        }
        Product product= new Product(uploadProduct.getId(),uploadProduct.getName(),uploadProduct.getPrice(),filename);
        ModelAndView modelAndView= new ModelAndView("/product/create");
        modelAndView.addObject("product",new Product());
        productSevice.save(product);
        return modelAndView;
    }
    @GetMapping("/detail/{id}")
    public ModelAndView showDetail(@PathVariable int id){
        ModelAndView modelAndView= new ModelAndView("/product/detail");
        Product  product = productSevice.findById(id);
        modelAndView.addObject("product", product);
        return modelAndView;
    }
    @GetMapping("/update/{id}")
    public ModelAndView showFormUpdate(@PathVariable int id){
        ModelAndView modelAndView = new ModelAndView("/product/update");
        Product product = productSevice.findById(id);
        modelAndView.addObject("product", product);
        return modelAndView;
    }

    @PostMapping("/update/{id}")
    public ModelAndView updateProduct(@ModelAttribute UploadProduct productForm){
        MultipartFile multipartFile = productForm.getImg();
        String fileName= multipartFile.getOriginalFilename();
        try{
            FileCopyUtils.copy(productForm.getImg().getBytes(), new File(fileUpload+ fileName));
        }catch (IOException e){
            e.printStackTrace();
        }
        Product product = new Product(productForm.getId(), productForm.getName(),productForm.getPrice(),fileName);
        ModelAndView modelAndView =new ModelAndView("/product/update");
        modelAndView.addObject("product", new Product());
        productSevice.save(product);
        return modelAndView;
    }
    @GetMapping("/delete/{id}")
    public ModelAndView showDeleteForm(@PathVariable int id) {
        productSevice.remove(id);
        List<Product> productList = productSevice.findAll();
        ModelAndView modelAndView = new ModelAndView("/product/list");
        modelAndView.addObject("products",productList);
        return modelAndView;
    }
}
