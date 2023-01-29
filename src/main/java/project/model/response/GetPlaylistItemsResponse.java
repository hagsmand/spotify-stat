package project.model.response;

import lombok.Data;
import project.model.response.playlistitem.Item;

import java.util.List;

@Data
public class GetPlaylistItemsResponse {
    private List<Item> items;
}
