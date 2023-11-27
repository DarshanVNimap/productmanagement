package com.productManagement.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor(staticName = "build")
@NoArgsConstructor
public class ProductRequest {
	
	@NotBlank(message = "name should not be blank")
	@Size(min = 3 ,max = 30, message = "name length must be in between 3 to 30")
	private String name;
	
	@Size(max = 400 , message = "description length should be less than 400 character")
	private String description;
	
	//@NotBlank(message = "price should not be blank")
	@Min(value = 1 , message = "price can not be 0 or less")
	private Double price;
	
	//@NotBlank(message = "quantity should not be blank")
	@Min(value = 0 , message = "price can not be negative")
	private Integer quantityInStock;
	
	private String productImage;

}
