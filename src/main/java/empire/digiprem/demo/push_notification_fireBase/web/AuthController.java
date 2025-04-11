package empire.digiprem.demo.push_notification_fireBase.web;

import empire.digiprem.demo.push_notification_fireBase.dto.*;
import empire.digiprem.demo.push_notification_fireBase.dto.ResponseBody;
import empire.digiprem.demo.push_notification_fireBase.model.AppUser;
import empire.digiprem.demo.push_notification_fireBase.model.UserRole;
import empire.digiprem.demo.push_notification_fireBase.services.UserDetailServiceImpl;
import empire.digiprem.demo.push_notification_fireBase.util.JwtTokenUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@RequestMapping("api/v1/auth")
@Slf4j
public class AuthController {
    private JwtTokenUtil jwtTokenUtil;
    private  UserDetailServiceImpl userDetailService;
    private AuthenticationManager authenticationManager;

    public AuthController(JwtTokenUtil jwtTokenUtil, UserDetailServiceImpl userDetailService, AuthenticationManager authenticationManager) {
        this.jwtTokenUtil = jwtTokenUtil;
        this.userDetailService = userDetailService;
        this.authenticationManager = authenticationManager;
    }

    @PostMapping("/login")
    public ResponseEntity<AuthRespDto> login(@RequestBody AuthReqDto authReqDto) {
        log.info("Login request received for user: {}", authReqDto.username());
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authReqDto.username(), authReqDto.password()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        UserDetails userDetails = userDetailService.loadUserByUsername(authReqDto.username());
        String token = jwtTokenUtil.generateToken(userDetails);
        return ResponseEntity.ok(AuthRespDto.builder().token(token).username(authReqDto.username()).build());
    }

    @PostMapping("/register")
    public  ResponseEntity<RegisterRespDto> register(@RequestBody RegisterReqDto registerReqDto) {

       // try{
            AppUser savedAppUser = userDetailService.register(registerReqDto);
            RegisterRespDto registerRespDto=new RegisterRespDto(savedAppUser.getId(), savedAppUser.getUsername(), savedAppUser.getEmail(), savedAppUser.getRoles());
            return ResponseEntity.ok(registerRespDto);
      //  }catch (Exception e){
        //    return ResponseEntity.ok(ResponseBody.builder().hasSusses(false).errorMessage(new ErrorMessage(e.getMessage())).build());
       // }
    }
    @PostMapping("/add_role")
    public  ResponseEntity<UserRole> addRole(@RequestBody RoleReqDto roleReqDto) {
        return ResponseEntity.ok(userDetailService.addUserRole(roleReqDto.roleName()));
    }
    @GetMapping("/list_all_roles")
    public  ResponseEntity<Collection<UserRole>> getRoles() {
        return ResponseEntity.ok(userDetailService.getRoles());
    }
    @GetMapping("/list_all_users")
    public  ResponseEntity<Collection<AppUser>> getUsers() {
        return ResponseEntity.ok(userDetailService.getAllUsers());
    }


}
