package models;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class Photo {
    boolean markers_restarted;
    String photo;
    String[] sizes;
    String latitude;
    String longitude;
    String kid;
    String[][] sizes2;
    String[] urls;
    String[] urls2;
}
