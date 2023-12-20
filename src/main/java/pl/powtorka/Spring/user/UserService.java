package pl.powtorka.Spring.user;

import org.springframework.stereotype.Service;
import pl.powtorka.Spring.user.dto.UserCredentialsDto;

import java.util.Optional;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
    public Optional<UserCredentialsDto> findCredentialByEmail(String email){
        return userRepository.findByEmail(email)
                .map(UserCredentialsDtoMapper::map);
    }
}
