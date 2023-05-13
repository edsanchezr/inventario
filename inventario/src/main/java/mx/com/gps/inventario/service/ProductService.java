package mx.com.gps.inventario.service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import mx.com.gps.inventario.dto.ResponseDTO;
import mx.com.gps.inventario.model.Product;
import mx.com.gps.inventario.repository.ProductRepository;

@Service
public class ProductService {
	
	@Autowired
	private ProductRepository productRepository;
	
	@Transactional(readOnly = true)
	public ResponseEntity<ResponseDTO> searchAllProduct () {
		ResponseDTO responseDTO = new ResponseDTO();
		try {
			List<Product> lst = productRepository.findAll();
			responseDTO.setDetail(lst);
			responseDTO.setMessage("Ok");
		} catch (Exception e) {
			responseDTO.setMessage("Error to get Products");
			responseDTO.setDetail(e.getMessage());
		}
		return new ResponseEntity <> (responseDTO, HttpStatus.OK);
	}
	
	@Transactional(readOnly = true)
	public ResponseEntity<ResponseDTO> searchProductById (String id) {
		ResponseDTO responseDTO = new ResponseDTO();
		try {
			Optional<Product> product = productRepository.findById(id);
			if (product.isPresent()) {
				responseDTO.setDetail(product.get());
				responseDTO.setMessage("Ok");
			} else {
				responseDTO.setMessage("Product Not Found");
				responseDTO.setDetail("[]");
			}
		} catch (Exception e) {
			responseDTO.setMessage("Error to get Products");
			responseDTO.setDetail(e.getMessage());
		}
		return new ResponseEntity <> (responseDTO, HttpStatus.OK);
	}
	
	@Transactional
	public ResponseEntity<ResponseDTO> updateProduct (Product product, String id) {
		ResponseDTO responseDTO = new ResponseDTO();
		if (id != null && !id.equals("")) {
			boolean error = false;
			if (product.getDescripcion().length() > 200) {
				error = true;
				responseDTO.setMessage("Campo: Descripcion, longitud:  invalida");
			} else if (product.getModelo().length() > 10) {
				error = true;
				responseDTO.setMessage("Campo: Modelo, longitud:  invalida");
			}
			
			if (!error) {
				try {
					Optional<Product> productOptional = productRepository.findById(id);
					if (productOptional.isPresent()) {
						productOptional.get().setId(id);
						productOptional.get().setDescripcion(product.getDescripcion());
						productOptional.get().setModelo(product.getModelo());
						productRepository.save(productOptional.get());
						responseDTO.setMessage("Ok");
						responseDTO.setDetail("[]");
					} else {
						responseDTO.setMessage("Product Not Found");
						responseDTO.setDetail("[]");
					}
				} catch (Exception e) {
					responseDTO.setMessage("Error to get Products");
					responseDTO.setDetail(e.getMessage());
				}
			}
		} else {
			responseDTO.setMessage("Id not valid");
			responseDTO.setDetail("[]");
		}
		
		return new ResponseEntity <> (responseDTO, HttpStatus.OK);
	}
	
	@Transactional
	public ResponseEntity<ResponseDTO> saveProduct (Product product) {
		ResponseDTO responseDTO = new ResponseDTO();
		if (product.getId() == null || product.getId().equals("")) {
			
			boolean error = false;
			if (product.getDescripcion().length() > 200) {
				error = true;
				responseDTO.setMessage("Campo: Descripcion, longitud:  invalida");
			} else if (product.getModelo().length() > 10) {
				error = true;
				responseDTO.setMessage("Campo: Modelo, longitud:  invalida");
			} else if (product.getNombre().length() > 20) {
				error = true;
				responseDTO.setMessage("Campo: Nombre, longitud:  invalida");
			}
			
			if (!error) {
				String uuid = UUID.randomUUID().toString().replaceAll("-", "");
				product.setId(uuid.substring(uuid.length() - 10));
				BigDecimal nuevoPrecio = 
						new BigDecimal(product.getPrecio().toString()).setScale(2, RoundingMode.HALF_DOWN);
				product.setPrecio(nuevoPrecio);
				try {
					productRepository.save(product);
					responseDTO.setMessage("OK");
					responseDTO.setDetail(product);
				} catch (Exception e) {
					responseDTO.setMessage("Error to get Products");
					responseDTO.setDetail(e.getMessage());
				}
			}
			
		} else {
			responseDTO.setMessage("Id not valid");
			responseDTO.setDetail("[]");
		}
		
		return new ResponseEntity <> (responseDTO, HttpStatus.OK);
	}
	
	@Transactional
	public ResponseEntity<ResponseDTO> deleteProduct (String id) {
		ResponseDTO responseDTO = new ResponseDTO();
		if (id != null && !id.equals("")) {
			try {
				productRepository.deleteById(id);
				responseDTO.setMessage("Delete Ok by Product ID " + id);
				responseDTO.setDetail("[]");
			} catch (Exception e) {
				responseDTO.setMessage("Error to get Products");
				responseDTO.setDetail(e.getMessage());
			}
		} else {
			responseDTO.setMessage("Id not valid");
			responseDTO.setDetail("[]");
		}
		
		return new ResponseEntity <> (responseDTO, HttpStatus.OK);
	}
	
	
}
