package loans.com.example.demo.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
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
                // ❌ CSRF not needed for JWT
                .csrf(csrf -> csrf.disable())

                // ⭐ THIS LINE IS VERY IMPORTANT ⭐
                .cors(Customizer.withDefaults())

                .sessionManagement(session ->
                        session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )

                .authorizeHttpRequests(auth -> auth
                        // ⭐ OPTIONS preflight must be allowed
                        .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()

                        // ⭐ Public APIs
                        .requestMatchers(
                                "/api/users/register",
                                "/api/users/login",
                                "/api/users/admin/register"
                        ).permitAll()

                        // 🔒 Others
                        .anyRequest().authenticated()
                );

        return http.build();
    }
}
