package com.example.geocontact.security;

import com.example.geocontact.entity.User;
import com.example.geocontact.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    public UserDetailsServiceImpl(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String id) throws UsernameNotFoundException {
        UUID uuid = UUID.fromString(id);
        User user = userRepository.findById(uuid)
            .orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado com id: " + id));
        return new UserDetailsImpl(user);
    }
}
