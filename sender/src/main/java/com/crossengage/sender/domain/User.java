package com.crossengage.sender.domain;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Data;

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

}


