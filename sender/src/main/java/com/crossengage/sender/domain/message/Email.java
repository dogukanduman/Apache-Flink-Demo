package com.crossengage.sender.domain.message;

import lombok.Data;

/**
 * Created by Dogukan Duman on 12.11.2018.
 */
@Data
public class Email extends Message {

    private String email;

    public Email(String content, String email) {
        super(content);
        this.email = email;
    }
}
