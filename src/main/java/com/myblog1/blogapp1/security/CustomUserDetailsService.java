package com.myblog1.blogapp1.security;

import com.myblog1.blogapp1.entities.Role;
import com.myblog1.blogapp1.entities.User;
import com.myblog1.blogapp1.repository.UserRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;
@Service
public class CustomUserDetailsService implements UserDetailsService {
    private UserRepository userRepo;

    public CustomUserDetailsService(UserRepository userRepo) {
        this.userRepo = userRepo;
    }

    @Override
    public UserDetails loadUserByUsername(String usernameOrEmail) throws UsernameNotFoundException {
        User user = userRepo.findByUsernameOrEmail(usernameOrEmail, usernameOrEmail)
                .orElseThrow(() ->
                        new UsernameNotFoundException("User not found with  username or email:" +usernameOrEmail));
        return new org.springframework.security.core.userdetails.User(
                user.getEmail(), user.getPassword(),
                mapRolesToAuthorities(user.getRoles()));

    }

    private Collection<? extends GrantedAuthority> mapRolesToAuthorities(Set<Role> roles) {

        return roles.stream().map(role ->new SimpleGrantedAuthority(role.getName())).collect(Collectors.toList());
    }
}
