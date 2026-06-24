package rncp.backend.service;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import rncp.backend.dto.UserProfileResponse;
import rncp.backend.entity.User;

@Service
public class UserService {

    public UserProfileResponse getCurrentUserProfile(Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        return UserProfileResponse.from(user);
    }
}
