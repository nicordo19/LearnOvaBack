package rncp.backend.sevice;
import jakarta.transaction.Transactional;
import org.springframework.security.crypto.password.PasswordEncoder;
import rncp.backend.dto.RegisterRequest;
import rncp.backend.entity.User;
import rncp.backend.repository.RoleRepository;
import rncp.backend.repository.UserRepository;
import org.springframework.stereotype.Service;
import rncp.backend.entity.Role ;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;


@Service
public class UserService  {

    private UserRepository userRepository;
    private RoleRepository roleRepository;
    private PasswordEncoder passwordEncoder;

    UserService(UserRepository userRepository , RoleRepository roleRepository ,  PasswordEncoder passwordEncoder) {

        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }
    public User register(User user){
        return null ;
    }
    public User login(User user){
        return null ;
    }

    @Transactional
    public User createUser(RegisterRequest request) {


        User user = new User();

        if ((request.isEtudiant() && request.isProfesseur()) || (!request.isEtudiant() && !request.isProfesseur())) {
            throw new RuntimeException("Vous devez choisir soit étudiant soit professeur");
        }
        Role role;

        if (request.isEtudiant()) {
            role = roleRepository.findByRoleName("ETUDIANT");
        } else {
            role = roleRepository.findByRoleName("PROFESSEUR");
        }

        user.setRole(role);
        user.setFirst_name(request.getFirstName());
        user.setLast_name(request.getLastName());
        user.setEmail(request.getEmail());
        user.setProfession(request.getProfession());
        user.setPassword(passwordEncoder.encode(request.getPassword()));

        return userRepository.save(user);
    }
}
