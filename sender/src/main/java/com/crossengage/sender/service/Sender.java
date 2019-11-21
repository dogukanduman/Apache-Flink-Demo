package com.crossengage.sender.service;

import com.crossengage.sender.domain.message.Message;

/**
 * Generic sender
 * Created by Dogukan Duman on 12.11.2018.
 */
public interface Sender<T extends Message> {

    void send(T message);
}
