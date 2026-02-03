package loans.com.example.demo.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class SecurityConfig {

    private final JwtFilter jwtFilter;

    public SecurityConfig(JwtFilter jwtFilter) {
        this.jwtFilter = jwtFilter;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http
                // ❌ CSRF not needed for JWT
                .csrf(csrf -> csrf.disable())

                // ❌ DO NOT enable cors() here
                // CORS is handled by CorsFilterConfig

                .sessionManagement(session ->
                        session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )

                .authorizeHttpRequests(auth -> auth
                        // ⭐ OPTIONS always allowed
                        .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()

                        // ⭐ Public APIs
                        .requestMatchers(
                                "/api/users/login",
                                "/api/users/register",
                                "/api/users/admin/register",
                                "/actuator/**"
                        ).permitAll()

                        // ⭐ Protected APIs
                        .anyRequest().authenticated()
                )

                // ⭐ JWT filter
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public AuthenticationManager authenticationManager(
            AuthenticationConfiguration configuration
    ) throws Exception {
        return configuration.getAuthenticationManager();
    }
}
