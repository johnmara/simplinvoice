package gr.aueb.invoicesmanagement.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class DashboardViewController extends AbstractViewController {

    @GetMapping(value = {"/", "/dashboard"})
    String getDashboardView(final Model model) {
        return getModelAndView("dashboard", model);
    }

}
