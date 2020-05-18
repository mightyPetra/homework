package pages;

import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Selenide.$;

public class ShoppingCartPage {

    public static final SelenideElement buttonProceedToCheckout = $("div.checkout a.btn-primary");

}
