package com.geekbrains.demoboot.controllers;

import com.geekbrains.demoboot.entities.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

//Логика для взаимодействия с фронтом реализованном на шаблонизаторе thymeleaf
//Код всех страниц содержится в папке resources->templates

@Controller
@RequestMapping("/products")
public class ProductsController {
    private ProductServiceClient productsService;

    @Autowired
    public void setProductsService(ProductServiceClient productsService) {
        this.productsService = productsService;
    }

    @GetMapping
    public String showProductsList(Model model, Principal principal) {
        Product product = new Product();

        PageImpl <Product> productPage = productsService.getAllProductsOnPages(0);

        model.addAttribute("products",productPage.getContent());
        model.addAttribute("product", product);
        model.addAttribute("page", 0);

        if (principal != null) {
            model.addAttribute("principal", principal.getName());
        } else {
            model.addAttribute("principal", "Unknown_Rabbit");
        }
        return "products";
    }

    @GetMapping("/page/{page}")
    public String showProductsWithFilters(Model model, Principal principal,
                                          @PathVariable(value = "page", required = false) Integer page,
                                          @RequestParam(value = "substring", required = false) String substring,
                                          @RequestParam(value = "min", required = false) Integer min,
                                          @RequestParam(value = "max", required = false) Integer max) {

       String[] productSpecification = new String[3];
        if (substring != null) {
            productSpecification[0]=substring;
        }
        if (max != null) {
            productSpecification[1]=max.toString();
        }
        if (min != null) {
            productSpecification[2]=min.toString();
        }

        if (page == null || page == 0) {
            page = 1;
        }

        PageImpl<Product> productPage = productsService.getAllProductsFiltered(page - 1, productSpecification);
        model.addAttribute("products", productPage.getContent());
        model.addAttribute("substring", substring);
        model.addAttribute("min", min);
        model.addAttribute("max", max);
        model.addAttribute("hits", productsService.top3TheMostPopularProducts());


        if (principal != null) {
            model.addAttribute("principal", principal.getName());
        } else {
            model.addAttribute("principal", "Unknown_Rabbit");
        }
        return "products";

    }

    @GetMapping("/add")
    public String addPageRedirect(Model model) {
        Product product = new Product();
        model.addAttribute(product);
        return "product_add";
    }

    @PostMapping("/product_add/add")
    public String addProduct(@ModelAttribute(value = "product") Product product) {
        if (product != null && product.getPrice() != 0 && product.getTitle() != null) {
            product.setViews(0);
            productsService.add(product);
        }
        return "redirect:/products";
    }

    @GetMapping("/delete/{id}")
    public String deleteProduct(Model model, @PathVariable(value = "id") Long id) {
        Product product = productsService.getById(id);
        productsService.delete(product);
        return "redirect:/products";
    }


    @GetMapping("/show/{id}")
    public String showOneProduct(Model model, @PathVariable(value = "id") Long id) {
        Product product = productsService.getById(id);
        model.addAttribute("product", product);
        return "product-page";
    }


    @GetMapping("/edit/{id}")
    public String editProduct(Model model, @PathVariable(value = "id") Long id) {
        Product product = productsService.getById(id);
        model.addAttribute("product", product);
        return "edit_product";
    }

    @PostMapping("/addEdit")
    public String addEditProduct(@ModelAttribute(value = "product") Product product) {
        Product product1 = productsService.getById(product.getId());
        product1.setPrice(product.getPrice());
        product1.setTitle(product.getTitle());
        productsService.add(product1);
        return "redirect:/products";


    }

}
