package com.productManagement.service;

import java.io.File;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.productManagement.dto.ProductRequest;
import com.productManagement.exceptionHandler.ProductNotFoundException;
import com.productManagement.model.Product;
import com.productManagement.repository.ProductRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class ProductService {

	private final ProductRepository productRepository;

	@Autowired
	private ModelMapper mapper;

	private final String PATH_URL = "C:\\Users\\Nimap\\Desktop\\LocalStorage\\";

	public ProductService(@Autowired ProductRepository repository) {
		this.productRepository = repository;
	}

	/*-------------------------------Post Request------------------------------------*/

	public ProductRequest addProduct(ProductRequest productRequest, MultipartFile file) throws Exception {

//		Product product =  productRepository.save(
//					Product.build((long) 0  ,
//								productRequest.getName(), 
//								productRequest.getDescription(), 
//								productRequest.getPrice(), 
//								productRequest.getQuantityInStock()));
		
		productRequest.setProductImage(PATH_URL + file.getOriginalFilename());
		Product product = productRepository.save(mapper.map(productRequest, Product.class));

		file.transferTo(new File(PATH_URL+file.getOriginalFilename()));

		if (product != null) {
			log.info("Product added");
		}
		return productRequest;
	}

	/*---------------------------Get Request--------------------------------*/

	public List<ProductRequest> getProductList(Integer pageNo, Integer pageSize) {

//		 productRepository.findAll().forEach(product -> {
//			 
//			 productRequests.add(
//					 ProductRequest.build(
//								product.getName(), 
//								product.getDescription(), 
//								product.getPrice(), 
//								product.getQuantityInStock())
//					 );
//		 });

		log.info("Getting all products.....");
		return productRepository.findAll(PageRequest.of(pageNo - 1, pageSize)).stream()
				.map(product -> mapper.map(product, ProductRequest.class)).collect(Collectors.toList());
	}

	public ProductRequest getProductById(Long id) {
		Product product = productRepository.findById(id).orElse(null);
		if (product == null)
			throw new ProductNotFoundException("Product not found id: " + id);

		log.info("Getting Product with id :" + id);
		return mapper.map(product, ProductRequest.class);
	}

	/*---------------------------------Put Request------------------------------------*/

	public ProductRequest updateProduct(ProductRequest productRequest, Long id) {

		Product product = mapper.map(productRequest, Product.class);
		product.setId(id);
		productRepository.save(product);
		log.info("Updating Product with id :" + id);
		return productRequest;
	}

	/*------------------------------Delete Request---------------------------------*/

	public String deleteProduct(Long id) {
		productRepository.deleteById(id);
		log.info("Deleting Product with id :" + id);
		return "Product remove with id: " + id;
	}

}
