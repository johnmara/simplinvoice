package gr.aueb.dmst.simplinvoice.controller.view;

import gr.aueb.dmst.simplinvoice.Constants;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class DashboardViewController extends AbstractViewController {

    @GetMapping(value = {"/", "/dashboard"})
    String getDashboardView(final Model model) {
        System.out.println(Constants.aadeInvoiceTypeMap);
        return getModelAndView("dashboard", model);
    }

}
