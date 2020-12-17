package gr.aueb.invoicesmanagement.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/products")
public class ProductsViewController extends AbstractViewController {

    @GetMapping(value = "/list")
    String getProductsList(final Model model) {
        return getModelAndView("productsList", model);
    }

}
