package gr.aueb.dmst.simplinvoice.service;

import gr.aueb.dmst.simplinvoice.dao.SystemConfigRepository;
import gr.aueb.dmst.simplinvoice.model.SystemConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class SystemConfigService {

    @Autowired
    SystemConfigRepository systemConfigRepository;

    public SystemConfig getSystemConfigByCompanyProfileId(Long companyProfileId) {
        return systemConfigRepository.findSystemConfigByCompanyProfileId(companyProfileId);
    }

    @Transactional
    public SystemConfig addSystemConfig(SystemConfig systemConfig) {
        return systemConfigRepository.save(systemConfig);
    }

}
