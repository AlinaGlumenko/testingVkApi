package utils;

import io.restassured.builder.MultiPartSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.http.Method;
import io.restassured.response.Response;
import io.restassured.specification.MultiPartSpecification;
import io.restassured.specification.RequestSpecification;
import org.apache.log4j.Logger;

import java.io.File;
import java.io.FileOutputStream;

import static io.restassured.RestAssured.given;

public class APIUtils {

    private static Response response;

    public static void get(String url) {
        RequestSpecification httpRequest = given();
        httpRequest.contentType(ContentType.JSON);
        response = httpRequest.request(Method.GET, url);
    }

    public static void post(String url) {
        RequestSpecification httpRequest = given();
        httpRequest.contentType(ContentType.JSON);
        httpRequest.urlEncodingEnabled(false);
        response = httpRequest.post(url);
    }

    public static String postImage(String url, File image) {
        RequestSpecification httpRequest = given();
        httpRequest.header("Content-type", "multipart/form-data");
        MultiPartSpecification multiPartSpecification = new MultiPartSpecBuilder(image)
                .fileName(image.getAbsolutePath())
                .controlName("photo")
                .mimeType("image/jpeg")
                .build();
        httpRequest.multiPart(multiPartSpecification);
        response = httpRequest.post(url);
        return response.getBody().htmlPath().getString("html.body");
    }

    public static File downloadFile(String url, String newImagePath) {
        RequestSpecification httpRequest = given();
        response = httpRequest.get(url);

        try {
            FileOutputStream os = new FileOutputStream(newImagePath);
            os.write(response.asByteArray());
            os.close();
        } catch (Exception e) {
            Logger.getRootLogger().error(e);
            e.printStackTrace();
        }
        return new File(newImagePath);
    }

    public static String getJsonValueFromResponse(String path) {
        return response.getBody().jsonPath().getString(path);
    }

    public static <T> T getJsonObjectFromResponse(String path, Class<T> tClass) {
        return response.getBody().jsonPath().getObject(path, tClass);
    }
}
