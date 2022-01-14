package models;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class ImageSize {
    String type;
    String url;
    String width;
    String height;
}
