package com.crossengage.sender.domain.message;

import lombok.Data;

/**
 * Created by Dogukan Duman on 12.11.2018.
 */
@Data
public class Sms extends Message{

    private String phoneNumber;

    public Sms(String content, String phoneNumber) {
        super(content);
        this.phoneNumber = phoneNumber;
    }
}
