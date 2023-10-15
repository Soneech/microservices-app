package org.soneech.service;

import org.soneech.exception.ProductException;
import org.soneech.model.Product;
import org.soneech.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class ProductService {
    private final ProductRepository productRepository;

    @Value("${app.messages.not-found.product}")
    private String productNotFoundMessage;

    @Autowired
    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public List<Product> findAll() {
        return productRepository.findAll();
    }

    public Optional<Product> findById(Long id) {
        Optional<Product> foundProduct = productRepository.findById(id);
        if (foundProduct.isEmpty())
            throw new ProductException(productNotFoundMessage, HttpStatus.NOT_FOUND);
        return foundProduct;
    }

    @Transactional
    public Product save(Product product) {
        return productRepository.save(product);
    }

    @Transactional
    public Product update(Long id, Product product) {
        Optional<Product> foundProduct = productRepository.findById(id);
        if (foundProduct.isEmpty())
            throw new ProductException(productNotFoundMessage, HttpStatus.NOT_FOUND);

        product.setId(id);
        return productRepository.save(product);
    }

    @Transactional
    public void delete(Long id) {
        Optional<Product> foundProduct = productRepository.findById(id);
        if (foundProduct.isEmpty())
            throw new ProductException(productNotFoundMessage, HttpStatus.NOT_FOUND);

        productRepository.deleteById(id);
    }
}
