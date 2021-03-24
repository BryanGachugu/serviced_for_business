package com.gachugusville.servicedforbusiness.Utils;

import java.util.List;

public class ServiceCategoryList {
    String service_category_name, image_url;
    List<String> category_tags;

    public ServiceCategoryList() {
        //For firebase
    }

    public ServiceCategoryList(String service_category_name, String image_url, List<String> category_tags) {
        this.service_category_name = service_category_name;
        this.image_url = image_url;
        this.category_tags = category_tags;
    }

    public String getService_category_name() {
        return service_category_name;
    }

    public String getImage_url() {
        return image_url;
    }

    public List<String> getCategory_tags() {
        return category_tags;
    }
}
