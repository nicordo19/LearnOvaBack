package rncp.backend.sevice;
import rncp.backend.entity.User;
import rncp.backend.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class UserService  {

    private UserRepository userRepository;

    UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
    public User register(User user){
        return null ;
    }
}
