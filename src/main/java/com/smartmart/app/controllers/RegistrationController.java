package com.smartmart.app.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.smartmart.app.models.UserProfile;
import com.smartmart.app.services.RegistartionService;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/register")
public class RegistrationController {
	@Autowired
	private RegistartionService registartionService;
	@PostMapping("/details")
	public String savePersonalDetail(@RequestBody UserProfile uProfile) {
		boolean result = registartionService.isSaveData(uProfile);
        return result  ? "User profile saved successfully" : "Failed to save user profile";
	}
	
	@GetMapping("/login")
	public ResponseEntity<String> login(@RequestParam String username, @RequestParam String password) {
	    String role = registartionService.isGetRole(username, password);

	    if (role == null) {
	        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
	    }

	    return ResponseEntity.ok(role);
	}

	

}
