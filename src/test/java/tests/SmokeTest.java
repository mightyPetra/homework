package tests;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.SelenideElement;
import com.mightypetra.testautomation.DriverUtils;
import com.mightypetra.testautomation.UserProfile;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import org.junit.jupiter.api.*;
import pages.*;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assumptions.assumeTrue;

@Epic("Online ShopPage")
@Feature("Smoke")
class SmokeTest {

    @BeforeEach
    public void setUp() {
        DriverUtils.setDriver(DriverUtils.FIREFOX);
        open("http://demo.prestashop.com");
        HomePage.loader.waitWhile(visible, Configuration.timeout);
        switchTo().frame("framelive");
    }

    @Test
    @DisplayName("(╯°□°）╯ Failing Test")
    @Story("User successfully registers")
    public void successfulUserRegistration() {
        UserProfile user = new UserProfile(" ", " ");

        HomePage.clickSignIn();
        assertTrue(HomePage.loginFrom.is(visible), "Login form is not visible");

        HomePage.clickCreateNewAccount();
        assumeTrue(RegistrationPage.registrationFrom.is(visible), "Registration form is not visible");

        RegistrationPage.fillRegistrationForm(user);
        RegistrationPage.checkTOCs();
        assumeTrue(RegistrationPage.registrationFrom.is(visible), "Registration form is not visible");
//        RegistrationPage.saveCustomer();
        assumeTrue(HomePage.buttonSignOut.is(visible), "User was not signed in");
        assertEquals(HomePage.getCurrentUserName(), user.getFullName(), "UserProfile name doesn't match");
    }


    @Test
    @DisplayName("ฅ^•ﻌ•^ฅ Passing Test")
    @Story("Shopping happy path")
    public void shoppingAndCheckoutFlow() {
        UserProfile user = new UserProfile();

        AppGeneral.registerNewUser(user);
        HomePage.openShopPage();
        ShopPage.openShopItemByIndex(1);
        ShopItemPage.addToCart();
        ShoppingCartPage.buttonProceedToCheckout.click();
        CheckoutPage.addressForm.waitUntil(visible, DriverUtils.TIMEOUT);
        CheckoutPage.checkout(user, "PrestaShop", "Pay by bank wire");
        assertEquals(CheckoutPage.getConfirmationMessageFroCurrentUser(user), CheckoutPage.labelConfirmationMessage.getText(), "Message was incorrect");

    }

    @AfterEach
    public void close() {
        HomePage.signOut();
        closeWebDriver();
    }

}
