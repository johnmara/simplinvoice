package gr.aueb.invoicesmanagement.model.request;

import org.springframework.util.ObjectUtils;

import java.util.Arrays;
import java.util.List;

public class PageListRequest<T> {

    private static final Integer DEFAULT_PAGE_SIZE = 5;
    private static final String DEFAULT_SORT_DIRECTION = "DESC";

    private Integer page;
    private Integer size;
    private String sortBy;
    private String asc;
    private Integer totalPages;
    private Integer totalElements;
    private List<T> content;

    public Integer getPage() {
        return page != null ? page : 1;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getSize() {
        return size != null ? size : DEFAULT_PAGE_SIZE;
    }

    public void setSize(Integer size) {
        this.size = size;
    }

    public String getSortBy() {
        return sortBy;
    }

    public void setSortBy(String sortBy) {
        this.sortBy = sortBy;
    }

    public String getAsc() {
        return asc != null ? asc : DEFAULT_SORT_DIRECTION;
    }

    public void setAsc(String asc) {
        this.asc = asc;
    }

    public Integer getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(Integer totalPages) {
        this.totalPages = totalPages;
    }

    public Integer getTotalElements() {
        return totalElements;
    }

    public void setTotalElements(Integer totalElements) {
        this.totalElements = totalElements;
    }

    public List<T> getContent() {
        return content;
    }

    public void setContent(List<T> content) {
        this.content = content;
    }

    public String toQueryString(String[] paramsToIgnore) {
        StringBuilder queryBuilder = new StringBuilder();

        queryBuilder.append(toQueryParam(paramsToIgnore, "page", getPage()));
        queryBuilder.append(toQueryParam(paramsToIgnore, "size", getSize()));
        if(getSortBy() != null) {
            queryBuilder.append(toQueryParam(paramsToIgnore, "sortBy", getSortBy()));
            queryBuilder.append(toQueryParam(paramsToIgnore, "asc", getAsc()));
        }

        return queryBuilder.toString();
    }

    protected String toQueryParam(String[] paramsToIgnore, String param, Object value) {
        String validParam = "&" + param + "=" + value;

        if(Arrays.stream(paramsToIgnore).anyMatch(param::equals) || ObjectUtils.isEmpty(value))
            return "";
        else
            return validParam;


    }

}
