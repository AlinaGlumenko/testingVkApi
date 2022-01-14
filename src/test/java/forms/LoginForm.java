package forms;

import aquality.selenium.browser.AqualityServices;
import aquality.selenium.elements.interfaces.IButton;
import aquality.selenium.elements.interfaces.ICheckBox;
import aquality.selenium.elements.interfaces.ITextBox;
import aquality.selenium.forms.Form;
import org.openqa.selenium.By;

public class LoginForm extends Form {

    private static By locator = By.id("index_login_form");
    private ITextBox loginField = AqualityServices.getElementFactory().getTextBox(By.id("index_email"), "Login field");
    private ITextBox passwordField = AqualityServices.getElementFactory().getTextBox(By.id("index_pass"), "Password field");
    private ICheckBox expireCheckbox = AqualityServices.getElementFactory().getCheckBox(By.id("index_expire"), "Expire checkbox");
    private IButton loginButton = AqualityServices.getElementFactory().getButton(By.id("index_login_button"), "Login button");

    public LoginForm() {
        super(locator, "Login form");
    }

    public void enterLogin(String login) {
        loginField.clearAndType(login);
    }

    public void enterPassword(String password) {
        passwordField.clearAndTypeSecret(password);
    }

    public void selectCheckbox() {
        expireCheckbox.click();
    }

    public void clickLoginButton() {
        loginButton.click();
    }
}
