package project.model.response;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class TrackInfo {
    private String artistName;
    private String trackName;
    private String trackId;
}
