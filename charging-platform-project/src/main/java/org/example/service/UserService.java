package org.example.service;

import org.example.models.po.Userregister;

public interface UserService {
    Userregister findByUserName(String username);

    void register(String username, String password);
}
