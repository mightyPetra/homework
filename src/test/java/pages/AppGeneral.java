package pages;

import com.codeborne.selenide.Condition;
import com.mightypetra.testautomation.DriverUtils;
import com.mightypetra.testautomation.User;

public class AppGeneral {

    public static void registerNewUser(User user) {
        HomePage.clickSignIn();
        HomePage.clickCreateNewAccount();
        RegistrationPage.fillRegistrationForm(user);
        RegistrationPage.checkTOCs();
        RegistrationPage.saveCustomer();
        HomePage.buttonSignOut.waitUntil(Condition.visible, DriverUtils.TIMEOUT);
    }


}
