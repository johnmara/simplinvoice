package gr.aueb.dmst.simplinvoice.service;

import gr.aueb.dmst.simplinvoice.dao.TraderRepository;
import gr.aueb.dmst.simplinvoice.enums.TraderType;
import gr.aueb.dmst.simplinvoice.model.Trader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class TraderService {

    @Autowired
    TraderRepository traderRepository;

    public List<Trader> getTradersList(TraderType traderType, Long companyProfileId) {
        return traderRepository.findAllByTypeAndCompanyProfileId(traderType, companyProfileId);
    }

    public Trader getTraderById(Long id, Long companyProfileId) {
        return traderRepository.findTraderByIdAndCompanyProfileId(id, companyProfileId);
    }

    @Transactional
    public Trader addTrader(Trader trader) {
        return traderRepository.save(trader);
    }

    @Transactional
    public void deleteTrader(Long id, Long companyProfileId) {
        traderRepository.deleteTraderByIdAndCompanyProfileId(id, companyProfileId);
    }

}
