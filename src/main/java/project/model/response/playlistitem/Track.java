package project.model.response.playlistitem;

import lombok.Data;

import java.util.List;

@Data
public class Track {
    private Album album;
    private List<Artist> artists;
    private String id;
    private String name;

}
