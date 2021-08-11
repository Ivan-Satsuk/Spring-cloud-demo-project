package com.productService.demoboot;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ProductService {

    private ProductRepository productRepository;

    @Autowired
    public void setProductRepository(ProductRepository productRepository1) {
        this.productRepository = productRepository1;
    }
    @RequestMapping("/getById")
    public Product getById(@RequestBody Long id) {
        return  productRepository.getOne(id);
    }

    public List<Product> getAllProducts(Specification<Product> productSpecification) {
        return productRepository.findAll(productSpecification);
    }
    @RequestMapping("/getAllProductsOnPages/{page}")
    public Page <Product> getAllProductsOnPages(@PathVariable(value = "page") int page){
        return productRepository.findAll(PageRequest.of(page,10));
    }
    @RequestMapping("/getAllProductsFiltered/{page}")
    public Page <Product> getAllProductsFiltered(@PathVariable(value = "page") int page,  @RequestParam(value = "specs") String []specs){

        // Спецификация для реализация комбинированного либо одичного применения фильтров.

        Specification<Product> productSpecification = Specification.where(null);

        if(!specs[0].equals("null")){
            productSpecification=productSpecification.and(ProductSpecifications.titleContains(specs[0]));
        }
        if(!specs[1].equals("null")){
            int max = Integer.parseInt(specs[1]);
            productSpecification=productSpecification.and(ProductSpecifications.priceLessThanOrEqualTo(max));
        }
        if(!specs[2].equals("null")){
            int min = Integer.parseInt(specs[2]);
            productSpecification=productSpecification.and(ProductSpecifications.priceGreaterThanOrEqualTo(min));
        }

        return productRepository.findAll(productSpecification, PageRequest.of(page,10,Sort.by("id").ascending()));
    }
    @RequestMapping("/add")
    public void add(@RequestBody Product product) {
        productRepository.save(product);
    }

    @RequestMapping("/getTop3TheMostPopularProducts")
    public List <Product> top3TheMostPopularProducts(){
        return productRepository.findTop3ByOrderByViewsDesc();
    }

    @RequestMapping("/delete")
    public void delete(@RequestBody Product product){
        productRepository.delete(product);
    }

}
