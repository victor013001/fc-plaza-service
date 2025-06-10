package com.example.fc_plaza_service.application.config.security.filter;

import static com.example.fc_plaza_service.domain.constants.HeaderConst.AUTHORIZATION;
import static com.example.fc_plaza_service.domain.constants.HeaderConst.BEARER;

import com.example.fc_plaza_service.application.service.security.JwtApplicationService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.util.Strings;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

  private final JwtApplicationService jwtService;
  private final UserDetailsService userDetailsService;

  @Override
  protected void doFilterInternal(
      HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
      throws ServletException, IOException {
    final String authorizationHeader = request.getHeader(AUTHORIZATION);
    final String jwt;
    final String userEmail;
    if (Strings.isNotBlank(authorizationHeader) && authorizationHeader.startsWith(BEARER)) {
      jwt = authorizationHeader.substring(BEARER.length());
      userEmail = jwtService.extractUserName(jwt);
      if (Objects.nonNull(userEmail)
          && Objects.isNull(SecurityContextHolder.getContext().getAuthentication())) {
        UserDetails userDetails = userDetailsService.loadUserByUsername(jwt);
        if (jwtService.isTokenValid(jwt, userDetails)) {
          UsernamePasswordAuthenticationToken authenticationToken =
              new UsernamePasswordAuthenticationToken(
                  userDetails, jwt, userDetails.getAuthorities());
          authenticationToken.setDetails(
              new WebAuthenticationDetailsSource().buildDetails(request));
          SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        }
      }
    }
    filterChain.doFilter(request, response);
  }
}
