package gr.aueb.invoicesmanagement.model.request;

import gr.aueb.invoicesmanagement.model.DeprecatedProduct;

public class DeprecatedProductPageListRequest extends PageListRequest<DeprecatedProduct> {

    private String name;
    private String category;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    @Override
    public String toQueryString(String[] paramsToIgnore) {
        StringBuilder queryBuilder = new StringBuilder();

        queryBuilder.append(super.toQueryString(paramsToIgnore));
        queryBuilder.append(toQueryParam(paramsToIgnore, "name", getName()));
        queryBuilder.append(toQueryParam(paramsToIgnore, "category", getCategory()));

        return queryBuilder.toString();
    }

}
