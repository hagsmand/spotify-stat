package project.client;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import project.model.response.GetPlaylistItemsResponse;
import project.model.response.GetSearchResponse;
import project.model.response.GetTrackFeatureResponse;
import reactor.core.publisher.Flux;
import se.michaelthelin.spotify.requests.data.playlists.GetPlaylistsItemsRequest;
import se.michaelthelin.spotify.requests.data.search.simplified.SearchPlaylistsRequest;
import se.michaelthelin.spotify.requests.data.tracks.GetAudioFeaturesForSeveralTracksRequest;

@Service
public class PlaylistClient {
    private static final Logger logger = LoggerFactory.getLogger(PlaylistClient.class);

    private final WebClient client = WebClient.create("https://api.spotify.com");


    public Flux<GetSearchResponse> searchPlaylist(SearchPlaylistsRequest request) {
        HttpHeaders httpHeaders = new HttpHeaders();
        request.getHeaders().forEach(header -> httpHeaders.add(header.getName(), header.getValue()));

        return client.get()
                .uri(request.getUri())
                .headers(consumerHeader -> consumerHeader.addAll(httpHeaders))
                .retrieve()
                .bodyToFlux(GetSearchResponse.class);
    }

    public Flux<GetPlaylistItemsResponse> getPlaylistItems(GetPlaylistsItemsRequest request) {
        HttpHeaders httpHeaders = new HttpHeaders();
        request.getHeaders().forEach(header -> httpHeaders.add(header.getName(), header.getValue()));

        return client.get()
                .uri(request.getUri())
                .headers(consumerHeader -> consumerHeader.addAll(httpHeaders))
                .retrieve()
                .bodyToFlux(GetPlaylistItemsResponse.class);
    }

    public Flux<GetTrackFeatureResponse> getTrackFeature(GetAudioFeaturesForSeveralTracksRequest request) {
        HttpHeaders httpHeaders = new HttpHeaders();
        request.getHeaders().forEach(header -> httpHeaders.add(header.getName(), header.getValue()));

        return client.get()
                .uri(request.getUri())
                .headers(consumerHeader -> consumerHeader.addAll(httpHeaders))
                .retrieve()
                .bodyToFlux(GetTrackFeatureResponse.class);
    }
}
