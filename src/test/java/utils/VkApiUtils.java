package utils;

import models.Image;
import models.ImageServer;
import models.Post;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

public class VkApiUtils {

    public static String createPost(String token, String userId, String text) {
        VkApiUrlBuilder vkApiUrlBuilder = new VkApiUrlBuilder(VkApiUrlBuilder.VkApiMethods.WALL_POST, token);
        vkApiUrlBuilder.addParam(VkApiConstants.OWNER_ID, userId);
        vkApiUrlBuilder.addParam(VkApiConstants.MESSAGE, text);
        APIUtils.get(vkApiUrlBuilder.getFullUrl());
        return APIUtils.getJsonValueFromResponse(String.format(VkApiConstants.RESPONSE_PATH_TO_PARAM, VkApiConstants.POST_ID));
    }

    public static String editPost(String token, String userId, String postId, String newMessage, File imageFile) {
        Image image = saveWallPhoto(token, userId, imageFile);

        VkApiUrlBuilder vkApiUrlBuilder = new VkApiUrlBuilder(VkApiUrlBuilder.VkApiMethods.WALL_EDIT, token);
        vkApiUrlBuilder.addParam(VkApiConstants.OWNER_ID, userId);
        vkApiUrlBuilder.addParam(VkApiConstants.POST_ID, postId);
        vkApiUrlBuilder.addParam(VkApiConstants.MESSAGE, newMessage);
        vkApiUrlBuilder.addParam(VkApiConstants.ATTACHMENTS, String.format(VkApiConstants.POST_PHOTO_ID_PATTERN, image.getOwnerId(), image.getId()));
        APIUtils.post(vkApiUrlBuilder.getFullUrl());
        return APIUtils.getJsonValueFromResponse(String.format(VkApiConstants.RESPONSE_PATH_TO_PARAM, VkApiConstants.POST_ID));
    }

    private static String getWallUploadServer(String token) {
        VkApiUrlBuilder vkApiUrlBuilder = new VkApiUrlBuilder(VkApiUrlBuilder.VkApiMethods.PHOTOS_GET_WALL_UPLOAD_SERVER, token);
        APIUtils.get(vkApiUrlBuilder.getFullUrl());
        return APIUtils.getJsonValueFromResponse(String.format(VkApiConstants.RESPONSE_PATH_TO_PARAM, VkApiConstants.UPLOAD_URL));
    }

    private static Image saveWallPhoto(String token, String userId, File imageFile) {
        String serverUrl = getWallUploadServer(token);
        String jsonString = APIUtils.postImage(serverUrl, imageFile);

        ImageServer imageServer = DataHandler.asObject(jsonString, ImageServer.class);

        String photoJson = imageServer.getPhoto().replace("\\", "").replaceAll("\\s", "");
        String encodedStr = null;
        try {
            encodedStr = URLEncoder.encode(photoJson, StandardCharsets.UTF_8.toString());
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        VkApiUrlBuilder vkApiUrlBuilder = new VkApiUrlBuilder(VkApiUrlBuilder.VkApiMethods.PHOTOS_SAVE_WALL_PHOTO, token);
        vkApiUrlBuilder.addParam(VkApiConstants.USER_ID, userId);
        vkApiUrlBuilder.addParam(VkApiConstants.PHOTO, encodedStr);
        vkApiUrlBuilder.addParam(VkApiConstants.SERVER, imageServer.getServer());
        vkApiUrlBuilder.addParam(VkApiConstants.CAPTION, imageFile.getName());
        vkApiUrlBuilder.addParam(VkApiConstants.HASH, imageServer.getHash());
        APIUtils.post(vkApiUrlBuilder.getFullUrl());
        return APIUtils.getJsonObjectFromResponse(String.format(VkApiConstants.RESPONSE_BY_ARRAY_INDEX, 0), Image.class);
    }

    public static String createComment(String token, String userId, String postId, String message) {
        VkApiUrlBuilder vkApiUrlBuilder = new VkApiUrlBuilder(VkApiUrlBuilder.VkApiMethods.WALL_CREATE_COMMENT, token);
        vkApiUrlBuilder.addParam(VkApiConstants.OWNER_ID, userId);
        vkApiUrlBuilder.addParam(VkApiConstants.POST_ID, postId);
        vkApiUrlBuilder.addParam(VkApiConstants.MESSAGE, message);
        APIUtils.post(vkApiUrlBuilder.getFullUrl());
        return APIUtils.getJsonValueFromResponse(String.format(VkApiConstants.RESPONSE_PATH_TO_PARAM, VkApiConstants.COMMENT_ID));
    }

    public static String deletePostById(String token, String ownerId, String postId) {
        VkApiUrlBuilder vkApiUrlBuilder = new VkApiUrlBuilder(VkApiUrlBuilder.VkApiMethods.WALL_DELETE, token);
        vkApiUrlBuilder.addParam(VkApiConstants.OWNER_ID, ownerId);
        vkApiUrlBuilder.addParam(VkApiConstants.POST_ID, postId);
        APIUtils.get(vkApiUrlBuilder.getFullUrl());
        return APIUtils.getJsonValueFromResponse(VkApiConstants.RESPONSE);
    }

    public static boolean isPostLikedByUser(String token, String userId, String postId) {
        VkApiUrlBuilder vkApiUrlBuilder = new VkApiUrlBuilder(VkApiUrlBuilder.VkApiMethods.LIKES_IS_LIKED, token);
        vkApiUrlBuilder.addParam(VkApiConstants.USER_ID, userId);
        vkApiUrlBuilder.addParam(VkApiConstants.ITEM_ID, postId);
        vkApiUrlBuilder.addParam(VkApiConstants.TYPE, "post");
        APIUtils.get(vkApiUrlBuilder.getFullUrl());
        return APIUtils.getJsonValueFromResponse(String.format(VkApiConstants.RESPONSE_PATH_TO_PARAM, VkApiConstants.LIKED)).equals("1");
    }

    public static Post getPostById(String token, String userId, String postId) {
        VkApiUrlBuilder vkApiUrlBuilder = new VkApiUrlBuilder(VkApiUrlBuilder.VkApiMethods.WALL_GET_BY_ID, token);
        vkApiUrlBuilder.addParam(VkApiConstants.POSTS, String.format(VkApiConstants.POST_ID_PATTERN, userId, postId));
        APIUtils.get(vkApiUrlBuilder.getFullUrl());
        return APIUtils.getJsonObjectFromResponse(String.format(VkApiConstants.RESPONSE_BY_ARRAY_INDEX, 0), Post.class);
    }
}
