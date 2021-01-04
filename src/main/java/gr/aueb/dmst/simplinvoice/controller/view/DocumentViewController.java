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

    @GetMapping(value = "/issue")
    String getTrader(@PathVariable(required = false) Long id, WebRequest request, final Model model) {
        Long companyProfileId = Utils.getUserFromWebRequest(request).getCompanyProfile().getId();

        DocumentHeader documentHeader = new DocumentHeader();
        documentHeader.setDate(new Date());
        documentHeader.setDocumentItems(new ArrayList<>());
        documentHeader.getDocumentItems().add(new DocumentItem());

        List<Trader> tradersList = traderService.getTradersList(TraderType.CUSTOMER, companyProfileId);
        List<Material> materialsList = materialService.getMaterialsList(companyProfileId);
        List<DocumentSeries> documentSeriesList = documentSeriesService.getDocumentSeriesList(companyProfileId);
        model.addAttribute("tradersList", tradersList);
        model.addAttribute("materialsList", materialsList);
        model.addAttribute("documentSeriesList", documentSeriesList);
        model.addAttribute("documentHeader", documentHeader);

        return getModelAndView("documentIssuePage", model);
    }

    @PostMapping(value = "/issue/save")
    String saveTrader(
            @ModelAttribute("documentHeader") @Valid DocumentHeader documentHeader, Errors errors, Model model, HttpServletRequest request, RedirectAttributes redirectAttributes) {

        if(errors.hasErrors())
            return getModelAndView("documentIssuePage", model);

        documentHeader.setCompanyProfile(Utils.getUserFromHttpServletRequest(request).getCompanyProfile());

        try {
            documentService.addDocumentHeader(documentHeader);
        } catch (DataIntegrityViolationException ex) {
            documentHeader.setId(null);
            model.addAttribute("modelError", messageSource.getMessage("document.code.already.exists", null, request.getLocale()));
            return getModelAndView("traderDetails", model);
        }

        redirectAttributes.addAttribute("type", documentHeader.getType());
        return addSuccessMessageAndRedirect("/documentHeader/list", messageSource.getMessage("document.added.success", null, request.getLocale()), redirectAttributes);
    }

    @PostMapping(params = "addItem", path = {"/item", "/item/{id}"})
    public String addDocumentItem(DocumentHeader documentHeader, HttpServletRequest request, Model model) {
        Long companyProfileId = Utils.getUserFromHttpServletRequest(request).getCompanyProfile().getId();
        List<Material> materialsList = materialService.getMaterialsList(companyProfileId);
        model.addAttribute("materialsList", materialsList);

        documentHeader.getDocumentItems().add(new DocumentItem());
        return "documentIssuePage::#documentItems";
    }

    // "removeItem" parameter contains index of a item that will be removed.
    @PostMapping(params = "removeItem", path = {"/item", "/item/{id}"})
    public String removeOrder(DocumentHeader documentHeader, @RequestParam("removeItem") int index, HttpServletRequest request, Model model) {
        Long companyProfileId = Utils.getUserFromHttpServletRequest(request).getCompanyProfile().getId();
        List<Material> materialsList = materialService.getMaterialsList(companyProfileId);
        model.addAttribute("materialsList", materialsList);

        documentHeader.getDocumentItems().remove(index);
        return "documentIssuePage::#documentItems";

    }

}
