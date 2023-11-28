package com.productManagement.service;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.nio.file.Files;
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
import com.productManagement.utils.FileHelper;

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
		
		String URL = PATH_URL + file.getOriginalFilename(); 
		
		productRequest.setProductImage(URL);
		file.transferTo(new File(URL));
		Product product = productRepository.save(mapper.map(productRequest, Product.class));

		if (product != null) {
			log.info("Product added");
		}
		return productRequest;
	 	}

	/*---------------------------Get Request--------------------------------*/

	public List<ProductRequest> getProductList(Integer pageNo, Integer pageSize) {
		
		log.info("Getting all products.....");
		return productRepository.findAll(
							PageRequest.of(pageNo - 1, pageSize))
										.stream()
										.map(product -> mapper.map(product, ProductRequest.class))
										.collect(Collectors.toList());
		
	}

	public ProductRequest getProductById(Long id) {
		Product product = productRepository.findById(id).orElse(null);
		if (product == null)
			throw new ProductNotFoundException("Product not found id: " + id);

		log.info("Getting Product with id :" + id);
		return mapper.map(product, ProductRequest.class);
	}

	/*---------------------------------Put Request------------------------------------*/

	public ProductRequest updateProduct(ProductRequest productRequest , MultipartFile file, Long id) throws Exception {

		
		if(!file.isEmpty()) { 
			String URL = PATH_URL+file.getOriginalFilename();
			file.transferTo(new File(URL));
			productRequest.setProductImage(URL);
		}
		
		Product product = mapper.map(productRequest, Product.class);
		
		product.setId(id);
		productRepository.save(product);
		
		log.info("Updating Product with id :" + id);
		
		return productRequest;
	}

	/*------------------------------Delete Request---------------------------------*/

	public String deleteProduct(Long id) throws Exception {
		Product product = productRepository.findById(id).orElseThrow();
		Files.deleteIfExists(new File(product.getProductImage()).toPath());
		
		log.info("Deleting Product with id :" + id);
		
		return "Product remove with id: " + id;
	}
	
	
	/*------------------------------- Export Excel------------------------------------*/
	
	public ByteArrayInputStream exportInExcel() {
		return FileHelper.exportToExcel(productRepository.findAll());
	}

}
