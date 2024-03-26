package com.gustavo.labjava.model;

import lombok.Data;

@Data
public class PlayerResponse {
    private String url;
    private String username;
    private Integer player_id;
    private String title;
    private String status;
    private String name;
    private String avatar;
    private String country;
    private Boolean is_streamer;
    private String twitch_url;
    private String location;
    private Integer joined;
    private Integer last_online;
    private Integer followers;
    private Integer fide;
}
