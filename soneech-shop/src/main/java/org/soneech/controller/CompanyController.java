package org.soneech.controller;

import jakarta.validation.Valid;
import org.soneech.dto.CompanyRequestDTO;
import org.soneech.dto.CompanyResponseDTO;
import org.soneech.exception.CompanyException;
import org.soneech.mapper.DefaultMapper;
import org.soneech.model.Company;
import org.soneech.service.CompanyService;
import org.soneech.util.CompanyValidator;
import org.soneech.util.ErrorsUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/companies")
public class CompanyController {
    private final CompanyService companyService;
    private final CompanyValidator companyValidator;
    private final DefaultMapper mapper;

    @Autowired
    public CompanyController(CompanyService companyService, CompanyValidator companyValidator,
                             DefaultMapper mapper) {
        this.companyService = companyService;
        this.companyValidator = companyValidator;
        this.mapper = mapper;
    }

    @GetMapping
    public List<CompanyResponseDTO> getCompanies() {
        return companyService
                .findAll().stream().map(mapper::convertToCompanyResponseDTO).collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public CompanyResponseDTO getCompanyById(@PathVariable("id") Long id) throws CompanyException {
        return mapper.convertToCompanyResponseDTO(companyService.findById(id).get());
    }

    @PostMapping
    public ResponseEntity<?> saveCompany(@RequestBody @Valid CompanyRequestDTO companyRequestDTO,
                                         BindingResult bindingResult) {
        Company company = mapper.convertToCompany(companyRequestDTO);
        companyValidator.validate(company, bindingResult);

        if (bindingResult.hasErrors()) {
            return ResponseEntity
                    .badRequest().body(Map.of("message", ErrorsUtil.prepareFieldsErrorMessage(bindingResult)));
        }
        Company savedCompany = companyService.save(company);
        return ResponseEntity.ok(savedCompany);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateCompany(@PathVariable("id") Long id,
                                           @RequestBody @Valid CompanyRequestDTO companyRequestDTO,
                                           BindingResult bindingResult) throws CompanyException {
        Company company = mapper.convertToCompanyWithId(companyRequestDTO, id);
        companyValidator.validate(company, bindingResult);

        if (bindingResult.hasErrors()) {
            return ResponseEntity
                    .badRequest().body(Map.of("message", ErrorsUtil.prepareFieldsErrorMessage(bindingResult)));
        }
        Company updatedCompany = companyService.update(id, company);
        return ResponseEntity.ok(updatedCompany);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteCompany(@PathVariable("id") Long id) throws CompanyException {
        companyService.delete(id);
        return ResponseEntity.ok(HttpStatus.OK);
    }
}
