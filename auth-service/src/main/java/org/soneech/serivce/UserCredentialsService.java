package org.soneech.serivce;

import org.soneech.model.User;
import org.soneech.repository.UserRepository;
import org.soneech.security.UserCredentials;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserCredentialsService implements UserDetailsService {
    private final UserRepository userRepository;

    @Autowired
    public UserCredentialsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> user = userRepository.findByUsername(username);
        if (user.isEmpty()) {
            throw new UsernameNotFoundException("пользователя с таким именем не существует");
        }
        return new UserCredentials(user.get());
    }
}
