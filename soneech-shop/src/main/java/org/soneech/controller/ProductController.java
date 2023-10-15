package org.soneech.controller;

import jakarta.validation.Valid;
import org.soneech.dto.ProductRequestDTO;
import org.soneech.dto.ProductResponseDTO;
import org.soneech.exception.CompanyException;
import org.soneech.exception.ProductException;
import org.soneech.mapper.DefaultMapper;
import org.soneech.model.Product;
import org.soneech.service.ProductService;
import org.soneech.util.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/products")
public class ProductController {
    private final ProductService productService;
    private final DefaultMapper mapper;

    @Autowired
    public ProductController(ProductService productService, DefaultMapper mapper) {
        this.productService = productService;
        this.mapper = mapper;
    }

    @GetMapping
    public List<ProductResponseDTO> getProducts() {
        return productService
                .findAll().stream().map(mapper::convertToProductResponseDTO).collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public ProductResponseDTO getProductById(@PathVariable("id") Long id) throws ProductException {
        return mapper.convertToProductResponseDTO(productService.findById(id).get());
    }

    @PostMapping
    public ResponseEntity<?> saveProduct(@RequestBody @Valid ProductRequestDTO productRequestDTO,
                                         BindingResult bindingResult) throws CompanyException {
        if (bindingResult.hasErrors()) {
            return ResponseEntity
                    .badRequest().body(Map.of("message", MessageService.prepareFieldsErrorMessage(bindingResult)));
        }

        Product product = mapper.convertToProduct(productRequestDTO);
        Product savedProduct = productService.save(product);
        return ResponseEntity.ok(savedProduct);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateProduct(@PathVariable("id") Long id,
                                           @RequestBody @Valid ProductRequestDTO productRequestDTO,
                                           BindingResult bindingResult)
            throws ProductException, CompanyException {

        if (bindingResult.hasErrors()) {
            return ResponseEntity
                    .badRequest().body(Map.of("message", MessageService.prepareFieldsErrorMessage(bindingResult)));
        }
        Product product = mapper.convertToProductWithId(productRequestDTO, id);
        Product updatedProduct = productService.update(id, product);
        return ResponseEntity.ok(updatedProduct);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteProduct(@PathVariable("id") Long id) throws ProductException {
        productService.delete(id);
        return ResponseEntity.ok(HttpStatus.OK);
    }
}
