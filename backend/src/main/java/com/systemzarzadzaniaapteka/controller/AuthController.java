package com.systemzarzadzaniaapteka.controller;

import com.systemzarzadzaniaapteka.model.AppUser;
import com.systemzarzadzaniaapteka.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import java.util.Map;
import com.systemzarzadzaniaapteka.security.CustomOidcUser;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.googleapis.util.Utils;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import java.util.Collections;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseToken;
import com.google.firebase.auth.FirebaseAuthException;
import com.systemzarzadzaniaapteka.security.JwtTokenProvider;
/**
 * Kontroler obsługujący uwierzytelnianie użytkowników.
 */
@RestController
@RequestMapping("/api/users")
public class AuthController {

    private final UserService userService;
  /**
     * Konstruktor kontrolera autentykacji.
     *
     * @param userService serwis użytkowników
     * @param jwtTokenProvider dostawca tokenów JWT
     */
    @Autowired
    public AuthController(UserService userService) {
        this.userService = userService;
    }

    /**
     * Uwierzytelnienie uzytkownika za pomoca emaila i hasla.
     * @param loginData Map zawierajacy email i haslo.
     * @return ResponseEntity z informacja o sukcesie lub bledzie.
     */
    @PostMapping("/authenticate")
    public ResponseEntity<?> authenticateUser(@RequestBody Map<String, String> loginData) {
        String email = loginData.get("email");
        String password = loginData.get("password");

        if (email == null || password == null) {
            return ResponseEntity.badRequest().body(Map.of(
                    "success", false,
                    "message", "Email and password are required"
            ));
        }

        try {
            AppUser user = userService.authenticate(email, password);
            String jwt = jwtTokenProvider.generateToken(user);
            return ResponseEntity.ok(Map.of(
                    "success", true,
                    "message", "Authentication successful",
                    "accessToken", jwt,
                    "user", Map.of(
                            "id", user.getId(),
                            "name", user.getName(),
                            "email", user.getEmail(),
                            "role", user.getRole()
                    )
            ));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of(
                    "success", false,
                    "message", "Invalid email or password"
            ));
        }
    }
     /**
     * Uwierzytelnia użytkownika za pomocą tokena Firebase.
     *
     * @param body ciało żądania z tokenem Firebase
     * @return odpowiedź z tokenem JWT lub błędem
     */
    @PostMapping("/authenticate/firebase")
    public ResponseEntity<?> authenticateWithFirebase(@RequestBody Map<String, String> body) {
        String idTokenString = body.get("id_token");

        if (idTokenString == null) {
            return ResponseEntity.badRequest().body(Map.of(
                    "success", false,
                    "message", "idToken is required"
            ));
        }

        try {
            FirebaseToken decodedToken = FirebaseAuth.getInstance().verifyIdToken(idTokenString);
            String email = decodedToken.getEmail();
            String name = (String) decodedToken.getName();

            AppUser user = userService.processOAuth2User(email, name);
            String jwt = jwtTokenProvider.generateToken(user);

            return ResponseEntity.ok(Map.of(
                    "success", true,
                    "message", "Authentication successful",
                    "accessToken", jwt,
                    "user", Map.of(
                            "id", user.getId(),
                            "name", user.getName(),
                            "email", user.getEmail(),
                            "role", user.getRole()
                    )
            ));
        } catch (FirebaseAuthException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of(
                    "success", false,
                    "message", "Invalid Firebase ID token"
            ));
        }
    }
    @Autowired
    JwtTokenProvider jwtTokenProvider;

        /**
     * Uwierzytelnia użytkownika za pomocą tokena Google.
     *
     * @param body ciało żądania z tokenem Google
     * @return odpowiedź z tokenem JWT lub błędem
     */

 @PostMapping("/authenticate/google")
 public ResponseEntity<?> authenticateWithGoogle(@RequestBody Map<String, String> body) {
     String idTokenString = body.get("id_token");
    System.out.println("test999:" + idTokenString);
     if (idTokenString == null) {
         return ResponseEntity.badRequest().body(Map.of(
                 "success", false,
                 "message", "idToken is required"
         ));
     }

     try {
         GoogleIdTokenVerifier verifier = new GoogleIdTokenVerifier.Builder(
                 new NetHttpTransport(),
                 Utils.getDefaultJsonFactory()
         )
                 .setAudience(Collections.singletonList("182032682556-fglp9iq1dr51c1uavr7lls07fkmeeuou.apps.googleusercontent.com"))
                 .build();

         GoogleIdToken idToken = verifier.verify(idTokenString);
         if (idToken != null) {
             GoogleIdToken.Payload payload = idToken.getPayload();
             String email = payload.getEmail();
             String name = (String) payload.get("name");

             // Znajdz lub stworz uzytkownika
             AppUser user = userService.processOAuth2User(email, name);
             String jwt = jwtTokenProvider.generateToken(user);
             return ResponseEntity.ok(Map.of(
                     "success", true,
                     "message", "Authentication successful",
                     "accessToken", jwt,
                     "user", Map.of(
                             "id", user.getId(),
                             "name", user.getName(),
                             "email", user.getEmail(),
                             "role", user.getRole()
                     )
             ));
         } else {
             return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of(
                     "success", false,
                     "message", "Invalid ID token"
             ));
         }

     } catch (Exception e) {
         e.printStackTrace();
         return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of(
                 "success", false,
                 "message", "Error while verifying ID token"
         ));
     }
 }
    @GetMapping("/me")
    public ResponseEntity<?> getCurrentUser(@AuthenticationPrincipal CustomOidcUser principal) {
        if (principal == null) {
            System.out.println("PRINCIPAL: " + principal);
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of(
                    "success", false,
                    "message", "User not authenticated"
            ));
        }
        AppUser user = principal.getAppUser();


        return ResponseEntity.ok(Map.of(
                "success", true,
                "user", Map.of(
                        "id", user.getId(),
                        "name", user.getName(),
                        "email", user.getEmail(),
                        "role", user.getRole()
                )
        ));
    }
}
