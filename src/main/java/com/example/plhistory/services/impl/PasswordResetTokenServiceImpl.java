package com.example.plhistory.services.impl;

import com.example.plhistory.entities.User.PasswordResetToken;
import com.example.plhistory.repositories.PasswordResetTokenRepository;
import com.example.plhistory.services.PasswordResetTokenService;
import org.springframework.stereotype.Service;

@Service
public class PasswordResetTokenServiceImpl implements PasswordResetTokenService {

    private final PasswordResetTokenRepository repository;

    public PasswordResetTokenServiceImpl(PasswordResetTokenRepository repository) {
        this.repository = repository;
    }

    @Override
    public void savePasswordResetToken(PasswordResetToken passwordResetToken) {
        repository.save(passwordResetToken);
    }

    @Override
    public PasswordResetToken getPasswordResetToken(String token) {
        return repository.findByToken(token);
    }
}
