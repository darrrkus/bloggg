package ua.tvsolutions.bloggg.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.tvsolutions.bloggg.models.AppUser;
import ua.tvsolutions.bloggg.models.Role;
import ua.tvsolutions.bloggg.repos.AppUserRepository;

import java.util.Collection;
import java.util.stream.Collectors;

@Service
public class AppUserService implements UserDetailsService {
    private AppUserRepository appUserRepository;

    @Autowired
    public void setAppUserRepository(AppUserRepository appUserRepository) {
        this.appUserRepository = appUserRepository;
    }

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        AppUser appUser = appUserRepository.findByUsername(username);
        return new User(appUser.getUsername(), appUser.getPassword(),
                mapRolesToAuthorities(appUser.getRoles()));
    }

    Collection<? extends GrantedAuthority> mapRolesToAuthorities(Collection<? extends Role> roles){
        return roles.stream().map(r->new SimpleGrantedAuthority(r.getName())).collect(Collectors.toList());
    }
}
