package project.model.response.audiofeature;

import com.fasterxml.jackson.annotation.JsonAlias;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class AudioFeature {
    private float danceability;
    private float energy;
    private float loudness;
    private float speechiness;
    private float acousticness;
    private float instrumentalness;
    private float liveness;
    private float valence;
    private float tempo;
    private String id;
    @JsonAlias("track_href")
    private String trackHref;
}