package org.soneech.service;

import org.soneech.exception.CompanyException;
import org.soneech.model.Company;
import org.soneech.repository.CompanyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class CompanyService {
    private final CompanyRepository companyRepository;

    @Value("${app.messages.not-found.company}")
    private String companyNotFoundMessage;

    @Autowired
    public CompanyService(CompanyRepository companyRepository) {
        this.companyRepository = companyRepository;
    }

    public List<Company> findAll() {
        return companyRepository.findAll();
    }

    public Optional<Company> findById(Long id) {
        Optional<Company> foundCompany = companyRepository.findById(id);
        if (foundCompany.isEmpty())
            throw new CompanyException(companyNotFoundMessage, HttpStatus.NOT_FOUND);
        return foundCompany;
    }

    public Optional<Company> findByName(String name) {
        return companyRepository.findByName(name);
    }

    public Optional<Company> findByEmail(String email) {
        return companyRepository.findByEmail(email);
    }

    public Optional<Company> findByPhoneNumber(String phoneNumber) {
        return companyRepository.findByPhoneNumber(phoneNumber);
    }

    @Transactional
    public Company save(Company company) {
        return companyRepository.save(company);
    }

    @Transactional
    public Company update(Long id, Company company) {
        Optional<Company> foundCompany = companyRepository.findById(id);
        if (foundCompany.isEmpty())
            throw new CompanyException(companyNotFoundMessage, HttpStatus.NOT_FOUND);

        company.setId(id);
        return companyRepository.save(company);
    }

    @Transactional
    public void delete(Long id) {
        Optional<Company> foundCompany = companyRepository.findById(id);
        if (foundCompany.isEmpty())
            throw new CompanyException(companyNotFoundMessage, HttpStatus.NOT_FOUND);

        companyRepository.deleteById(id);
    }
}
