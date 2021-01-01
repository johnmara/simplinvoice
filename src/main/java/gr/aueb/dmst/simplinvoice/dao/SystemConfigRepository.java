package gr.aueb.dmst.simplinvoice.dao;

import gr.aueb.dmst.simplinvoice.model.SystemConfig;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SystemConfigRepository extends JpaRepository<SystemConfig, Long> {

    SystemConfig findSystemConfigByCompanyProfileId(Long companyProfileId);


}