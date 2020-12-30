package gr.aueb.dmst.simplinvoice.dao;

import gr.aueb.dmst.simplinvoice.model.Material;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MaterialRepository extends JpaRepository<Material, Long> {

    Material findMaterialByIdAndCompanyProfileId(Long id, Long companyProfileId);

    void deleteMaterialByIdAndCompanyProfileId(Long id, Long companyProfileId);

    List<Material> findAllByCompanyProfileId(Long companyProfileId);

}