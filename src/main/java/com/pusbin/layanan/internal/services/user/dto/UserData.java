package com.pusbin.layanan.internal.services.user.dto;

import com.pusbin.layanan.internal.models.User;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserData {

    private String nama;
    private String email;
    private String username;

    public static UserData fromUserModel(User user) {
        UserData data = new UserData();
        data.setNama(user.getNama());
        data.setUsername(user.getUsername());
        data.setEmail(user.getEmail());
        return data;
    }
}
