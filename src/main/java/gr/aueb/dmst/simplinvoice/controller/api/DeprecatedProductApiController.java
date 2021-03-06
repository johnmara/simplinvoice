package gr.aueb.dmst.simplinvoice.controller.api;

import gr.aueb.dmst.simplinvoice.model.DeprecatedProduct;
import gr.aueb.dmst.simplinvoice.service.DeprecatedProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/api/deprecated/product")
public class DeprecatedProductApiController {

    @Autowired
    DeprecatedProductService deprecatedProductService;

    @PostMapping(value = "/add")
    public String addProduct(@RequestBody DeprecatedProduct deprecatedProduct) {
        return deprecatedProductService.addProduct(deprecatedProduct).getId().toString();
    }

    @GetMapping(value = "/list")
    public Object getProducts() {
        return deprecatedProductService.getProductsList();
    }

    @GetMapping(value = "/{id}")
    public DeprecatedProduct getProductById(@PathVariable Long id) {
        return deprecatedProductService.getProductById(id);
    }

}
