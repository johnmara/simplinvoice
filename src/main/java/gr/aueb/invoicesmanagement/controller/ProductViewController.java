package gr.aueb.invoicesmanagement.controller;

import gr.aueb.invoicesmanagement.model.request.PageListRequest;
import gr.aueb.invoicesmanagement.model.Product;
import gr.aueb.invoicesmanagement.model.request.ProductPageListRequest;
import gr.aueb.invoicesmanagement.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/product")
public class ProductViewController extends AbstractViewController {

    @Autowired
    ProductService productService;

    @ModelAttribute
    ProductPageListRequest createProductPageListRequest() {
        return new ProductPageListRequest();
    }

    @GetMapping(value = "/list")
    String getProductsList(final Model model) {
        model.addAttribute("productsList", productService.getProductsList());

        return getModelAndView("productsListDatatable", model);
    }

    @GetMapping(value = "/list/paginated")
    public String getProductsListPaginated(
            Model model,
            @ModelAttribute ProductPageListRequest productPageListRequest) {

        Page<Product> productPage = productService.findPaginatedJpa(productPageListRequest, constructPageable(productPageListRequest));

        addPageListToModel(productPageListRequest, productPage, model);
        return getModelAndView("productsListPaginated", model);

    }

}
