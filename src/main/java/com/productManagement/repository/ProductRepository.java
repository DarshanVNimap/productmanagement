package com.productManagement.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.productManagement.model.Product;

public interface ProductRepository extends JpaRepository<Product, Long>{
	
	

}
