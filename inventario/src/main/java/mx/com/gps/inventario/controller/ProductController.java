package mx.com.gps.inventario.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import mx.com.gps.inventario.dto.ResponseDTO;
import mx.com.gps.inventario.model.Product;
import mx.com.gps.inventario.service.ProductService;

@RestController
@RequestMapping("/api/v1")
public class ProductController {

	@Autowired
	private ProductService productService;
	
	@GetMapping("/products")
	public ResponseEntity<ResponseDTO> getAllProducts () {
		return productService.searchAllProduct();
	}
	
	@GetMapping("/products/{id}")
	public ResponseEntity<ResponseDTO> getProductById (@PathVariable String id) {
		return productService.searchProductById(id);
	}
	
	@PostMapping("/products")
	public ResponseEntity <ResponseDTO> saveProduct (@RequestBody Product product) {
		return productService.saveProduct(product);
	}
	
	@PutMapping("/products/{id}")
	public ResponseEntity <ResponseDTO> updateProduct (@RequestBody Product product,
			@PathVariable String id) {
		return productService.updateProduct(product, id);
	}
	
	@DeleteMapping("/products/{id}")
	public ResponseEntity <ResponseDTO> deleteProduct (@PathVariable String id) {
		return productService.deleteProduct(id);
	}
}
