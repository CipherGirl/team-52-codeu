package com.google.codeu.data;

import java.util.UUID;

/** A single  posted by a user. */
public class Image {

    private UUID id;
    private String user;

    private long timestamp;
    private String url;


    /**
     * Constructs a new {@link Image} posted by {@code user} with {@code text} content. Generates a
     * random ID and uses the current system time for the creation time.
     */
    public Image(String user, String url) {
        this(UUID.randomUUID(), user, url, System.currentTimeMillis());
    }



    public Image(UUID id, String user, String url, long timestamp) {
        this.id = id;
        this.user = user;
        this.url = url;
        this.timestamp = timestamp;


    }


    public UUID getImageId() {
        return id;
    }

    public String getImageUser() {
        return user;
    }


    public String getImageURL() {
        return url;
    }

}
