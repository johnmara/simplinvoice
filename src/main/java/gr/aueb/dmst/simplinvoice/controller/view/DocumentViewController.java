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
    String getDocumentIssued(@PathVariable(required = false) Long id, WebRequest request, final Model model) {
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
        model.addAttribute("type", DocumentType.ISSUED);

        return getModelAndView("documentIssuePage", model);
    }

    @GetMapping(value = {"/receive/", "/receive/{id}"})
    String getDocumentReceive(@PathVariable(required = false) Long id, WebRequest request, final Model model) {
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

        List<Trader> tradersList = traderService.getTradersList(TraderType.SUPPLIER, companyProfileId);
        List<Material> materialsList = materialService.getMaterialsList(companyProfileId);
        List<DocumentSeries> documentSeriesList = documentSeriesService.getDocumentSeriesList(companyProfileId);
        model.addAttribute("tradersList", tradersList);
        model.addAttribute("materialsList", materialsList);
        model.addAttribute("documentSeriesList", documentSeriesList);
        model.addAttribute("documentHeader", documentHeader);
        model.addAttribute("type", DocumentType.RECEIVED);

        return getModelAndView("documentReceivePage", model);
    }

    @GetMapping(value = "/summary/{id}")
    String getDocumentSummary(@PathVariable Long id, WebRequest request, final Model model) {
        Long companyProfileId = Utils.getUserFromWebRequest(request).getCompanyProfile().getId();

        DocumentHeader documentHeader = documentService.getDocumentHeaderById(id, companyProfileId);

        model.addAttribute("documentHeader", documentHeader);

        return getModelAndView("documentSummary", model);
    }

    @GetMapping(value = "/issue/summary/public/{authenticationCode}")
    String getDocumentSummaryPublic(@PathVariable String authenticationCode, HttpServletRequest request, final Model model) throws Exception {
//        String appUrl = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath();
        String requestUrl = String.valueOf(request.getRequestURL());
        DocumentHeader documentHeader = documentService.getDocumentHeaderPublic(authenticationCode, requestUrl);

        model.addAttribute("documentHeader", documentHeader);

        return "documentIssueSummaryPublic";
    }

    @PostMapping(value = "/issue/save")
    String saveDocumentIssue(
            @ModelAttribute("documentHeader") @Valid DocumentHeader documentHeader, Errors errors, Model model, HttpServletRequest request, RedirectAttributes redirectAttributes) {

        if(errors.hasErrors())
            return getModelAndView("documentIssuePage", model);

        documentHeader.setCompanyProfile(Utils.getUserFromHttpServletRequest(request).getCompanyProfile());
        documentHeader.setType(DocumentType.ISSUED);

        documentService.addDocumentHeader(documentHeader);

        redirectAttributes.addAttribute("type", documentHeader.getType());
        return addSuccessMessageAndRedirect("/document/list", messageSource.getMessage("document.added.success", null, request.getLocale()), redirectAttributes);
    }

    @PostMapping(value = "/receive/save")
    String saveDocumentReceive(
            @ModelAttribute("documentHeader") @Valid DocumentHeader documentHeader, Errors errors, Model model, HttpServletRequest request, RedirectAttributes redirectAttributes) {

        if(errors.hasErrors())
            return getModelAndView("documentReceivePage", model);

        documentHeader.setCompanyProfile(Utils.getUserFromHttpServletRequest(request).getCompanyProfile());
        documentHeader.setType(DocumentType.RECEIVED);

        documentService.addDocumentHeader(documentHeader);

        redirectAttributes.addAttribute("type", documentHeader.getType());
        return addSuccessMessageAndRedirect("/document/list", messageSource.getMessage("document.added.success", null, request.getLocale()), redirectAttributes);
    }

    @PostMapping(params = {"addItem", "documentType"}, path = {"/item", "/item/{id}"})
    public String addDocumentItem(DocumentHeader documentHeader, @RequestParam("documentType") DocumentType documentType, HttpServletRequest request, Model model) {
        Long companyProfileId = Utils.getUserFromHttpServletRequest(request).getCompanyProfile().getId();
        List<Material> materialsList = materialService.getMaterialsList(companyProfileId);
        model.addAttribute("materialsList", materialsList);

        documentHeader.getDocumentItems().add(new DocumentItem());
        return getDocumentPage(documentType) + "::#documentItems";
    }

    @PostMapping(params = {"removeItem", "documentType"}, path = {"/item", "/item/{id}"})
    public String removeDocumentItem(DocumentHeader documentHeader, @RequestParam("removeItem") int index,
                                     @RequestParam("documentType") DocumentType documentType, HttpServletRequest request, Model model) {
        Long companyProfileId = Utils.getUserFromHttpServletRequest(request).getCompanyProfile().getId();
        List<Material> materialsList = materialService.getMaterialsList(companyProfileId);
        model.addAttribute("materialsList", materialsList);

        documentHeader.getDocumentItems().remove(index);
        return getDocumentPage(documentType) + "::#documentItems";
    }

    @PostMapping(params = {"addTax", "documentType"}, path = {"/tax", "/tax/{id}"})
    public String addDocumentTax(DocumentHeader documentHeader, @RequestParam("documentType") DocumentType documentType, HttpServletRequest request, Model model) {
        if(documentHeader.getDocumentTaxes() == null) {
            documentHeader.setDocumentTaxes(new ArrayList<>());
        }
        documentHeader.getDocumentTaxes().add(new DocumentTax());

        return getDocumentPage(documentType) + "::#documentTaxes";
    }

    @PostMapping(params = {"removeTax", "documentType"}, path = {"/tax", "/tax/{id}"})
    public String removeDocumentTax(DocumentHeader documentHeader, @RequestParam("removeTax") int index,
                                    @RequestParam("documentType") DocumentType documentType, HttpServletRequest request, Model model) {
        documentHeader.getDocumentTaxes().remove(index);

        return getDocumentPage(documentType) + "::#documentTaxes";
    }

    private String getDocumentPage(DocumentType documentType) {
        switch (documentType) {
            case ISSUED:
                return "documentIssuePage";
            case RECEIVED:
                return "documentReceivePage";
            default:
                return "documentIssuePage";
        }
    }

}
