package project.model.response;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

@Data
@Accessors(chain = true)
public class GetTrackInfoResponse {
   private List<TrackInfo> trackInfoList;
}
