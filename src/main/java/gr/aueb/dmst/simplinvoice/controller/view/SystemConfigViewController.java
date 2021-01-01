package gr.aueb.dmst.simplinvoice.controller.view;

import gr.aueb.dmst.simplinvoice.Utils;
import gr.aueb.dmst.simplinvoice.model.SystemConfig;
import gr.aueb.dmst.simplinvoice.service.SystemConfigService;
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

@Controller
@RequestMapping("/system/config")
public class SystemConfigViewController extends AbstractViewController {

    @Autowired
    SystemConfigService systemConfigService;

    @Autowired
    MessageSource messageSource;

    @GetMapping("/retrieve")
    public String showSystemConfigForm(WebRequest request, Model model) {
        Long companyProfileId = Utils.getUserFromWebRequest(request).getCompanyProfile().getId();

        SystemConfig systemConfig = systemConfigService.getSystemConfigByCompanyProfileId(companyProfileId);
        if (systemConfig == null) {
            systemConfig = new SystemConfig();
        }
        model.addAttribute("systemConfig", systemConfig);
        return getModelAndView("systemConfig", model);
    }

    @PostMapping("/update")
    public String updateSystemConfig(
            @ModelAttribute("systemConfig") @Valid SystemConfig systemConfig, Errors errors, Model model, HttpServletRequest request, RedirectAttributes redirectAttributes) {

        if(errors.hasErrors())
            return getModelAndView("systemConfig", model);

        systemConfig.setCompanyProfile(Utils.getUserFromHttpServletRequest(request).getCompanyProfile());

        systemConfigService.addSystemConfig(systemConfig);

        return addSuccessMessageAndRedirect("/system/config/retrieve", messageSource.getMessage("system.config.update.success", null, request.getLocale()), redirectAttributes);
    }

}