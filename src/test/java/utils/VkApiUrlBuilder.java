package utils;

public class VkApiUrlBuilder {

    private String apiVersion = "5.131";
    private String baseApiUrl = TestData.get("apiUrl") + "%s%s";
    private String token;
    private String apiMethod;
    private String params = "";

    public enum VkApiMethods {
        WALL_POST("wall.post"), WALL_EDIT("wall.edit"),
        PHOTOS_GET_WALL_UPLOAD_SERVER("photos.getWallUploadServer"),
        PHOTOS_SAVE_WALL_PHOTO("photos.saveWallPhoto"),
        WALL_CREATE_COMMENT("wall.createComment"),
        WALL_DELETE("wall.delete"), WALL_GET_BY_ID("wall.getById"),
        LIKES_IS_LIKED("likes.isLiked");

        private String method;
        VkApiMethods(String method){
            this.method = method;
        }
        public String getMethod(){ return method;}
    }

    public VkApiUrlBuilder(VkApiMethods method, String token) {
        this.token = token;
        this.apiMethod = method.getMethod();
    }

    public void addParam(String key, String value) {
        if(params.isEmpty()) {
            params = params.concat("?" + key + "=" + value);
        } else {
            params = params.concat("&" + key + "=" + value);
        }
    }

    public String getFullUrl() {
        addParam("access_token", token);
        addParam("v", apiVersion);
        return String.format(baseApiUrl, apiMethod, params);
    }
}
