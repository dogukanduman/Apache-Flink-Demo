package com.crossengage.sender.service.imp;

import com.crossengage.sender.domain.message.Sms;
import com.crossengage.sender.service.Sender;

import java.io.Serializable;
import com.crossengage.sender.service.SendManager;
/**
 *Sender implementation
 * Responsible for sending SMS.
 * Used by @{{@link SendManager }}
 * Created by Dogukan Duman on 12.11.2018.
 */

public class SmsSender implements Sender<Sms>, Serializable{
    private static final long serialVersionUID = 1L;
    @Override
    public void send(Sms message) {
        System.out.println("Sms is sending number:"+message.getPhoneNumber()+" content:"+message.getContent());
    }
}
