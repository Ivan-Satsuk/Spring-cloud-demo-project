package com.geekbrains.demoboot.controllers;

import com.geekbrains.demoboot.configuration.RestResponsePage;
import com.geekbrains.demoboot.entities.Product;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

//API для взаимодействия с классом ProductService из модуля product_service

@FeignClient("spring-cloud-product-client")
public interface ProductServiceClient {
    @RequestMapping("/getById")
    Product getById(@RequestBody Long id);

    @RequestMapping("/getAllProducts")
    public List<Product> getAllProducts(Specification<Product> productSpecification);

    @RequestMapping("/getAllProductsOnPages/{page}")
    public RestResponsePage<Product> getAllProductsOnPages(@PathVariable(value = "page") int page);

    @RequestMapping("/getAllProductsFiltered/{page}")
    public RestResponsePage<Product> getAllProductsFiltered(@PathVariable(value = "page") int page, @RequestParam(value = "specs") String []specs);

    @RequestMapping("/add")
    public void add(@RequestBody Product product);

    @RequestMapping("/getTop3TheMostPopularProducts")
    public List<Product> top3TheMostPopularProducts();

    @RequestMapping("/delete")
    public void delete(@RequestBody Product product);

}
