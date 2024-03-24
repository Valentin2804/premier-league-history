package com.example.plhistory.services;

import com.example.plhistory.entities.User.ConformationToken;

public interface ConformationTokenService {

    void save(ConformationToken token);

    ConformationToken getToken(String tokenString);

    void setConfirmedAt(String tokenString);

}
