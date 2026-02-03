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
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

@Configuration
public class SecurityConfig {

    private final JwtFilter jwtFilter;

    public SecurityConfig(JwtFilter jwtFilter) {
        this.jwtFilter = jwtFilter;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http
                // 🔥 CORS MUST BE FIRST
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))

                // ❌ CSRF not needed for JWT
                .csrf(csrf -> csrf.disable())

                // 🔐 Stateless session
                .sessionManagement(session ->
                        session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )

                // 🔓 Authorization rules
                .authorizeHttpRequests(auth -> auth

                        // ✅ VERY IMPORTANT – OPTIONS must be allowed
                        .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()

                        // ✅ Public APIs
                        .requestMatchers(
                                "/api/users/login",
                                "/api/users/register",
                                "/api/users/admin/register",
                                "/actuator/**"
                        ).permitAll()

                        // 🔒 Everything else secured
                        .anyRequest().authenticated()
                )

                // 🔐 JWT filter
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    // 🌍 GLOBAL CORS CONFIG
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {

        CorsConfiguration config = new CorsConfiguration();

        // 🔥 Netlify frontend origin
        config.setAllowedOriginPatterns(List.of(
                "https://dazzling-dragon-6c4dfa.netlify.app"
        ));

        // 🔥 Required methods
        config.setAllowedMethods(List.of(
                "GET", "POST", "PUT", "DELETE", "OPTIONS"
        ));

        // 🔥 Required headers
        config.setAllowedHeaders(List.of("*"));

        // Optional
        config.setExposedHeaders(List.of("Authorization"));

        // ❌ DO NOT ENABLE credentials
        // config.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source =
                new UrlBasedCorsConfigurationSource();

        source.registerCorsConfiguration("/**", config);

        return source;
    }

    // 🔑 Authentication Manager
    @Bean
    public AuthenticationManager authenticationManager(
            AuthenticationConfiguration configuration
    ) throws Exception {
        return configuration.getAuthenticationManager();
    }
}
