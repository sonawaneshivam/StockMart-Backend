package com.smartmart.app.models;

import org.springframework.web.multipart.MultipartFile;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Product extends Category {
	private int product_id;
	private String name;
	private int category_id;
	private int  price;
	private String description;
	private String product_image_url;
	
}
