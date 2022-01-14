package forms;

import aquality.selenium.browser.AqualityServices;
import aquality.selenium.elements.interfaces.ILink;
import aquality.selenium.forms.Form;
import org.openqa.selenium.By;

public class FeedForm extends Form {

    private static By locator = By.id("feed_rmenu");
    private ILink profileLink = AqualityServices.getElementFactory().getLink(By.id("l_pr"), "Profile link");

    public FeedForm() {
        super(locator, "Feed form");
    }

    public void clickProfileLink() {
        profileLink.click();
    }
}
