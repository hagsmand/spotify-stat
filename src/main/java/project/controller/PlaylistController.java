package project.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import project.model.response.GetTrackFeatureResponse;
import project.model.response.GetTrackInfoResponse;
import project.service.PlaylistService;
import reactor.core.publisher.Flux;

import java.util.List;

@RestController
@RequestMapping("/v1/playlist")
public class PlaylistController {

    @Autowired
    private PlaylistService playlistService;

    @GetMapping("/search")
    private Flux<String> getEmployeeById(@RequestParam String searchString) {
        return playlistService.getTopPlayListBySearch(searchString);
    }

    @GetMapping("/playlist-items")
    private Flux<GetTrackInfoResponse> getPlaylistItems(
            @RequestParam(required = false) String search,
            @RequestParam(required = false) String playlistId,
            @RequestParam(required = false, defaultValue = "items(track.name, track.id, track.artists(name), track.album.name)")
            String fields
    ) {
        return playlistService.getPlaylistItems(search, playlistId, fields);
    }

    @GetMapping("/track-features")
    private Flux<GetTrackFeatureResponse> getTrackFeature(
            @RequestParam(required = false) String search,
            @RequestParam(required = false) String playlistId,
            @RequestParam(required = false) List<String> tracksId
    ) throws Exception {
        return playlistService.getTrackFeature(search, playlistId, tracksId);
    }
}
