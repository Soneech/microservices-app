package org.soneech.util;


import org.soneech.model.Company;
import org.soneech.service.CompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.util.Optional;

@Component
public class CompanyValidator implements Validator {
    private final CompanyService companyService;

    @Autowired
    public CompanyValidator(CompanyService companyService) {
        this.companyService = companyService;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return clazz.equals(Company.class);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Company company = (Company) target;

        Optional<Company> foundByName = companyService.findByName(company.getName());
        if (foundByName.isPresent() && company.getId() != foundByName.get().getId())
            errors.rejectValue("name", "", "компания с таким названием уже существует");

        Optional<Company> foundByEmail = companyService.findByEmail(company.getEmail());
        if (foundByEmail.isPresent() && company.getId() != foundByEmail.get().getId())
            errors.rejectValue("email", "", "компания с таким email уже существует");

        Optional<Company> foundByPhoneNumber = companyService.findByPhoneNumber(company.getPhoneNumber());
        if (foundByPhoneNumber.isPresent() && company.getId() != foundByPhoneNumber.get().getId())
            errors.rejectValue("phoneNumber", "",
                    "компания с таким номером телефона уже существует");
    }
}
