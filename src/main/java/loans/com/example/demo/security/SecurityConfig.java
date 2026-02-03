package loans.com.example.demo.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http
                // JWT use panrom, CSRF thevai illa
                .csrf(csrf -> csrf.disable())

                // ⭐ VERY IMPORTANT – enable CORS
                .cors(cors -> {})

                // Session illa (JWT)
                .sessionManagement(session ->
                        session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )

                .authorizeHttpRequests(auth -> auth
                        // ⭐ Preflight OPTIONS must be allowed
                        .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()

                        // ⭐ Public APIs
                        .requestMatchers(
                                "/api/users/register",
                                "/api/users/login",
                                "/api/users/admin/register"
                        ).permitAll()

                        // 🔒 Remaining all secured
                        .anyRequest().authenticated()
                );

        return http.build();
    }
}
