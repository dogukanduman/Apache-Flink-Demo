package com.crossengage.sender.service;

import com.crossengage.sender.domain.ContactType;
import com.crossengage.sender.domain.User;
import com.crossengage.sender.domain.message.Email;
import com.crossengage.sender.domain.message.Sms;
import com.crossengage.sender.service.imp.MailSender;
import com.crossengage.sender.service.imp.SmsSender;
import org.apache.flink.streaming.api.functions.sink.RichSinkFunction;
import org.apache.flink.streaming.api.functions.sink.SinkFunction;
import org.springframework.stereotype.Service;

/**
 * SendManager extends RichSinkFunction
 * Calls Sender instances which responsible for sending SMS and EMAIL
 * Depends on the users desire, SendManager reaches to users.
 * Created by Dogukan Duman on 12.11.2018.
 */
@Service
public class SendManager extends RichSinkFunction<User> {


    private static Sender<Sms> smsSender = new SmsSender();
    private static Sender<Email> emailSender= new MailSender();

    @Override
    public void invoke(User user, SinkFunction.Context context) throws Exception {

        String content ="Hello...";

        if (user.getContactBy() == ContactType.EMAIL) {
            emailSender.send(new Email(content,user.getEmail()));

        } else if (user.getContactBy()== ContactType.PHONE) {
            smsSender.send(new Sms(content,user.getPhone()));

        } else {
            emailSender.send(new Email(content,user.getEmail()));
            smsSender.send(new Sms(content,user.getPhone()));
        }
    }
}
