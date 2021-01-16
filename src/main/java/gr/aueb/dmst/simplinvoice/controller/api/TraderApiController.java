package gr.aueb.dmst.simplinvoice.controller.api;

import gr.aueb.dmst.simplinvoice.Utils;
import gr.aueb.dmst.simplinvoice.model.Trader;
import gr.aueb.dmst.simplinvoice.service.TraderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;

@RestController
@RequestMapping(value = "/api/trader")
public class TraderApiController {

    @Autowired
    TraderService traderService;

    @GetMapping(value = "/{id}")
    Trader getTrader(@PathVariable(required = false) Long id, WebRequest request) {
        Long companyProfileId = Utils.getUserFromWebRequest(request).getCompanyProfile().getId();

        return traderService.getTraderById(id, companyProfileId);
    }

}
