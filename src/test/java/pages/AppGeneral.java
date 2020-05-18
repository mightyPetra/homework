package pages;

import com.codeborne.selenide.Condition;
import com.mightypetra.testautomation.DriverUtils;
import com.mightypetra.testautomation.UserProfile;

import static com.codeborne.selenide.Selenide.$;

public class AppGeneral {

    public static void registerNewUser(UserProfile user) {
        HomePage.clickSignIn();
        HomePage.clickCreateNewAccount();
        RegistrationPage.fillRegistrationForm(user);
        RegistrationPage.checkTOCs();
        RegistrationPage.saveCustomer();
        HomePage.buttonSignOut.waitUntil(Condition.visible, DriverUtils.TIMEOUT);
    }


}
