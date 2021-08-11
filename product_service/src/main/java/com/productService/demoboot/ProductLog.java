package com.productService.demoboot;

//@Aspect
//@Component
//public class ProductLog {
//    @Autowired
//    ProductService productsService;
//
//    private static Map<Product, Integer> productHistoryChek = new HashMap<>();
//
//    @Pointcut(" execution (* com.geekbrains.demoboot.controllers.ProductsController.showOneProduct(..)) &&" +
//            "args(..,id)")
//    private void getName(Long id) {
//    }
//
//    public static Map<Product, Integer> getProductHistoryChek() {
//        return productHistoryChek;
//    }
//
//    @After("getName(id)")
//    public void logProductChek(Long id) {
//        Product product = productsService.getById(id);
//        product.setViews(product.getViews()+1);
//        productsService.add(product);
//
//    }
//}