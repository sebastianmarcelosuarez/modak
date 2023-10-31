package com.modak;

import org.springframework.stereotype.Service;

@Service
public class Gateway {
    /* already implemented */
    public void send(String userId, String message) {
        System.out.println("sending message to user " + userId + " message: " + message);
    }
}
