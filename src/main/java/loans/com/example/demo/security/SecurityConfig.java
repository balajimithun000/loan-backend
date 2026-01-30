package loans.com.example.demo.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;

import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

@Configuration
public class SecurityConfig {

    @Autowired
    private JwtFilter jwtFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http
                // 🔥 ENABLE CORS
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))

                // 🔥 DISABLE CSRF (JWT)
                .csrf(csrf -> csrf.disable())

                // 🔥 STATELESS SESSION
                .sessionManagement(session ->
                        session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )

                // 🔥 AUTH RULES
                .authorizeHttpRequests(auth -> auth

                        // ✅ ALLOW PREFLIGHT REQUESTS (MOST IMPORTANT)
                        .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()

                        // 🔓 PUBLIC ENDPOINTS
                        .requestMatchers(
                                "/",
                                "/health",
                                "/error",
                                "/actuator/**"
                        ).permitAll()

                        .requestMatchers(HttpMethod.POST,
                                "/api/users/login",
                                "/api/users/register",
                                "/api/users/admin/register"
                        ).permitAll()

                        // 🔐 ROLE BASED ACCESS
                        .requestMatchers("/api/admin/**").hasAuthority("ADMIN")
                        .requestMatchers("/api/users/**").hasAuthority("USER")

                        // 🔒 EVERYTHING ELSE
                        .anyRequest().authenticated()
                )

                // 🔥 JWT FILTER
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    // ✅ CORS CONFIGURATION
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {

        CorsConfiguration config = new CorsConfiguration();

        config.setAllowCredentials(true);
        config.setAllowedOriginPatterns(List.of(
                "https://dazzling-dragon-6c4dfa.netlify.app",
                "https://*.up.railway.app"
        ));
        config.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        config.setAllowedHeaders(List.of("*"));

        UrlBasedCorsConfigurationSource source =
                new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);

        return source;
    }

    // ✅ AUTH MANAGER
    @Bean
    public AuthenticationManager authenticationManager(
            AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }
}
