package rncp.backend.security;


import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.web.filter.OncePerRequestFilter;
import rncp.backend.entity.User;
import rncp.backend.repository.UserRepository;
import rncp.backend.sevice.JwtService;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final UserRepository userRepository;

    public JwtAuthenticationFilter(JwtService jwtService, UserRepository userRepository) {
        this.jwtService = jwtService;
        this.userRepository = userRepository;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        String token = null;

        if (request.getCookies() != null) {
            for (jakarta.servlet.http.Cookie cookie : request.getCookies()) {
                if ("jwt".equals(cookie.getName())) {
                    token = cookie.getValue();
                }
            }
        }

        if (token == null) {
            filterChain.doFilter(request, response);
            return;
        }
        String email = jwtService.extractEmail(token);

        Optional<User> userOptional = userRepository.findByEmail(email);
        if (userOptional.isPresent()) {
            User user = userOptional.get();

            if (jwtService.isTokenValid(token, user)) {
                //creation de structur d'objet Spring Security pour:
                // utilisateur connecté + son été d'autentification;
                //                                                                                    identifant/mot de passe, role
                List<GrantedAuthority> authorities = new ArrayList<>();
                String roleName = user.getRole() != null ? user.getRole().getRoleName() : null;

                if ("PROFESSEUR".equalsIgnoreCase(roleName)) {
                    authorities.add(new SimpleGrantedAuthority("ROLE_PROF"));
                } else if ("ETUDIANT".equalsIgnoreCase(roleName)) {
                    authorities.add(new SimpleGrantedAuthority("ROLE_USER"));
                }

                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(user, null, authorities);

                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        }
        filterChain.doFilter(request, response);
    }
}
