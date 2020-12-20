package gr.aueb.invoicesmanagement.controller;

import gr.aueb.invoicesmanagement.model.request.PageListRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.ui.Model;
import org.springframework.util.ObjectUtils;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class AbstractViewController {

    private static final String MAIN_VIEW = "layout";
    private static final String VIEW_MODEL_ATTRIBUTE = "view";


    public String getModelAndView(String viewName, Model model) {
        model.addAttribute(VIEW_MODEL_ATTRIBUTE, viewName);
        return MAIN_VIEW;
    }

    public Pageable constructPageable(PageListRequest pageListRequest) {
        int currentPage = pageListRequest.getPage();
        int pageSize = pageListRequest.getSize();
        if(!ObjectUtils.isEmpty(pageListRequest.getSortBy())) {
            Sort.Direction direction = Sort.Direction.fromString(pageListRequest.getAsc());

            return PageRequest.of(currentPage - 1, pageSize, direction, pageListRequest.getSortBy());
        } else {
            return PageRequest.of(currentPage - 1, pageSize);
        }

    }

    public <T> void addPageListToModel(PageListRequest<T> pageListRequest, Page<T> page, Model model) {
        pageListRequest.setPage(page.getNumber());
        pageListRequest.setSize(page.getSize());
        pageListRequest.setTotalPages(page.getTotalPages());
        pageListRequest.setTotalElements((int) page.getTotalElements());
        if(page.getSort().isSorted()) {
            pageListRequest.setSortBy(page.getSort().get().collect(Collectors.toList()).get(0).getProperty());
            pageListRequest.setAsc(page.getSort().get().collect(Collectors.toList()).get(0).getDirection().name());
        }
        pageListRequest.setContent(page.getContent());

        model.addAttribute("pageListRequest", pageListRequest);

        int totalPages = page.getTotalPages();
        if (totalPages > 0) {
            List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages)
                    .boxed()
                    .collect(Collectors.toList());
            model.addAttribute("pageNumbers", pageNumbers);
        }
    }

}
