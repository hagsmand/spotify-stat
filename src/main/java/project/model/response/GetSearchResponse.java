package project.model.response;

import lombok.Data;
import project.model.response.search.Playlist;

@Data
public class GetSearchResponse {
    private Playlist playlists;
}
