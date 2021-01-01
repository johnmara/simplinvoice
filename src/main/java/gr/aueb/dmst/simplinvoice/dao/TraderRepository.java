package gr.aueb.dmst.simplinvoice.dao;

import gr.aueb.dmst.simplinvoice.enums.TraderType;
import gr.aueb.dmst.simplinvoice.model.Trader;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TraderRepository extends JpaRepository<Trader, Long> {

    Trader findTraderByIdAndCompanyProfileId(Long id, Long companyProfileId);

    void deleteTraderByIdAndCompanyProfileId(Long id, Long companyProfileId);

    List<Trader> findAllByTypeAndCompanyProfileId(TraderType traderType, Long companyProfileId);

}