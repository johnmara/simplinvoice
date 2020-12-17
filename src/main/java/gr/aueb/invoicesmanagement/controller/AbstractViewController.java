package gr.aueb.invoicesmanagement.controller;

import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.servlet.ModelAndView;

public class AbstractViewController {

    private static final String MAIN_VIEW = "layout";
    private static final String VIEW_MODEL_ATTRIBUTE = "view";

    public String getModelAndView(String viewName, Model model) {
        model.addAttribute(VIEW_MODEL_ATTRIBUTE, viewName);
        return MAIN_VIEW;
    }


}
