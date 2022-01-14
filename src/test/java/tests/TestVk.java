package tests;

import aquality.selenium.browser.AqualityServices;
import forms.FeedForm;
import forms.LoginForm;
import forms.ProfileForm;
import models.Comment;
import models.Image;
import models.Post;
import models.User;
import org.apache.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import utils.*;

import java.io.File;

public class TestVk {

    private String postImagePath = "./src/test/resources/pic.jpg";
    private String newImagePath = "./src/test/resources/pic2.jpg";
    private File image = new File(postImagePath);

    private static final int MESSAGE_LENGTH = 15;
    private static final int NEW_MESSAGE_LENGTH = 20;
    private static final int COMMENT_LENGTH = 20;

    @BeforeMethod
    public void beforeMethod() {
        AqualityServices.getBrowser().maximize();
    }

    @Test
    public void testVk() {
        Logger.getRootLogger().info("[UI] Step 1 - go to " + TestData.get("url"));
        AqualityServices.getBrowser().goTo(TestData.get("url"));

        User user = DataHandler.asObject(TestData.get("user"), User.class);
        LoginForm loginForm = new LoginForm();

        Assert.assertTrue(loginForm.state().waitForDisplayed(), "Login form doesn't open");

        Logger.getRootLogger().info("[UI] Step 2 - login");
        loginForm.enterLogin(user.getLogin());
        loginForm.enterPassword(user.getPassword());
        loginForm.selectCheckbox();
        loginForm.clickLoginButton();

        FeedForm feedForm = new FeedForm();
        Assert.assertTrue(feedForm.state().waitForDisplayed(), "Feed form doesn't open");

        Logger.getRootLogger().info("[UI] Step 3 - go to 'My profile'");
        feedForm.clickProfileLink();

        ProfileForm profileForm = new ProfileForm();
        Assert.assertTrue(profileForm.state().waitForDisplayed(), "Profile form doesn't open");

        Logger.getRootLogger().info("[API] Step 4 - create VK post");
        String messageText = DataGenerator.generateText(MESSAGE_LENGTH);
        String postId = VkApiUtils.createPost(user.getToken(), user.getId(), messageText);

        Logger.getRootLogger().info("[UI] Step 5 - make sure there is a post on the wall with the correct text from the correct user");
        Post post = profileForm.getWallPost(user.getId(), postId);
        Assert.assertEquals(post.getMessageText(), messageText, "Post message text doesn't match");
        Assert.assertEquals(post.getAuthorName(), user.getUsername(), "Post author name doesn't match");

        Logger.getRootLogger().info("[API] Step 6 - edit a post via an API request - change the text and add (upload) picture");
        String newMessageText = DataGenerator.generateText(NEW_MESSAGE_LENGTH);
        postId = VkApiUtils.editPost(user.getToken(), user.getId(), postId, newMessageText, image);

        Logger.getRootLogger().info("[UI] Step 7 - make sure that the message text has changed and the uploaded picture has been added (make sure the pictures are the same)");
        post = profileForm.getWallPost(user.getId(), postId);
        Assert.assertNotEquals(post.getMessageText(), messageText, "Message text matches");

        Post postWithImage = VkApiUtils.getPostById(user.getToken(), user.getId(), postId);
        Image vkImage = postWithImage.getAttachments()[0].getPhoto();
        File downloadedImage = APIUtils.downloadFile(vkImage.getSizes()[vkImage.getSizes().length-1].getUrl(), newImagePath);
        try {
            Assert.assertTrue(ImageComparator.compare(downloadedImage, image), "Image doesn't match");
        } catch (Exception e) {
            Logger.getRootLogger().error(e);
            throw new RuntimeException(e);
        }

        Logger.getRootLogger().info("[API] Step 8 - add a comment to a post with random text");
        String commentMessage = DataGenerator.generateText(COMMENT_LENGTH);
        String commentId = VkApiUtils.createComment(user.getToken(), user.getId(), postId, commentMessage);

        Logger.getRootLogger().info("[UI] Step 9 - make sure that a comment from the correct user has been added to the desired post");
        Comment comment = profileForm.getPostComment(user.getId(), postId, commentId);
        Assert.assertEquals(comment.getAuthorName(), user.getUsername(), "Comment message text doesn't match");
        Assert.assertEquals(comment.getMessageText(), commentMessage, "Comment author name doesn't match");

        Logger.getRootLogger().info("[UI] Step 10 - leave a like on the post");
        profileForm.likePost(user.getId(), postId);

        Logger.getRootLogger().info("[API] Step 11 - make sure the post has a like from the correct user");
        Assert.assertTrue(VkApiUtils.isPostLikedByUser(user.getToken(), user.getId(), postId), "Post is not liked by this user");

        Logger.getRootLogger().info("[API] Step 12 - delete created post");
        Assert.assertEquals(VkApiUtils.deletePostById(user.getToken(), user.getId(), postId), "1", "Post is not deleted");
        Logger.getRootLogger().info("[UI] Step 13 - make sure the post is deleted");
        Assert.assertTrue(profileForm.postIsNotPresent(user.getId(), postId), "Post is present");
    }

    @AfterMethod
    public void afterMethod() {
        AqualityServices.getBrowser().quit();
    }
}
