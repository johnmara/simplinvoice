package gr.aueb.dmst.simplinvoice.controller.view;

import gr.aueb.dmst.simplinvoice.Constants;
import gr.aueb.dmst.simplinvoice.Utils;
import gr.aueb.dmst.simplinvoice.enums.TraderType;
import gr.aueb.dmst.simplinvoice.model.Trader;
import gr.aueb.dmst.simplinvoice.service.TraderService;
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
@RequestMapping("/trader")
public class TraderViewController extends AbstractViewController {

    @Autowired
    TraderService traderService;

    @Autowired
    MessageSource messageSource;

    @GetMapping(value = "/list")
    String getTradersList(@RequestParam(value = "type") TraderType traderType, WebRequest request, final Model model) {
        Long companyProfileId = Utils.getUserFromWebRequest(request).getCompanyProfile().getId();

        model.addAttribute("type", traderType);
        model.addAttribute("tradersList", traderService.getTradersList(traderType, companyProfileId));

        return getModelAndView("tradersList", model);
    }

    @GetMapping(value = {"/", "/{id}"})
    String getTrader(@PathVariable(required = false) Long id, @RequestParam(value = "type", required = false) TraderType traderType, WebRequest request, final Model model) {
        Long companyProfileId = Utils.getUserFromWebRequest(request).getCompanyProfile().getId();

        Trader trader;

        if(id != null) {
            trader = traderService.getTraderById(id, companyProfileId);
        } else {
            trader = new Trader();
            trader.setType(traderType);
            System.out.println(Constants.countries);
        }

        model.addAttribute("trader", trader);

        return getModelAndView("traderDetails", model);
    }

    @DeleteMapping(value = "/delete/{id}")
    String deleteTrader(@PathVariable Long id, WebRequest request, final Model model, RedirectAttributes redirectAttributes) {
        Long companyProfileId = Utils.getUserFromWebRequest(request).getCompanyProfile().getId();

        Trader trader = traderService.getTraderById(id, companyProfileId);
        traderService.deleteTrader(id, companyProfileId);
        redirectAttributes.addAttribute("type", trader.getType());
        return addSuccessMessageAndRedirect("/trader/list", messageSource.getMessage("trader.deleted.success", null, request.getLocale()), redirectAttributes);
    }

    @PostMapping(value = "/save")
    String saveTrader(
            @ModelAttribute("trader") @Valid Trader trader, Errors errors, Model model, HttpServletRequest request, RedirectAttributes redirectAttributes) {

        if(errors.hasErrors())
            return getModelAndView("traderDetails", model);

        trader.setCompanyProfile(Utils.getUserFromHttpServletRequest(request).getCompanyProfile());

        try {
            traderService.addTrader(trader);
        } catch (DataIntegrityViolationException ex) {
            trader.setId(null);
            model.addAttribute("modelError", messageSource.getMessage("trader.code.already.exists", null, request.getLocale()));
            return getModelAndView("traderDetails", model);
        }

        redirectAttributes.addAttribute("type", trader.getType());

        return addSuccessMessageAndRedirect("/trader/list", messageSource.getMessage("trader.added.success", null, request.getLocale()), redirectAttributes);
    }

}
