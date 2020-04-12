package tests;

import com.codeborne.selenide.Configuration;
import com.mightypetra.testautomation.DriverUtils;
import com.mightypetra.testautomation.User;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import pages.HomePage;
import pages.RegistrationPage;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assumptions.assumeTrue;

@Epic("Registration Tests")
@Feature("General registration")
class RegistrationTest {

    @BeforeAll
    public static void setUp() {
        DriverUtils.setDriver(DriverUtils.CHROME);
        open("http://demo.prestashop.com");
        switchTo().frame("framelive");
    }

    @Test
    @DisplayName("╯°□°）╯")
    @Story("User successfully registers")
    public void successfulUserRegistration() {
        User user = new User();

        HomePage.clickSignIn();
        assumeTrue(HomePage.loginFrom.is(visible), "Login form is not visible");

        HomePage.clickCreateNewAccount();
        assumeTrue(RegistrationPage.registrationFrom.is(visible), "Registration form is not visible");

        RegistrationPage.fillRegistrationForm(user);
        RegistrationPage.checkTOCs();
        RegistrationPage.saveCustomer();

        HomePage.buttonSignOut.waitUntil(visible, Configuration.timeout);
        assertEquals(HomePage.getCurrentUserName(), user.getFullName(), "User name doesn't match");
    }

    @AfterAll
    public static void close() {
        closeWebDriver();
    }

}
