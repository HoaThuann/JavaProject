package com.web.clothes.ClothesWeb.service;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.web.clothes.ClothesWeb.entity.ConfirmationToken;
import com.web.clothes.ClothesWeb.repository.ConfirmationTokenRepository;

import lombok.RequiredArgsConstructor;
@Service
@RequiredArgsConstructor
public class ConfirmationTokenServiceImpl implements ConfirmationTokenService{
    private final ConfirmationTokenRepository confirmationTokenRepository;
    @Override
    public ConfirmationToken save(ConfirmationToken ConfirmationToken) {
        confirmationTokenRepository.save(ConfirmationToken);
        return ConfirmationToken;
    }
    @Override
    public Optional<ConfirmationToken> getConfirmationTokenByToken(String token) {
       return confirmationTokenRepository.findByToken(token);
    }

}
