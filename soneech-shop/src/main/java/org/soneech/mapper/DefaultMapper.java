package org.soneech.mapper;

import org.modelmapper.ModelMapper;
import org.soneech.dto.CompanyRequestDTO;
import org.soneech.dto.CompanyResponseDTO;
import org.soneech.dto.ProductRequestDTO;
import org.soneech.dto.ProductResponseDTO;
import org.soneech.exception.CompanyException;
import org.soneech.exception.ProductException;
import org.soneech.model.Company;
import org.soneech.model.Product;
import org.soneech.service.CompanyService;
import org.soneech.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class DefaultMapper {
    private final ModelMapper modelMapper;
    private final CompanyService companyService;
    private final ProductService productService;

    @Value("${app.messages.not-found.company}")
    private String companyNotFoundMessage;

    @Value("${app.messages.not-found.product}")
    private String productNotFoundMessage;

    @Autowired
    public DefaultMapper(ModelMapper modelMapper, CompanyService companyService, ProductService productService) {
        this.modelMapper = modelMapper;
        this.companyService = companyService;
        this.productService = productService;
    }

    public Company convertToCompany(CompanyRequestDTO companyDTO) {
        if (companyDTO != null)
            return modelMapper.map(companyDTO, Company.class);
        return null;
    }

    public Company convertToCompanyWithId(CompanyRequestDTO companyDTO, Long id) {
        if (companyDTO != null) {
            Optional<Company> foundCompany = companyService.findById(id);
            if (foundCompany.isEmpty())
                throw new CompanyException(companyNotFoundMessage, HttpStatus.NOT_FOUND);

            Company company = convertToCompany(companyDTO);
            company.setId(id);
            return company;
        }
        return null;
    }

    public CompanyResponseDTO convertToCompanyResponseDTO(Company company) {
        if (company != null) {
            CompanyResponseDTO companyResponseDTO = modelMapper.map(company, CompanyResponseDTO.class);
            List<ProductResponseDTO> productsResponse = new ArrayList<>();

            for (var product: company.getProducts())
                productsResponse.add(convertToProductResponseDTO(product));
            companyResponseDTO.setProducts(productsResponse);

            return companyResponseDTO;
        }
        return null;
    }

    public Product convertToProduct(ProductRequestDTO productDTO) {
        if (productDTO != null) {
            Optional<Company> company = companyService.findById(productDTO.getCompanyId());
            if (company.isEmpty())
                throw new CompanyException(companyNotFoundMessage, HttpStatus.NOT_FOUND);

            Product product = modelMapper.map(productDTO, Product.class);
            product.setCompany(company.get());
            return product;
        }
        return null;
    }

    public Product convertToProductWithId(ProductRequestDTO productDTO, Long id) {
       if (productDTO != null) {
           Optional<Product> foundProduct = productService.findById(id);
           if (foundProduct.isEmpty())
               throw new ProductException(productNotFoundMessage, HttpStatus.NOT_FOUND);

           Product product = convertToProduct(productDTO);
           product.setId(id);
           return product;
       }
       return null;
    }

    public ProductResponseDTO convertToProductResponseDTO(Product product) {
        if (product != null) {
            ProductResponseDTO productDTO = modelMapper.map(product, ProductResponseDTO.class);
            productDTO.setCompanyId(product.getCompany().getId());
            return productDTO;
        }
        return null;
    }
}
