package project.model.response.search;

import lombok.Data;

import java.util.List;

@Data
public class Playlist {
    private List<Item> items;
}
