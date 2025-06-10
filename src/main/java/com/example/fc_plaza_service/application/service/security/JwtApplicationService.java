package com.example.fc_plaza_service.application.service.security;

import io.jsonwebtoken.Claims;
import java.util.List;
import java.util.function.Function;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public interface JwtApplicationService {

  <T> T extractClaim(String token, Function<Claims, T> claimsResolver);

  String extractUserName(String token);

  boolean isTokenValid(String token, UserDetails userDetails);

  List<GrantedAuthority> extractAuthorities(String token);
}
