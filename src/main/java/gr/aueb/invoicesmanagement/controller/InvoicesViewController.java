package gr.aueb.invoicesmanagement.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/invoices")
public class InvoicesViewController extends AbstractViewController {

    @GetMapping(value = "/issued/list")
    String getIssuedInvoices(final Model model) {
        return getModelAndView("issuedInvoicesList", model);
    }

    @GetMapping(value = "/create")
    String getCreateInvoicePage(final Model model) {
        return getModelAndView("invoice", model);
    }

}
