package gr.aueb.dmst.simplinvoice.controller.view;

import gr.aueb.dmst.simplinvoice.Utils;
import gr.aueb.dmst.simplinvoice.model.CompanyProfile;
import gr.aueb.dmst.simplinvoice.model.User;
import gr.aueb.dmst.simplinvoice.service.CompanyProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.Objects;

@Controller
@RequestMapping("/company/profile")
public class CompanyProfileViewController extends AbstractViewController {

    @Autowired
    CompanyProfileService companyProfileService;

    @Autowired
    MessageSource messageSource;

    @GetMapping("/retrieve")
    public String showCompanyProfileForm(WebRequest request, Model model) {
        User user = Objects.requireNonNull(Utils.getUserFromWebRequest(request));
        CompanyProfile companyProfile = companyProfileService.getCompanyProfileByUserId(user.getId());
        if (companyProfile == null) {
            companyProfile = new CompanyProfile();
        }
        model.addAttribute("companyProfile", companyProfile);
        return getModelAndView("companyProfile", model);
    }

    @PostMapping("/update")
    public String updateCOmpanyProfile(
            @ModelAttribute("companyProfile") @Valid CompanyProfile companyProfile, Errors errors, Model model, HttpServletRequest request, RedirectAttributes redirectAttributes) {

        if(errors.hasErrors())
            return getModelAndView("companyProfile", model);

        companyProfile.setUser(Utils.getUserFromHttpServletRequest(request));
        companyProfileService.addCompanyProfile(companyProfile);

        return addSuccessMessageAndRedirect("/company/profile/retrieve", messageSource.getMessage("configure.company.success", null, request.getLocale()), redirectAttributes);
    }

}