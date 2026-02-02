package loans.com.example.demo.security;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

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
                .csrf(csrf -> csrf.disable())
                .sessionManagement(session ->
                        session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )
                .authorizeHttpRequests(auth -> auth

                        // ✅ OPTIONS MUST BE ALLOWED
                        .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()

                        // ✅ PUBLIC APIs
                        .requestMatchers(HttpMethod.POST,
                                "/api/users/login",
                                "/api/users/register",
                                "/api/users/admin/register"
                        ).permitAll()

                        // 🔐 ALL OTHERS NEED JWT
                        .anyRequest().authenticated()
                )
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    // 🔥 REAL CORS CONFIG (MOST IMPORTANT)
    @Bean
    public FilterRegistrationBean<CorsFilter> corsFilter() {

        CorsConfiguration config = new CorsConfiguration();

        config.setAllowCredentials(false);
        config.setAllowedOrigins(List.of(
                "https://dazzling-dragon-6c4dfa.netlify.app"
        ));
        config.setAllowedMethods(List.of(
                "GET", "POST", "PUT", "DELETE", "OPTIONS"
        ));
        config.setAllowedHeaders(List.of("*"));

        UrlBasedCorsConfigurationSource source =
                new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);

        FilterRegistrationBean<CorsFilter> bean =
                new FilterRegistrationBean<>(new CorsFilter(source));

        bean.setOrder(Ordered.HIGHEST_PRECEDENCE); // ⭐ VERY IMPORTANT
        return bean;
    }

    @Bean
    public AuthenticationManager authenticationManager(
            AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }
}
