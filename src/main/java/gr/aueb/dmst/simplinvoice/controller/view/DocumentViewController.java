package gr.aueb.dmst.simplinvoice.controller.view;

import gr.aueb.dmst.simplinvoice.Utils;
import gr.aueb.dmst.simplinvoice.enums.DocumentType;
import gr.aueb.dmst.simplinvoice.enums.TraderType;
import gr.aueb.dmst.simplinvoice.model.*;
import gr.aueb.dmst.simplinvoice.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/document")
public class DocumentViewController extends AbstractViewController {

    @Autowired
    DocumentService documentService;

    @Autowired
    TraderService traderService;

    @Autowired
    CompanyProfileService companyProfileService;

    @Autowired
    MaterialService materialService;

    @Autowired
    DocumentSeriesService documentSeriesService;

    @Autowired
    MessageSource messageSource;

    @GetMapping(value = "/list")
    String getTradersList(@RequestParam(value = "type") DocumentType documentType, WebRequest request, final Model model) {
        Long companyProfileId = Utils.getUserFromWebRequest(request).getCompanyProfile().getId();

        model.addAttribute("type", documentType);
        model.addAttribute("documentsList", documentService.getDocumentsList(documentType, companyProfileId));

        return getModelAndView("documentsList", model);
    }

    @GetMapping(value = {"/issue/", "/issue/{id}"})
    String getDocument(@PathVariable(required = false) Long id, WebRequest request, final Model model) {
        Long companyProfileId = Utils.getUserFromWebRequest(request).getCompanyProfile().getId();

        DocumentHeader documentHeader;

        if(id != null) {
            documentHeader = documentService.getDocumentHeaderById(id, companyProfileId);
        } else {
            documentHeader = new DocumentHeader();
            documentHeader.setDate(new Date());
            documentHeader.setCurrency("EUR");
            documentHeader.setDocumentItems(new ArrayList<>());
            DocumentItem documentItem = new DocumentItem();
            documentHeader.getDocumentItems().add(documentItem);
            documentHeader.setDocumentTaxes(new ArrayList<>());
        }

        List<Trader> tradersList = traderService.getTradersList(TraderType.CUSTOMER, companyProfileId);
        List<Material> materialsList = materialService.getMaterialsList(companyProfileId);
        List<DocumentSeries> documentSeriesList = documentSeriesService.getDocumentSeriesList(companyProfileId);
        model.addAttribute("tradersList", tradersList);
        model.addAttribute("materialsList", materialsList);
        model.addAttribute("documentSeriesList", documentSeriesList);
        model.addAttribute("documentHeader", documentHeader);

        return getModelAndView("documentIssuePage", model);
    }

    @GetMapping(value = "/issue/summary/{id}")
    String getDocumentSummary(@PathVariable Long id, WebRequest request, final Model model) {
        Long companyProfileId = Utils.getUserFromWebRequest(request).getCompanyProfile().getId();

        DocumentHeader documentHeader = documentService.getDocumentHeaderById(id, companyProfileId);

        model.addAttribute("documentHeader", documentHeader);

        return getModelAndView("documentSummary", model);
    }

    @GetMapping(value = "/issue/summary/public/{id}")
    String getDocumentSummaryPublic(@PathVariable Long id, WebRequest request, final Model model) {
        DocumentHeader documentHeader = documentService.getDocumentHeaderPublic(id);

        model.addAttribute("documentHeader", documentHeader);

        return "documentSummaryPublic";
    }

    @PostMapping(value = "/issue/save")
    String saveDocument(
            @ModelAttribute("documentHeader") @Valid DocumentHeader documentHeader, Errors errors, Model model, HttpServletRequest request, RedirectAttributes redirectAttributes) {

        if(errors.hasErrors())
            return getModelAndView("documentIssuePage", model);

        documentHeader.setCompanyProfile(Utils.getUserFromHttpServletRequest(request).getCompanyProfile());
        documentHeader.setType(DocumentType.ISSUED);

        documentService.addDocumentHeader(documentHeader);

        redirectAttributes.addAttribute("type", documentHeader.getType());
        return addSuccessMessageAndRedirect("/document/list", messageSource.getMessage("document.added.success", null, request.getLocale()), redirectAttributes);
    }

    @PostMapping(params = "addItem", path = {"/item", "/item/{id}"})
    public String addDocumentItem(DocumentHeader documentHeader, HttpServletRequest request, Model model) {
        Long companyProfileId = Utils.getUserFromHttpServletRequest(request).getCompanyProfile().getId();
        List<Material> materialsList = materialService.getMaterialsList(companyProfileId);
        model.addAttribute("materialsList", materialsList);

        documentHeader.getDocumentItems().add(new DocumentItem());
        return "documentIssuePage::#documentItems";
    }

    @PostMapping(params = "removeItem", path = {"/item", "/item/{id}"})
    public String removeDocumentItem(DocumentHeader documentHeader, @RequestParam("removeItem") int index, HttpServletRequest request, Model model) {
        Long companyProfileId = Utils.getUserFromHttpServletRequest(request).getCompanyProfile().getId();
        List<Material> materialsList = materialService.getMaterialsList(companyProfileId);
        model.addAttribute("materialsList", materialsList);

        documentHeader.getDocumentItems().remove(index);
        return "documentIssuePage::#documentItems";

    }

    @PostMapping(params = "addTax", path = {"/tax", "/tax/{id}"})
    public String addDocumentTax(DocumentHeader documentHeader, HttpServletRequest request, Model model) {
        if(documentHeader.getDocumentTaxes() == null) {
            documentHeader.setDocumentTaxes(new ArrayList<>());
        }
        documentHeader.getDocumentTaxes().add(new DocumentTax());
        return "documentIssuePage::#documentTaxes";
    }

    @PostMapping(params = "removeTax", path = {"/tax", "/tax/{id}"})
    public String removeDocumentTax(DocumentHeader documentHeader, @RequestParam("removeTax") int index, HttpServletRequest request, Model model) {
        documentHeader.getDocumentTaxes().remove(index);
        return "documentIssuePage::#documentTaxes";

    }

}
