package com.crossengage.sender.domain.message;

import lombok.Data;

/**
 * Created by Dogukan Duman on 12.11.2018.
 */
@Data
public class Message {

    private String content;

    public Message(String content) {
        this.content = content;
    }
}
