package gr.aueb.dmst.simplinvoice.controller.view;

import gr.aueb.dmst.simplinvoice.model.DeprecatedProduct;
import gr.aueb.dmst.simplinvoice.model.request.DeprecatedProductPageListRequest;
import gr.aueb.dmst.simplinvoice.service.DeprecatedProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/deprecated/product")
public class DeprecatedProductViewController extends AbstractViewController {

    @Autowired
    DeprecatedProductService deprecatedProductService;

    @ModelAttribute
    DeprecatedProductPageListRequest createProductPageListRequest() {
        return new DeprecatedProductPageListRequest();
    }

    @GetMapping(value = "/list")
    String getProductsList(final Model model) {
        model.addAttribute("productsList", deprecatedProductService.getProductsList());

        return getModelAndView("productsListDatatable", model);
    }

    @GetMapping(value = "/list/paginated")
    public String getProductsListPaginated(
            Model model,
            @ModelAttribute DeprecatedProductPageListRequest deprecatedProductPageListRequest) {

        Page<DeprecatedProduct> productPage = deprecatedProductService.findPaginatedJpa(deprecatedProductPageListRequest, constructPageable(deprecatedProductPageListRequest));

        addPageListToModel(deprecatedProductPageListRequest, productPage, model);
        return getModelAndView("deprecatedProductsList", model);

    }

}
