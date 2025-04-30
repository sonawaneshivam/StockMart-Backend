package com.smartmart.app.services;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.smartmart.app.models.Stock;
import com.smartmart.app.repositories.ProductRepository;

@Service
public class ProductService {

	 private static final String IMAGE_UPLOAD_DIR="F:\\Stock\\StockMart-Backend\\src\\main\\resources\\image\\";
	@Autowired
	private ProductRepository productRepository;
	public String saveProductImage(MultipartFile image) {
        try {
            if (image != null && !image.isEmpty()) {
                // Get the original file name
                String imageName = image.getOriginalFilename();
                // Set up the file path where the image will be stored
                Path imagePath = Paths.get(IMAGE_UPLOAD_DIR +"\\"+ imageName);
                // Create the directory if it doesn't exist
                Files.createDirectories(imagePath.getParent());
                // Save the file
                image.transferTo(imagePath.toFile());

                // Return the URL or path where the image can be accessed
                return  imageName;  // This will be the URL used in the application
            } else {
                return null; // If no image is uploaded
            }
        } catch (IOException e) {
            e.printStackTrace();
            return null; // Handle error (maybe log it)
        }
    }
   
	public boolean storeData(Stock stock) {
		return productRepository.storeData(stock);
	}
	
	public List<Stock> showAllProduct(){
		return productRepository.showAllProduct();
	}
	
	public List<Stock> showAllProductByCategory(String category){
		return productRepository.showAllProductByCategory(category);
	}
	
	public boolean isdeleteProduct(int id) {
		return productRepository.isdeleteProduct(id);
	}
	
	public boolean isUpdateProduct(Stock stock) {
		return productRepository.isUpdateProduct(stock);
	}
}
