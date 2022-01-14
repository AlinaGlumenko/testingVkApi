package forms;

import aquality.selenium.browser.AqualityServices;
import aquality.selenium.forms.Form;
import models.Comment;
import models.Post;
import org.openqa.selenium.By;

public class ProfileForm extends Form {

    private static By locator = By.id("page_info_wrap");
    private String postAuthorNameTextBoxXpath = "//div[@id='post%s_%s']//a[contains(@class, 'author') and not(contains(@class, '_post_field_author'))]";
    private String postMessageTextBoxXpath = "//div[@id='post%s_%s']//div[contains(@class, 'wall_post_text')]";
    private String showCommentsLinkXpath = "//div[@id='replies%s_%s']//a[contains(@class, 'replies_next')]";
    private String commentAuthorNameTextBoxXpath = "//div[@id='post%s_%s']//a[contains(@class, 'author') and not(contains(@class, '_post_field_author'))]";
    private String commentMessageTextBoxXpath = "//div[@id='wpt%s_%s']//div[contains(@class, 'wall_reply_text')]";
    private String likePostButtonXpath = "//div[@id='post%s_%s']//span[contains(@class, '_like_button_icon')]";

    public ProfileForm() {
        super(locator, "Profile form");
    }

    public Post getWallPost(String userId, String postId) {
        Post post = new Post();
        post.setAuthorName(AqualityServices.getElementFactory().getTextBox(By.xpath(String.format(postAuthorNameTextBoxXpath, userId, postId)), "Post author name text box").getText());
        post.setMessageText(AqualityServices.getElementFactory().getTextBox(By.xpath(String.format(postMessageTextBoxXpath, userId, postId)), "Post message text box").getText());
        return post;
    }

    public Comment getPostComment(String userId, String postId, String commentId) {
        AqualityServices.getElementFactory().getLink(By.xpath(String.format(showCommentsLinkXpath, userId, postId)), "Show comments link").click();
        Comment comment = new Comment();
        comment.setAuthorName(AqualityServices.getElementFactory().getTextBox(By.xpath(String.format(commentAuthorNameTextBoxXpath, userId, commentId)), "Comment author name text box").getText());
        comment.setMessageText(AqualityServices.getElementFactory().getTextBox(By.xpath(String.format(commentMessageTextBoxXpath, userId, commentId)), "Comment message text box").getText());
        return comment;
    }

    public void likePost(String userId, String postId) {
        AqualityServices.getElementFactory().getButton(By.xpath(String.format(likePostButtonXpath, userId, postId)), "Like post button").click();
    }

    public boolean postIsNotPresent(String userId, String postId) {
        return AqualityServices.getElementFactory().getTextBox(By.xpath(String.format(postAuthorNameTextBoxXpath, userId, postId)), "Post author name text box").state().waitForNotDisplayed();
    }
}

