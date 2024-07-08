package com.ejemploo.soaa.config;

import com.ejemploo.soaa.model.OurUser;
import com.ejemploo.soaa.repository.OurUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class OurUserInfoUserDetailsService implements UserDetailsService {
    @Autowired
    private OurUserRepository ourUserRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<OurUser> user = ourUserRepository.findByEmail(email);
        return user.map(OurUserInfoDetails::new).orElseThrow(()-> new UsernameNotFoundException("Usuario no existe"));
    }

}
