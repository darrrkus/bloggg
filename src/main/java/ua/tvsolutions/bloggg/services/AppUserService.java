package ua.tvsolutions.bloggg.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.tvsolutions.bloggg.models.AppUser;
import ua.tvsolutions.bloggg.models.Role;
import ua.tvsolutions.bloggg.repos.AppUserRepository;
import ua.tvsolutions.bloggg.repos.RoleRepository;

import java.util.Collection;
import java.util.Collections;
import java.util.stream.Collectors;

@Service
public class AppUserService implements UserDetailsService {

    private final AppUserRepository appUserRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public AppUserService(AppUserRepository appUserRepository,
                          RoleRepository roleRepository,
                          PasswordEncoder passwordEncoder) {
        this.appUserRepository = appUserRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        AppUser appUser = appUserRepository.findByUsername(username);
        if (appUser == null) throw new UsernameNotFoundException(String.format("user %s not found", username));
        return new User(appUser.getUsername(), appUser.getPassword(),
                appUser.getAuthorities());
    }


    Collection<? extends GrantedAuthority> mapRolesToAuthorities(Collection<? extends Role> roles) {
        return roles
                .stream()
                .map(r -> new SimpleGrantedAuthority(r.getAuthority()))
                .collect(Collectors.toList());
    }

    @Transactional
    public void registerNewUser(AppUser appUser) {
        String cryptPassword = passwordEncoder.encode(appUser.getPassword());
        appUser.setPassword(cryptPassword);
        appUser.setRoles(Collections.singleton(roleRepository.findByName("ROLE_USER")));
        appUserRepository.save(appUser);
    }

    public AppUser findByUsername(String username) {
        return appUserRepository.findByUsername(username);
    }
}
