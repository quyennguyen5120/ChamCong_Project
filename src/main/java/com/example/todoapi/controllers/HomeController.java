package com.example.todoapi.controllers;

import com.example.todoapi.dtos.JwtResponse;
import com.example.todoapi.dtos.LoginRequest;
import com.example.todoapi.dtos.SignupRequest;
import com.example.todoapi.dtos.StaffSalaryDTO;
import com.example.todoapi.entities.RefreshToken;
import com.example.todoapi.entities.RoleEntity;
import com.example.todoapi.entities.StaffEntity;
import com.example.todoapi.entities.UserEntity;
import com.example.todoapi.jwt.JwtUtils;
import com.example.todoapi.payload.request.TokenRefreshRequest;
import com.example.todoapi.payload.response.TokenRefreshResponse;
import com.example.todoapi.repositories.RoleRepository;
import com.example.todoapi.repositories.StaffRepository;
import com.example.todoapi.repositories.UserRepository;
import com.example.todoapi.services.MailService;
import com.example.todoapi.services.RefreshTokenService;
import com.example.todoapi.services.UserDetailsImpl;
import com.example.todoapi.services.UserService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/auth")
public class HomeController {
    @Autowired
    AuthenticationManager authenticationManager;
    @Autowired
    JwtUtils jwtUtils;
    @Autowired
    RefreshTokenService refreshTokenService;
    @Autowired
    UserService userService;
    @Autowired
    UserRepository userRepository;
    @Autowired
    RoleRepository roleRepository;
    @Autowired
    StaffRepository staffRepository;
    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired
    MailService mailService;

    @Operation(summary = "????ng nh???p", description = "")
    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@RequestBody LoginRequest loginRequest) {
        Authentication authentication = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

        String jwt = jwtUtils.generateJwtToken(userDetails);

        List<String> roles = userDetails.getAuthorities().stream().map(item -> item.getAuthority())
                .collect(Collectors.toList());

        RefreshToken refreshToken = refreshTokenService.createRefreshToken(userDetails.getId());
        StaffEntity staff = staffRepository.getStaffByUserId(userDetails.getId());
        return ResponseEntity.ok(new JwtResponse(jwt, refreshToken.getToken(), userDetails.getId(),
                userDetails.getUsername(), userDetails.getEmail(), roles, staff.getId()));
    }
    @PostMapping("/refreshtoken")
    public ResponseEntity<?> refreshtoken(@RequestBody TokenRefreshRequest request) {
        String requestRefreshToken = request.getRefreshToken();
        return refreshTokenService.findByToken(requestRefreshToken)
                .map(refreshTokenService::verifyExpiration)
                .map(RefreshToken::getUser)
                .map(user -> {
                    String token = jwtUtils.generateTokenFromUsername(user.getUsername());
                    return ResponseEntity.ok(new TokenRefreshResponse(token, requestRefreshToken));
                })
                .orElseThrow(null);
    }

    @GetMapping("/add")
    public String addUser(){
        RoleEntity roleUser = roleRepository.findByName("ROLE_USER");
        UserEntity user = new UserEntity();
        user.setUsername("userz");
        user.setPassword(passwordEncoder.encode("123"));
        user.setRoles(Set.of(roleUser));
        user = userRepository.save(user);
        StaffEntity staff = new StaffEntity();
        staff.setFullname("Nguyen Huu Quyen");
        staff.setAddress("Bac Giang");
        staff.setAge(20);
        staff.setEmail("quyenproxxxx@gmail.com");
        staff.setUserEntity(user);
        staffRepository.save(staff);
        return "";
    }

    @Operation(summary = "????ng k??", description = "")
    @PostMapping("/signup")
    public ResponseEntity<?> signupUser(@RequestBody SignupRequest signupRequest){
        return ResponseEntity.ok(userService.addNewUser(signupRequest));
    }

    @Operation(summary = "G???i mail l????ng th??ng n??y cho 1 ng?????i b???t k???", description = "g???i mail cho 1 b???t ky")
    @GetMapping("/sendMailByMail")
    public ResponseEntity<?> sendMailByMail(
            @RequestParam(value = "email", required = false) Optional<String> email,
            @RequestParam(value = "content", required = false) Optional<String> content,
            @RequestParam(value = "title", required = false) Optional<String> title){
        return ResponseEntity.ok(mailService.SendMailByEmailAndContent(email.orElse(null),content.orElse(null),title.orElse(null)));
    };
}
