package gr.aueb.dmst.simplinvoice.dao;

import gr.aueb.dmst.simplinvoice.model.CompanyProfile;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CompanyProfileRepository extends JpaRepository<CompanyProfile, Long> {

    CompanyProfile findByUserId(Long userId);


}