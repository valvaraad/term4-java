package com.gustavo.labjava.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class Player {
    private String url;
    private String username;
    @JsonProperty("player_id")
        private Integer playerId;
    private String title;
    private String status;
    private String name;
    private String avatar;
    private String country;
    @JsonProperty("is_streamer")
        private Boolean isStreamer;
    @JsonProperty("twitch_url")
        private String twitchUrl;
    private String location;
    private Integer joined;
    @JsonProperty("last_online")
        private Integer lastOnline;
    private Integer followers;
    private Integer fide;
}
