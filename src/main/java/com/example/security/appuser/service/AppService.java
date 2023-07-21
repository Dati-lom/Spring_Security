package com.example.security.appuser.service;

import com.example.security.Email.EmailSender;
import com.example.security.appuser.modules.AppUser;
import com.example.security.appuser.repositories.AppUserRepository;
import com.example.security.security.token.ConfirmationToken;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
@AllArgsConstructor
public class AppService implements UserDetailsService {
    private final AppUserRepository appUserRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final ConfirmationService confirmationService;
    private final EmailSender emailSender;
    private final RegistrationService registrationService;
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return appUserRepository.findByEmail(email).orElseThrow(()-> new UsernameNotFoundException("Email not found"));
    }

    public String singUpUser(AppUser appUser){
        Optional<AppUser> optAPp = appUserRepository.findByEmail(appUser.getEmail());
        if(optAPp.isPresent()){
            throw new IllegalStateException("Email Already exists");
        }

        String encodedPassword = bCryptPasswordEncoder.encode(appUser.getPassword());

        appUser.setPassword(encodedPassword);

        appUserRepository.save(appUser);
        //Send token
        String token = UUID.randomUUID().toString();
        ConfirmationToken confirmationToken =
                new ConfirmationToken(token, LocalDateTime.now(),LocalDateTime.now().plusMinutes(15),appUser);

        confirmationService.saveConfirmationToken(confirmationToken);

        return token;
    }

    public void enableAppUser(String email){
        AppUser user = appUserRepository.findByEmail(email).orElseThrow(()->new IllegalStateException("No such user exists"));
        user.setEnabled(true);
    }
}
