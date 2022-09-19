package com.amazon.pages;

import com.amazon.utils.WebUtils;

public class Pages {

    private WebUtils webUtils;
    private SearchBarPage searchBarPage;
    private EGiftCardPage eGiftCardPage;

    public Pages() {
        this.searchBarPage = new SearchBarPage();
        this.webUtils = new WebUtils();
        this.eGiftCardPage = new EGiftCardPage();
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

}
