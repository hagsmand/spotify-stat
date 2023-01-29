package project.model.response;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.experimental.Accessors;
import project.model.response.audiofeature.AudioFeature;

import java.util.List;

@Data
@Accessors(chain = true)
public class GetTrackFeatureResponse {
    @JsonAlias("audio_features")
    private List<AudioFeature> audioFeatures;
}
