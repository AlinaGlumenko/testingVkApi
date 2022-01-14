package models;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class ImageServer {
    String photo;
    String server;
    String hash;
}
