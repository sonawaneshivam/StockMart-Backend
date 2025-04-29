package com.smartmart.app.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.smartmart.app.models.Category;
import com.smartmart.app.services.CategoryService;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/category")
public class CategoryController {

	@Autowired
	private CategoryService categoryService;

	@PostMapping("/add")
	public String addCategory(@RequestParam("category_name") String categoryName,
			@RequestParam("image") MultipartFile imageFile) {
		if (categoryService.addCategory(categoryName, imageFile)) {
			return "Category is added successfully!";
		} else {
			return "Failed to add category!";
		}
	}

	 @GetMapping("/showAll")
	    public List<Category> showData() {
	        List<Category> categories = categoryService.showAllData();
	        // Assuming the image file is stored in a folder on the server
	        String baseUrl = "http://localhost:8080/image/"; // Modify this based on your actual setup

	        // Append the image base URL to each category's image name
	        categories.forEach(category -> {
	            String imageUrl = baseUrl + category.getImage_url();
	            category.setImage_url(imageUrl); // Update the category object with the full image URL
	        });

	        return categories;
	    }
	@DeleteMapping("/delete")
	public String isDelete(@RequestParam int id) {
		if (categoryService.isDeleteCategory(id)) {
			return "category is deleted";
		} else {
			return "category is not deleted";
		}
	}

	 @PutMapping("/update")
	    public ResponseEntity<String> updateCategory(
	            @RequestParam("id") int id,
	            @RequestParam("category_name") String categoryName,
	            @RequestParam(value = "image", required = false) MultipartFile imageFile) {

	        try {
	            if (categoryService.updateCategory(id, categoryName, imageFile)) {
	                return ResponseEntity.ok("Category updated successfully!");
	            } else {
	                return ResponseEntity.badRequest().body("Failed to update category!");
	            }
	        } catch (Exception e) {
	            e.printStackTrace();
	            return ResponseEntity.internalServerError().body("Server error!");
	        }
	    }

}
