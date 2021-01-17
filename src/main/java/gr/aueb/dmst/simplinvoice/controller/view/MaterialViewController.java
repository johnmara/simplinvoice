package gr.aueb.dmst.simplinvoice.controller.view;

import gr.aueb.dmst.simplinvoice.Utils;
import gr.aueb.dmst.simplinvoice.model.Material;
import gr.aueb.dmst.simplinvoice.service.MaterialService;
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
@RequestMapping("/material")
public class MaterialViewController extends AbstractViewController {

    @Autowired
    MaterialService materialService;

    @Autowired
    MessageSource messageSource;

    @GetMapping(value = "/list")
    String getMaterialsList(WebRequest request, final Model model) {
        Long companyProfileId = retrieveCompanyProfileId(Utils.getUserFromWebRequest(request));

        model.addAttribute("materialsList", materialService.getMaterialsList(companyProfileId));

        return getModelAndView("materialsList", model);
    }

    @GetMapping(value = {"/", "/{id}"})
    String getMaterials(@PathVariable(required = false) Long id, WebRequest request, final Model model) {
        Long companyProfileId = retrieveCompanyProfileId(Utils.getUserFromWebRequest(request));

        Material material;

        if(id != null) {
            material = materialService.getMaterialById(id, companyProfileId);
        } else {
            material = new Material();
        }

        model.addAttribute("material", material);

        return getModelAndView("materialDetails", model);
    }

    @DeleteMapping(value = "/delete/{id}")
    String deleteMaterial(@PathVariable Long id, WebRequest request, final Model model, RedirectAttributes redirectAttributes) {
        Long companyProfileId = retrieveCompanyProfileId(Utils.getUserFromWebRequest(request));

        materialService.deleteMaterial(id, companyProfileId);
        return addSuccessMessageAndRedirect("/material/list", messageSource.getMessage("material.deleted.success", null, request.getLocale()), redirectAttributes);
    }

    @PostMapping(value = "/save")
    String saveMaterial(
            @ModelAttribute("material") @Valid Material material, Errors errors, Model model, HttpServletRequest request, RedirectAttributes redirectAttributes) {

        if(errors.hasErrors())
            return getModelAndView("materialDetails", model);

        material.setCompanyProfile(Utils.getUserFromHttpServletRequest(request).getCompanyProfile());

        try {
            materialService.addMaterial(material);
        } catch (DataIntegrityViolationException ex) {
            material.setId(null);
            model.addAttribute("modelError", messageSource.getMessage("material.code.already.exists", null, request.getLocale()));
            return getModelAndView("materialDetails", model);
        }

        return addSuccessMessageAndRedirect("/material/list", messageSource.getMessage("material.added.success", null, request.getLocale()), redirectAttributes);
    }

}
