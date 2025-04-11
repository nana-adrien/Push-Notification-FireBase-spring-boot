package empire.digiprem.services;

import empire.digiprem.dto.RegisterReqDto;
import empire.digiprem.models.AppUser;
import empire.digiprem.models.UserRole;
import empire.digiprem.repositories.UserRepository;
import empire.digiprem.repositories.UserRoleRepository;
import jakarta.transaction.Transactional;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Transactional
@Service
public class UserDetailServiceImpl implements UserDetailsService {

    private UserRoleRepository roleRepository;
    private UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserDetailServiceImpl(UserRoleRepository roleRepository, UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.roleRepository = roleRepository;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("Utilisateur non trouvé"));
    }
    public AppUser register(RegisterReqDto user){
        if (user==null){
            throw new RuntimeException("user is null");
        }
        if (user.username()==null||user.password()==null){
            throw new RuntimeException("require user and password information");
        }
        if ( userRepository.findByUsername(user.username()).orElse(null)!=null){
            throw new RuntimeException("user exist");
        }
        String password=passwordEncoder.encode(user.password());/*
        Collection<UserRole> roles=new ArrayList<>();
        user.roles().forEach(r-> roles.add(findRoleByName(r)));*/
        Collection<UserRole> roles= List.of(findRoleByName("ADMIN"))  ;
        return userRepository.save(new AppUser(user.username(),password,user.email(),roles));
    }

    public Collection<UserRole> getRoles(){
        return roleRepository.findAll();
    }
    public UserRole findRoleByName(String roleName){
        return roleRepository.findRoleByName(roleName).orElse(null);
    }

    public Collection<AppUser> getAllUsers(){
        return userRepository.findAll();
    }
    public UserRole addUserRole(String roleName){
        UserRole userRole=findRoleByName(roleName);
        if (userRole!=null){
            throw new RuntimeException("role <"+roleName+"> exists");
        }
        userRole=new UserRole();
        userRole.setName(roleName);
       return roleRepository.save(userRole);
    }

}
