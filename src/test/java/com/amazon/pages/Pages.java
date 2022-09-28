package com.amazon.pages;

import com.amazon.utils.WebUtils;

public class Pages {

    private WebUtils webUtils;
    private SearchBarPage searchBarPage;
    private EGiftCardPage eGiftCardPage;
    private APIResponse apiResponse;

    public Pages() {
        this.searchBarPage = new SearchBarPage();
        this.webUtils = new WebUtils();
        this.eGiftCardPage = new EGiftCardPage();
        this.apiResponse = new APIResponse();
    }

    public WebUtils webUtils() {
        return webUtils;
    }

    public SearchBarPage searchBarPage() {
        return searchBarPage;
    }

    public EGiftCardPage eGiftCardPage() {
        return eGiftCardPage;
    }

    public APIResponse apiResponse() {
        return apiResponse;
    }
}
