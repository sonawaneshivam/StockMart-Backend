package com.smartmart.app.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.smartmart.app.models.Product;
import com.smartmart.app.models.Stock;
import com.smartmart.app.services.ProductService;


@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/product")
public class ProductController {
	@Autowired
	private ProductService productService;
	@PostMapping("/add")
    public ResponseEntity<String> addProduct(@RequestParam String p_name,
                                             @RequestParam String p_description,
                                             @RequestParam int category_id,
                                             @RequestParam int p_price,
                                             @RequestParam int stock,
                                             @RequestParam("image") MultipartFile image) {
       
        Stock s=new Stock();
       s.setName(p_name);
       s.setDescription(p_description);
       s.setCategory_id(category_id);
       s.setPrice(p_price);
       s.setQuantity(stock);
       String imageUrl = productService.saveProductImage(image);
       s.setProduct_image_url(imageUrl); 
       

        if (productService.storeData(s)) {
            return ResponseEntity.ok("Product is added successfully");
        } else {
            return ResponseEntity.status(500).body("Product not added");
        }
    }
	
	@GetMapping("/showAll")
	public List<Stock> showAllProduct(){
		List<Stock> product=productService.showAllProduct();
		 String baseUrl = "http://localhost:8080/image/"; // Modify this based on your actual setup

	        // Append the image base URL to each category's image name
		 product.forEach(products -> {
	            String imageUrl = baseUrl + products.getProduct_image_url();
	            products.setProduct_image_url(imageUrl); // Update the category object with the full image URL
	        });

		return product;
	}
	
	@GetMapping("/showProductByCategory")
	public List<Stock> showAllProductByCategory(@RequestParam String category){
		return productService.showAllProductByCategory(category);
	}
	
	@DeleteMapping("/delete")
	public String deletProduct(@RequestParam int id) {
		if (productService.isdeleteProduct(id)) {
			return "the product was deleted";
		} else {
			return "product was not deleted";
		}
	
	}
	
	@PutMapping("/update")
	public String updateProduct(@RequestBody Stock stock) {
		if (productService.isUpdateProduct(stock)) {
			return "the product was Updated";
		} else {
			return "product was not Updated";
		}
	}
	
	
}
