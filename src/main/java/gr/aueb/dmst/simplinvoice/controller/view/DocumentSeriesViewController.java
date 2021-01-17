package gr.aueb.dmst.simplinvoice.controller.view;

import gr.aueb.dmst.simplinvoice.Utils;
import gr.aueb.dmst.simplinvoice.model.DocumentSeries;
import gr.aueb.dmst.simplinvoice.service.DocumentSeriesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@Controller
@RequestMapping("/document/series")
public class DocumentSeriesViewController extends AbstractViewController {

    @Autowired
    DocumentSeriesService documentSeriesService;

    @Autowired
    MessageSource messageSource;

    @GetMapping(value = "/list")
    String getDocumentSeriesList(WebRequest request, final Model model) {
        Long companyProfileId = retrieveCompanyProfileId(Utils.getUserFromWebRequest(request));

        model.addAttribute("documentSeriesList", documentSeriesService.getDocumentSeriesList(companyProfileId));

        return getModelAndView("documentSeriesList", model);
    }

    @GetMapping(value = {"/", "/{id}"})
    String getDocumentSeries(@PathVariable(required = false) Long id, WebRequest request, final Model model) {
        Long companyProfileId = retrieveCompanyProfileId(Utils.getUserFromWebRequest(request));

        DocumentSeries documentSeries;

        if(id != null) {
            documentSeries = documentSeriesService.getDocumentSeriesById(id, companyProfileId);
        } else {
            documentSeries = new DocumentSeries();
        }

        model.addAttribute("documentSeries", documentSeries);

        return getModelAndView("documentSeriesDetails", model);
    }

    @DeleteMapping(value = "/delete/{id}")
    String deleteDocumentSeries(@PathVariable Long id, WebRequest request, final Model model, RedirectAttributes redirectAttributes) {
        Long companyProfileId = retrieveCompanyProfileId(Utils.getUserFromWebRequest(request));

        documentSeriesService.deleteDocumentSeries(id, companyProfileId);
        return addSuccessMessageAndRedirect("/document/series/list", messageSource.getMessage("document.series.deleted.success", null, request.getLocale()), redirectAttributes);
    }

    @PostMapping(value = "/save")
    String saveDocumentSeries(
            @ModelAttribute("documentSeries") @Valid DocumentSeries documentSeries, Errors errors, Model model, HttpServletRequest request, RedirectAttributes redirectAttributes) {

        if(errors.hasErrors())
            return getModelAndView("documentSeriesDetails", model);

        documentSeries.setCompanyProfile(Utils.getUserFromHttpServletRequest(request).getCompanyProfile());

        try {
            documentSeriesService.addDocumentSeries(documentSeries);
        } catch (DataIntegrityViolationException ex) {
            documentSeries.setId(null);
            model.addAttribute("modelError", messageSource.getMessage("document.series.code.already.exists", null, request.getLocale()));
            return getModelAndView("documentSeriesDetails", model);
        }

        return addSuccessMessageAndRedirect("/document/series/list", messageSource.getMessage("document.series.added.success", null, request.getLocale()), redirectAttributes);
    }

}
