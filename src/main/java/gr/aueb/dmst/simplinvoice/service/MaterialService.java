package gr.aueb.dmst.simplinvoice.service;

import gr.aueb.dmst.simplinvoice.dao.MaterialRepository;
import gr.aueb.dmst.simplinvoice.model.Material;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class MaterialService {

    @Autowired
    MaterialRepository materialRepository;

    public List<Material> getMaterialsList(Long companyProfileId) {
        return materialRepository.findAllByCompanyProfileId(companyProfileId);
    }

    public Material getMaterialById(Long id, Long companyProfileId) {
        return materialRepository.findMaterialByIdAndCompanyProfileId(id, companyProfileId);
    }

    @Transactional
    public Material addMaterial(Material material) {
        return materialRepository.save(material);
    }

    @Transactional
    public void deleteMaterial(Long id, Long companyProfileId) {
        materialRepository.deleteMaterialByIdAndCompanyProfileId(id, companyProfileId);
    }

}
