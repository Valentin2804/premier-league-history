package com.example.plhistory.services.impl;

import com.example.plhistory.entities.User.ConformationToken;
import com.example.plhistory.repositories.ConformationTokenRepository;
import com.example.plhistory.services.ConformationTokenService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class ConformationTokenServiceImpl implements ConformationTokenService {

    private final ConformationTokenRepository repository;

    public ConformationTokenServiceImpl(ConformationTokenRepository repository) {
        this.repository = repository;
    }

    @Override
    public void save(ConformationToken token) {
        repository.save(token);
    }

    @Override
    public ConformationToken getToken(String tokenString){

        ConformationToken conformationToken = repository.findByToken(tokenString);

        if(conformationToken != null)
        {
            return conformationToken;
        }else
        {
            throw new IllegalStateException("token not found");
        }
    }

    @Override
    public void setConfirmedAt(String tokenString) {
        ConformationToken conformationToken = repository.findByToken(tokenString);

        if(conformationToken.getExpiresAt().isAfter(LocalDateTime.now())){
            conformationToken.setConfirmedAt(LocalDateTime.now());
        }else {
            throw new IllegalStateException("Expired activation token");
        }


        repository.save(conformationToken);

    }
}
