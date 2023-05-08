package com.web.clothes.ClothesWeb.service;


import java.util.Optional;

import com.web.clothes.ClothesWeb.entity.ConfirmationToken;


public  interface ConfirmationTokenService {
	public ConfirmationToken save(ConfirmationToken ConfirmationToken);
	public Optional<ConfirmationToken> getConfirmationTokenByToken(String token);
}
