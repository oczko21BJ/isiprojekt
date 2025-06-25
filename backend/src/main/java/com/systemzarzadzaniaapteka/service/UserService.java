package com.systemzarzadzaniaapteka.service;

import com.systemzarzadzaniaapteka.model.AppUser;
import com.systemzarzadzaniaapteka.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.password.PasswordEncoder;
import com.systemzarzadzaniaapteka.security.CustomOidcUser;
import jakarta.annotation.PostConstruct;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.io.FileInputStream;
import java.io.IOException;
import org.springframework.core.io.ClassPathResource;
import java.io.InputStream;

/**
 * Klasa serwisowa do zarządzania użytkownikami w systemie zarządzania apteką.
 * 
 * <p>Klasa UserService zapewnia metody do obsługi użytkowników,
 * w tym pobieranie listy użytkowników, sprawdzanie istnienia e-maila,
 * wyszukiwanie użytkownika po e-mailu, rejestrację użytkownika,
 * uwierzytelnianie oraz przetwarzanie użytkowników OAuth2.</p>
 * 
 * @author System Zarządzania Apteką
 * @version 1.0
 * @since 1.0
 */
@Service
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }
    @PostConstruct
    public void initFirebase() throws IOException {
        InputStream serviceAccount = new ClassPathResource("firebase.json").getInputStream();

        FirebaseOptions options = new FirebaseOptions.Builder()
                .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                .build();

        if (FirebaseApp.getApps().isEmpty()) {
            FirebaseApp.initializeApp(options);
        }
    }

    public List<AppUser> getAllUsers() {
        return userRepository.findAll();
    }

    public boolean emailExists(String email) {
        return userRepository.existsByEmail(email);
    }
    public AppUser findByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + email));
    }


 @Override
 public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
     AppUser user = userRepository.findByEmail(email)
             .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + email));

     return org.springframework.security.core.userdetails.User
             .withUsername(user.getEmail())
             .password(user.getPassword())
             .authorities("ROLE_" + user.getRole())
             .build();
 }
    public class Roles {
        public static final String ADMIN = "ADMIN";
        public static final String CLIENT = "CLIENT";
        public static final String PHARMACIST = "PHARMACIST";
        // inne role
    }
    public AppUser processOAuth2User(String email, String name) {
        if (email == null || email.isEmpty()) {
            throw new IllegalArgumentException("Email is required for OAuth2 user processing");
        }

        AppUser user = userRepository.findByEmail(email).orElse(null);


        if (user == null) {
            user = new AppUser();
            user.setEmail(email);
            user.setName(name);
            user.setPhone("000000000"); // placeholder
            //user.setRole("CUSTOMER");
            user.setRole(Roles.CLIENT);
            user.setPassword("null");
            // nie ustawiamy hasla - uzytkownik loguje sie przez Google
            user = userRepository.save(user);
            System.out.println("Created new user: " + user.getEmail() + ", id: " + user.getId());
        } else {
            System.out.println("Found existing user: " + user.getEmail() + ", id: " + user.getId());
        }

        return user;
    }
    public AppUser registerUserFromMap(Map<String, String> userData) {
        String email = userData.get("email");
        if (emailExists(email)) {
            throw new IllegalStateException("User already exists");
        }

        String rawPassword = userData.get("password");
        if (rawPassword == null || rawPassword.isEmpty()) {
            throw new IllegalArgumentException("Password is required");
        }

        // Zawsze kodujemy haslo przed zapisaniem
        String encodedPassword = passwordEncoder.encode(rawPassword);

        AppUser user = new AppUser();
        user.setName(userData.get("firstName") + " " + userData.get("lastName"));
        user.setEmail(email);
        user.setPhone(userData.get("phoneNumber"));
        user.setRole(userData.getOrDefault("role", "CUSTOMER"));
        user.setPassword(encodedPassword);  // Przechowujemy zakodowane haslo!

        return userRepository.save(user);
    }

    public AppUser authenticate(String email, String password) {
        AppUser user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (user.getPassword() == null || user.getPassword().isEmpty()) {
            throw new RuntimeException("User has no password set");
        }

        // Debug: Sprawdzamy, czy haslo jest zapisane jako plain text
        if (!user.getPassword().startsWith("$2a$")) {
            System.out.println("Haslo jest zapisane jako plain text! Koduje je teraz...");
            String encodedPassword = passwordEncoder.encode(password);  // Zakoduj haslo
            user.setPassword(encodedPassword);
            userRepository.save(user);  // Zapisz haslo w bazie danych
        }

        // Debug: Sprawdzamy, czy wprowadzone haslo pasuje
        System.out.println("Wprowadzone haslo: " + password);
        System.out.println("Zakodowane haslo w bazie: " + user.getPassword());

        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new RuntimeException("Invalid password");
        }

        return user;
    }

    public AppUser getById(Long id) {
        Optional<AppUser> userOpt = userRepository.findById(id);
        if (userOpt.isPresent()) {
            return userOpt.get();
        } else {
            throw new RuntimeException("User not found with id: " + id);
        }
    }

}
