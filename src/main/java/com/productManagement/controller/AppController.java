package com.productManagement.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.productManagement.dto.ProductRequest;
import com.productManagement.service.ProductService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/product")
public class AppController {
	
	@Autowired
	private ProductService service;
	
	@GetMapping("")
	public ResponseEntity<?> getAllProducts(
			@RequestParam(defaultValue = "1" , name = "pageNo") Integer pageNo ,
			@RequestParam(defaultValue = "10" , name = "pageSize") Integer pageSize){
		return ResponseEntity
						.ok()
						.body(service.getProductList(pageNo , pageSize));
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<?> getProductById(@PathVariable Long id){
		return ResponseEntity
							.ok()
							.body(service.getProductById(id));
	}
	
	@PostMapping
	public ResponseEntity<?> addProduct(@ModelAttribute @Valid  ProductRequest product , @RequestParam(name = "image") MultipartFile file) throws Exception {
		return ResponseEntity
							.status(HttpStatus.CREATED)
							.body(service.addProduct(product , file));
	} 
	
	@PutMapping("/{id}")
	public ResponseEntity<?> updateProduct(@ModelAttribute @Valid ProductRequest product , @RequestParam(name = "image" , required = false) MultipartFile file,@PathVariable Long id) throws Exception{
		return ResponseEntity
							.ok()
							.body(service.updateProduct(product , file, id));
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<?> removeProduct(@PathVariable Long id) throws Exception{
		return ResponseEntity
							.ok()
							.body(service.deleteProduct(id));
	}

	
	@GetMapping("/export")
	public ResponseEntity<?> exportToExcel(){
		return ResponseEntity
						.ok()
						.header("Content-Disposition", "attachment; filename=products")
						.contentType(MediaType.valueOf("application/vnd.ms-excel"))
						.body(new InputStreamResource(service.exportInExcel()));
	}
}
