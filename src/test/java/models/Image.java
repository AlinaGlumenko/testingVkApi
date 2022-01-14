package models;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import utils.VkApiConstants;

@NoArgsConstructor
@Data
public class Image {
    String id;
    @JsonProperty(VkApiConstants.ALBUM_ID)
    String albumId;
    @JsonProperty(VkApiConstants.USER_ID)
    String userId;
    String text;
    String date;
    @JsonProperty(VkApiConstants.HAS_TAGS)
    String hasTags;
    @JsonProperty(VkApiConstants.ACCESS_KEY)
    String accessKey;
    @JsonProperty(VkApiConstants.OWNER_ID)
    String ownerId;
    ImageSize[] sizes;
}
