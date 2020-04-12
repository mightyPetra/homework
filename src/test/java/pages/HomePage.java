package pages;


import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selectors.byId;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.$;

public class HomePage {

    private static final SelenideElement buttonToggleHeader = $("a.btn.btn-collapse");
    public static final SelenideElement buttonSignIn = $("div.user-info");
    public static final SelenideElement buttonSignOut = $(byText("Sign out"));

    private static final SelenideElement labelUserInformation = $("a.account span");

    public static final SelenideElement loginFrom = $(byId("login-form"));
    private static final SelenideElement linkCreateNewAccount = $("div.no-account a");


    public static void hideHeader() {
        buttonToggleHeader.waitUntil(visible, Configuration.timeout);
        buttonToggleHeader.click();
    }

    public static void clickSignIn() {
        buttonSignIn.waitUntil(visible, Configuration.timeout);
        buttonSignIn.click();
        loginFrom.waitUntil(visible, Configuration.timeout);
    }

    public static void clickSignOut() {
        buttonSignOut.click();
    }

    public static void clickCreateNewAccount() {
        linkCreateNewAccount.click();
    }

    public static String getCurrentUserName() {
        return labelUserInformation.getText();
    }


}
