package co.edu.escuelaing.cvds.lab7.controller;

import co.edu.escuelaing.cvds.lab7.model.Category;
import co.edu.escuelaing.cvds.lab7.model.Employee;
import co.edu.escuelaing.cvds.lab7.model.Product;
import co.edu.escuelaing.cvds.lab7.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Controller
@RequestMapping(value = "/api")
public class ProductController {
    private final ProductService productService;

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/products")
    @ResponseBody
    public List<Product> getAllProducts() {
        return productService.getAllProducts();
    }

    @GetMapping("/product/:{id}")
    public ResponseEntity<Product> getProductById(@PathVariable int id) {
        Optional<Product> product = productService.getProduct(id);
        return product.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping("/product")
    public ResponseEntity<Product> createProduct(@RequestBody Product product){
        Product savedProduct = productService.addProduct(product);
        return new ResponseEntity<>(savedProduct, HttpStatus.CREATED);
    }

    @PutMapping("/product")
    public ResponseEntity<Product> updateProduct(
            @RequestBody Product product
    ){
        Product updatedProduct = productService.updateProduct(product);
        return new ResponseEntity<Product>(updatedProduct, HttpStatus.OK);
    }

    @PatchMapping("/product/:{id}")
    public ResponseEntity<Product> partialUpdateProduct(
            @PathVariable("id") int id,
            @RequestBody Map<String, Object> updates
    ){
        Product product = productService.getProduct(id).stream().findFirst().orElse(null);
        if (product == null) {
            return ResponseEntity.notFound().build();
        }
        updates.forEach((key, value) -> {
            switch (key) {
                case "name":
                    product.setName((String) value);
                    break;
                case "description":
                    product.setDescription((String) value);
                    break;
                case "category":
                    product.setCategory((Category) value);
                    break;
                case "rating":
                    product.setRating((short) value);
                    break;
                case "price":
                    product.setPrice((int) value);
                    break;
                case "quantity":
                    product.setQuantity((int) value);
                    break;
            }
        });
        Product updatedProduct = productService.updateProduct(product);
        return ResponseEntity.ok(updatedProduct);
    }


    @DeleteMapping("/product/:{id}")
    public ResponseEntity<String> deleteProduct(@PathVariable("id") int id){

        productService.deleteProduct(id);

        return new ResponseEntity<>("Product deleted successfully!.", HttpStatus.OK);
    }

}
