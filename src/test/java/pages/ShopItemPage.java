package pages;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import com.mightypetra.testautomation.DriverUtils;

import static com.codeborne.selenide.Selenide.$;

public class ShopItemPage {

    private static final SelenideElement labelItemTitle = $("h1.h1");
    private static final SelenideElement buttonAddToCart = $("button.add-to-cart");

    private static final SelenideElement modalAddedToCart = $("#blockcart-modal div.modal-content");
    private static final SelenideElement buttonProceedToCheckout = modalAddedToCart.$("a.btn-primary");

    public static void addToCart(){
        buttonAddToCart.click();
        modalAddedToCart.waitUntil(Condition.visible, DriverUtils.TIMEOUT);
        buttonProceedToCheckout.click();
        ShoppingCartPage.buttonProceedToCheckout.waitUntil(Condition.visible, DriverUtils.TIMEOUT);
    }
}
