package com.example.plhistory.services;

import com.example.plhistory.entities.User.PasswordResetToken;

public interface PasswordResetTokenService {

    void savePasswordResetToken(PasswordResetToken passwordResetToken);

    PasswordResetToken getPasswordResetToken(String token);
}
