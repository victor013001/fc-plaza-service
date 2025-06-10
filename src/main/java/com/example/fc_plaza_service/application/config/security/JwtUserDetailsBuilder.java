package com.example.fc_plaza_service.application.config.security;

import com.example.fc_plaza_service.application.service.security.JwtApplicationService;
import com.example.fc_plaza_service.domain.exceptions.standard_exception.BadRequest;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.util.Strings;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class JwtUserDetailsBuilder implements UserDetailsService {

  private final JwtApplicationService jwtService;

  @Override
  public UserDetails loadUserByUsername(String token) throws UsernameNotFoundException {
    if (Strings.isBlank(token)) {
      throw new BadRequest();
    }
    String username = jwtService.extractUserName(token);
    List<GrantedAuthority> authorities = jwtService.extractAuthorities(token);

    return new User(username, "", authorities);
  }
}
