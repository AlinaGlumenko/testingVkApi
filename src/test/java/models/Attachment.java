package models;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class Attachment {
    String type;
    Image photo;
}
