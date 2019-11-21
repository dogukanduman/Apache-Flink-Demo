package com.crossengage.sender.service.imp;

import com.crossengage.sender.domain.message.Email;
import com.crossengage.sender.service.Sender;

import java.io.Serializable;
import com.crossengage.sender.service.SendManager;
/**
 * Sender implementation
 * Responsible for sending mails.
 * Used by @{{@link SendManager }}
 * Created by Dogukan Duman on 12.11.2018.
 */

public class MailSender implements Sender<Email>,Serializable {

    private static final long serialVersionUID = 1L;
    @Override
    public void send(Email message) {
        System.out.println("Emai is sending address:"+message.getEmail()+" content:"+message.getContent());
    }
}