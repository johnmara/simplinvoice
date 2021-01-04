package gr.aueb.dmst.simplinvoice.controller.api;

import gr.aueb.dmst.simplinvoice.Utils;
import gr.aueb.dmst.simplinvoice.model.Material;
import gr.aueb.dmst.simplinvoice.model.Trader;
import gr.aueb.dmst.simplinvoice.service.MaterialService;
import gr.aueb.dmst.simplinvoice.service.TraderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;

@RestController
@RequestMapping(value = "/api/material")
public class MaterialApiController {

    @Autowired
    MaterialService materialService;

    @GetMapping(value = "/{id}")
    Material getMaterial(@PathVariable(required = false) Long id, WebRequest request) {
        Long companyProfileId = Utils.getUserFromWebRequest(request).getCompanyProfile().getId();

        return materialService.getMaterialById(id, companyProfileId);
    }

}
