package project.service;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import project.client.PlaylistClient;
import project.client.SpotifyAuthorizeClient;
import project.model.response.GetPlaylistItemsResponse;
import project.model.response.GetTrackFeatureResponse;
import project.model.response.GetTrackInfoResponse;
import project.model.response.TrackInfo;
import reactor.core.publisher.Flux;

import java.util.List;
import java.util.stream.Collectors;

import static project.common.CommonConstant.DEFAULT_PLAYLIST_ID;

@Service
public class PlaylistService {
    @Autowired
    private PlaylistClient playlistClient;

    private static final Logger logger = LoggerFactory.getLogger(PlaylistService.class);

    public Flux<String> getTopPlayListBySearch(String playlistName) {
        var getPlaylistRequest = new SpotifyAuthorizeClient().spotifyApi.searchPlaylists(playlistName).build();

        return playlistClient.searchPlaylist(getPlaylistRequest)
                .map(playlist -> playlist.getPlaylists().getItems())
                .map(playlistItems -> playlistItems
                        .stream()
                        .filter(owner -> "spotify".equals(owner.getOwner().getId())).findFirst())
                .map(item -> item.isPresent() ? item.get().getId() : DEFAULT_PLAYLIST_ID);
    }

    public Flux<GetTrackInfoResponse> getPlaylistItems(String search, String playlistId, String fields) {
        if (StringUtils.isNotEmpty(search) && StringUtils.isEmpty(playlistId)) {
            return getTopPlayListBySearch(search).flatMap(playlistIdFromSearch -> getGetTrackInfoResponseFlux(playlistIdFromSearch, fields));
        }

        return getGetTrackInfoResponseFlux(playlistId, fields);
    }

    private Flux<GetTrackInfoResponse> getGetTrackInfoResponseFlux(String playlistId, String fields) {
        var getPlaylistsItemsRequest = new SpotifyAuthorizeClient().spotifyApi
                .getPlaylistsItems(playlistId)
                .fields(fields)
                .build();

        return playlistClient.getPlaylistItems(getPlaylistsItemsRequest)
                .map(GetPlaylistItemsResponse::getItems)
                .map(item -> item
                        .stream()
                        .map(itemData -> new TrackInfo()
                                .setTrackId(itemData.getTrack().getId())
                                .setArtistName(itemData.getTrack().getArtists().get(0).getName())
                                .setTrackName(itemData.getTrack().getName())
                        ).collect(Collectors.toList()))
                .map(trackInfos -> new GetTrackInfoResponse().setTrackInfoList(trackInfos));
    }

    public Flux<GetTrackFeatureResponse> getTrackFeature(String search, String playlistId, List<String> trackIds) throws Exception {
        if (CollectionUtils.isEmpty(trackIds)) {
            return getPlaylistItems(search, playlistId, "items(track.name, track.id, track.artists(name), track.album.name)")
                    .map(GetTrackInfoResponse::getTrackInfoList)
                    .map(trackInfos -> trackInfos.stream().map(TrackInfo::getTrackId).collect(Collectors.toList()))
                    .flatMap(trackInfoList -> {
                        try {
                            return getGetTrackFeatureResponseFlux(trackInfoList);
                        } catch (Exception e) {
                            throw new RuntimeException(e);
                        }
                    });
        }

        logger.info("trackIds {}", trackIds);

        return getGetTrackFeatureResponseFlux(trackIds);
    }

    private Flux<GetTrackFeatureResponse> getGetTrackFeatureResponseFlux(List<String> trackIds) throws Exception {
        if (!CollectionUtils.isEmpty(trackIds)) {
            var getPlaylistsItemsRequest = new SpotifyAuthorizeClient().spotifyApi
                    .getAudioFeaturesForSeveralTracks(trackIds.toArray(new String[trackIds.size()]))
                    .build();

            return playlistClient.getTrackFeature(getPlaylistsItemsRequest);
        } else {
            throw new Exception("trackIds is empty");
        }
    }
}
