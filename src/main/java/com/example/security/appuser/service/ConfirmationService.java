package com.example.security.appuser.service;


import com.example.security.appuser.repositories.ConfirmationServiceRepository;
import com.example.security.security.token.ConfirmationToken;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@AllArgsConstructor
public class ConfirmationService {
    private final ConfirmationServiceRepository confirmationServiceRepository;

    public void saveConfirmationToken(ConfirmationToken token){
        confirmationServiceRepository.save(token);
    }

    public Optional<ConfirmationToken> getToken(String token) {
        return confirmationServiceRepository.findByToken(token);
    }

    public int setConfirmedAt(String token) {
        return confirmationServiceRepository.updateConfirmedAt(
                token, LocalDateTime.now());
    }


}
