package pages;

import com.codeborne.selenide.SelenideElement;
import com.mightypetra.testautomation.User;
import org.openqa.selenium.By;

import static com.codeborne.selenide.Selectors.*;
import static com.codeborne.selenide.Selenide.$;

public class RegistrationPage {

    public static final SelenideElement registrationFrom = $(byClassName("register-form"));
    private static final SelenideElement radioGender = $(registrationFrom, byName("id_gender"));
    private static final SelenideElement inputName = $(registrationFrom, byName("firstname"));
    private static final SelenideElement inputSurname = $(registrationFrom, byName("lastname"));
    private static final SelenideElement inputEmail = $(registrationFrom, byName("email"));
    private static final SelenideElement inputPassword = $(registrationFrom, byName("password"));
    private static final SelenideElement checkboxGdpr = $(registrationFrom, byName("psgdpr"));

    private static final SelenideElement buttonSave = $(registrationFrom, By.xpath(".//button[@data-link-action='save-customer']"));

    public static void fillRegistrationForm(User user){
        inputName.setValue(user.getName());
        inputSurname.setValue(user.getSurname());
        inputEmail.setValue(user.getEmail());
        inputPassword.setValue(user.getPassword());
    }

    public static void checkTOCs(){
        checkboxGdpr.click();
    }

    public static void saveCustomer(){
        buttonSave.click();
    }
}
