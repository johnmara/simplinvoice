package gr.aueb.dmst.simplinvoice.controller.view;

import gr.aueb.dmst.simplinvoice.Utils;
import gr.aueb.dmst.simplinvoice.service.DashboardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;

@Controller
public class DashboardViewController extends AbstractViewController {

    @Autowired
    DashboardService dashboardService;

    @GetMapping(value = {"/", "/dashboard"})
    String getDashboardView(HttpServletRequest request, final Model model) {
        Long companyProfileId = retrieveCompanyProfileId(Utils.getUserFromHttpServletRequest(request));

        model.addAttribute("totalRevenue", dashboardService.calculateTotalRevenue(companyProfileId));
        model.addAttribute("totalExpenses", dashboardService.calculateTotalExpenses(companyProfileId));
        model.addAttribute("revenueByMonthMap", dashboardService.constructRevenueByMonthData(companyProfileId, request.getLocale()));
        model.addAttribute("expensesByMonthMap", dashboardService.constructExpensesByMonthData(companyProfileId, request.getLocale()));
        model.addAttribute("invoicesBySupplierMap", dashboardService.constructSuppliersInvoicesData(companyProfileId));
        model.addAttribute("productsSalesMap", dashboardService.constructProductsSalesData(companyProfileId));

        return getModelAndView("dashboard", model);
    }

}
