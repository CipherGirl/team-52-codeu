package com.google.codeu.data;

import java.util.UUID;

/** A single Group posted by a user. */
public class Group {

    private UUID id;
    private String user;
    private String text;
    private long timestamp;
    private String groupId;

    /**
     * Constructs a new {@link Group} posted by {@code user} with {@code text} content. Generates a
     * random ID and uses the current system time for the creation time.
     */
    public Group(String user, String text) {
        this(UUID.randomUUID(), user, text, System.currentTimeMillis());
    }



    public Group(UUID id, String user, String text, long timestamp) {
        this.id = id;
        this.user = user;
        this.text = text;
        this.timestamp = timestamp;


    }

    public Group(UUID id, String user, String text, String groupId, long timestamp) {
        this.id = id;
        this.user = user;
        this.text = text;
        this.timestamp = timestamp;
        this.groupId = groupId;


    }
    public Group(String user, String text, String groupId) {
        this(UUID.randomUUID(), user, text, groupId, System.currentTimeMillis());
    }

    public UUID getId() {
        return id;
    }

    public String getUser() {
        return user;
    }

    public String getText() {
        return text;
    }

    public long getTimestamp() {
        return timestamp;
    }



    public String getGroupId() {
        return groupId;
    }
}
