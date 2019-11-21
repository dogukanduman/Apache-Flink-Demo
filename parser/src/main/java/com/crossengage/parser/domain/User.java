package com.crossengage.parser.domain;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Data;

import java.util.Optional;

/**
 * Created by Dogukan Duman on 11.11.2018.
 */
@Data
@JsonSerialize
public class User {

    private Long id;
    private Boolean active;
    private ContactType contactBy;
    private String email;
    private String phone;

    public static Optional<User> buildWith(String raw) {

        try {
            String[] splitted = raw.split(",");
            Long id = Long.parseLong(splitted[0]);
            Boolean active = Boolean.parseBoolean(splitted[1]);
            String contactBy = splitted[2];
            String email = splitted[3];
            String phone = splitted[4];

            User user = new User();
            user.setId(id);
            user.setActive(active);

            if (contactBy.equals("email")) {
                user.setContactBy(ContactType.EMAIL);
            } else if (contactBy.equals("phone")) {
                user.setContactBy(ContactType.PHONE);
            } else if (contactBy.equals("all")) {
                user.setContactBy(ContactType.ALL);
            } else {
                user.setContactBy(ContactType.NONE);
            }

            user.setEmail(email);
            user.setPhone(phone);
            return Optional.of(user);
        } catch (Exception ex) {
            return Optional.empty();
        }


    }

}


