package gr.aueb.dmst.simplinvoice.service;

import gr.aueb.dmst.simplinvoice.dao.CompanyProfileRepository;
import gr.aueb.dmst.simplinvoice.model.CompanyProfile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CompanyProfileService {

    @Autowired
    CompanyProfileRepository companyProfileRepository;

    public CompanyProfile getCompanyProfileByUserId(Long userId) {
        return companyProfileRepository.findByUserId(userId);
    }

    public CompanyProfile addCompanyProfile(CompanyProfile companyProfile) {
        return companyProfileRepository.save(companyProfile);
    }

}
