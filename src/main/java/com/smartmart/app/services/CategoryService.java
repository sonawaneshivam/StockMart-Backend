package com.smartmart.app.services;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.smartmart.app.models.Category;
import com.smartmart.app.repositories.CategoryRepo;

@Service
public class CategoryService {
	@Autowired
	private CategoryRepo categoryRepo;
	 private static final String UPLOAD_DIR = "F:\\Stock\\StockMart-Backend\\src\\main\\resources\\image\\";

	  public boolean addCategory(String categoryName, MultipartFile imageFile) {
	        try {
	            // Get the original file name
	            String originalFileName = imageFile.getOriginalFilename();
	            Path filePath = Paths.get(UPLOAD_DIR +"\\"+ originalFileName);

	            // Check if file already exists in the folder
	            if (Files.exists(filePath)) {
	                // If file exists, don't save again, just use the existing file
	               
	            } else {
	                // If file doesn't exist, save the new file
	                Files.createDirectories(filePath.getParent()); // Create directory if it doesn't exist
	                Files.write(filePath, imageFile.getBytes()); // Save the file
	            }

	            // Save category to DB with the existing file name
	            Category category = new Category();
	            category.setCategory_name(categoryName);
	            category.setImage_url(originalFileName);  // Store the original file name in DB

	            // Save category to the database
	            int rowsAffected = categoryRepo.saveCategory(category);
	            return rowsAffected > 0;
	        } catch (IOException e) {
	            e.printStackTrace();
	            return false;
	        }
	    }
	
	public List<Category> showAllData(){
		return categoryRepo.showAllData();
	}
	
	public boolean isDeleteCategory(int id ) {
		return categoryRepo.isDeleteCategory(id);
	}
	
	 public boolean updateCategory(int id, String categoryName, MultipartFile imageFile) {
	        try {
	            String fileName = null;

	            // Handle image upload
	            if (imageFile != null && !imageFile.isEmpty()) {
	                fileName = imageFile.getOriginalFilename();
	                Path filePath = Paths.get(UPLOAD_DIR + File.separator + fileName);

	                if (!Files.exists(filePath)) {
	                    Files.createDirectories(filePath.getParent());
	                    Files.write(filePath, imageFile.getBytes());
	                }
	            }

	            // If no new image is uploaded, use existing one from DB
	            if (fileName == null) {
	                fileName = categoryRepo.getImageUrlById(id);
	            }

	            // Create Category object
	            Category category = new Category();
	            category.setCategory_id(id);
	            category.setCategory_name(categoryName);
	            category.setImage_url(fileName);

	            return categoryRepo.isUpdate(category);
	        } catch (IOException e) {
	            e.printStackTrace();
	            return false;
	        }
	    }
	 
}
