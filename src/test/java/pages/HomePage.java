package pages;


import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.SelenideElement;
import com.mightypetra.testautomation.DriverUtils;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selectors.*;
import static com.codeborne.selenide.Selenide.$;

public class HomePage {

    private static final SelenideElement buttonToggleHeader = $("a.btn.btn-collapse");
    public static final SelenideElement buttonSignIn = $("div.user-info");
    public static final SelenideElement buttonSignOut = $(byText("Sign out"));

    public static final SelenideElement loader = $("#loadingMessage");

    private static final SelenideElement labelUserInformation = $("a.account span");

    public static final SelenideElement loginFrom = $("#login-form");
    private static final SelenideElement linkCreateNewAccount = $("div.no-account a");

    private static final SelenideElement linkShowAllProducts = $("a.all-product-link");


    public static void hideHeader() {
        buttonToggleHeader.waitUntil(visible, Configuration.timeout);
        buttonToggleHeader.click();
    }

    public static void clickSignIn() {
        buttonSignIn.waitUntil(visible, Configuration.timeout);
        buttonSignIn.click();
        loginFrom.waitUntil(visible, DriverUtils.TIMEOUT);
    }

    public static void signOut() {
        buttonSignOut.click();
        buttonSignIn.waitUntil(visible, DriverUtils.TIMEOUT);
    }

    public static void clickCreateNewAccount() {
        linkCreateNewAccount.click();
    }

    public static String getCurrentUserName() {
        return labelUserInformation.getText();
    }

    public static void openShopPage() {
        linkShowAllProducts.click();
        ShopPage.productsForm.waitUntil(visible, DriverUtils.TIMEOUT);
    }


}
