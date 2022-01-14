package models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import utils.VkApiConstants;

/*
 * "authorName" - variable for a local using in the code
 * Unused VK API post parameters: "post_source", "comments", "likes", "reposts", "is_favorite",
 * "donut", "short_text_rate", "edited", "hash"
 */
@NoArgsConstructor
@Data
@JsonIgnoreProperties(value = { "authorName", "post_source", "comments", "likes",
        "reposts", "is_favorite", "donut", "short_text_rate", "edited", "hash" })
public class Post {
    String id;
    @JsonProperty(VkApiConstants.OWNER_ID)
    String ownerId;
    @JsonProperty(VkApiConstants.FROM_ID)
    String fromId;
    String date;
    @JsonProperty(VkApiConstants.POST_TYPE)
    String postType;
    String authorName;
    @JsonProperty(VkApiConstants.CAN_EDIT)
    String canEdit;
    @JsonProperty(VkApiConstants.CAN_DELETE)
    String canDelete;
    @JsonProperty(VkApiConstants.CAN_PIN)
    String canPin;
    @JsonProperty(VkApiConstants.CAN_ARCHIVE)
    String canArchive;
    @JsonProperty(VkApiConstants.IS_ARCHIVED)
    String isArchived;
    @JsonProperty(VkApiConstants.TEXT)
    String messageText;
    Attachment[] attachments;
}
