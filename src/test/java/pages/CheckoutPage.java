package pages;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.SelenideElement;
import com.mightypetra.testautomation.DriverUtils;
import com.mightypetra.testautomation.UserProfile;
import org.junit.jupiter.api.Assertions;

import static com.codeborne.selenide.Selenide.$;

public class CheckoutPage {

    public static final SelenideElement addressForm = $("#checkout-addresses-step");
    private static final SelenideElement inputAddress = addressForm.$("input[name='address1']");
    private static final SelenideElement inputZip = addressForm.$("input[name='postcode']");
    private static final SelenideElement inputCity = addressForm.$("input[name='city']");

    private static final SelenideElement buttonSubmitAddress = addressForm.$("div.clearfix > button[type=submit]");

    private static final SelenideElement shippingForm = $("#checkout-delivery-step");
    private static final SelenideElement buttonSubmitShipping = shippingForm.$("button[type=submit]");

    private static final SelenideElement paymentForm = $("#checkout-payment-step");
    private static final SelenideElement paymentOptions = paymentForm.$("div.payment-options");
    private static final SelenideElement checkboxTOS = paymentForm.$("#conditions_to_approve\\[terms-and-conditions\\]");

    private static final SelenideElement buttonOrder = $("#payment-confirmation button");

    private static final SelenideElement orderConfirmationForm = $("#content-hook_order_confirmation");
    public static final SelenideElement labelConfirmationMessage = orderConfirmationForm.$("p");

    private static final String confirmationMessageTemplate = "An email has been sent to the %s address.";


    public static void fillCheckoutForm(UserProfile user) {
        inputAddress.setValue(user.getAddress());
        inputZip.setValue(user.getZip());
        inputCity.setValue(user.getCity()).submit();
        buttonSubmitAddress.waitUntil(Condition.visible, DriverUtils.TIMEOUT).click();
    }

    public static void skipSelectingShippingMethod() {
        buttonSubmitShipping.click();
    }

    public static void selectPaymentOption(String option) {
        switch (option.toLowerCase()) {
            case "pay by check":
                paymentOptions.$("#payment-option-1").click();
                break;
            case "pay by bank wire":
                paymentOptions.$("#payment-option-2").click();
                break;
            default:
                Assertions.fail("unknown option");
        }
    }

    public static void checkout(UserProfile user, String shippingMethod, String paymentOption) {
        fillCheckoutForm(user);
        skipSelectingShippingMethod();
        selectPaymentOption(paymentOption);
        checkboxTOS.click();
        buttonOrder.click();
        orderConfirmationForm.waitUntil(Condition.visible, Configuration.timeout);
    }

    public static String getConfirmationMessageFroCurrentUser(UserProfile user) {
        return String.format(confirmationMessageTemplate, user.getEmail());
    }
}
