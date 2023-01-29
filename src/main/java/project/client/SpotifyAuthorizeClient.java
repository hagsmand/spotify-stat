package project.client;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import se.michaelthelin.spotify.SpotifyApi;
import se.michaelthelin.spotify.model_objects.credentials.ClientCredentials;

public class SpotifyAuthorizeClient {

    private static final Logger logger = LoggerFactory.getLogger(SpotifyAuthorizeClient.class);

    public SpotifyApi spotifyApi = new SpotifyApi.Builder()
            .setClientId("40e481bb582149fd91c0a56b64cddfd2")
            .setClientSecret("f207f809e7914216ab4e2a981005faf8")
            .build();

    public SpotifyAuthorizeClient() {
        try {
            ClientCredentials execute = spotifyApi.clientCredentials().build().execute();
            spotifyApi.setAccessToken(execute.getAccessToken());
        } catch (Exception e) {
            logger.error("Unable to authorize to Spotify");
        }
    }
}