package loans.com.example.demo.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import java.util.List;

@Configuration
public class CorsFilterConfig {

    @Bean
    public CorsFilter corsFilter() {

        CorsConfiguration config = new CorsConfiguration();

        // ⭐ MUST
        config.setAllowCredentials(true);

        // ⭐ Netlify frontend
        config.setAllowedOriginPatterns(List.of(
                "https://dazzling-dragon-6c4dfa.netlify.app"
        ));

        // ⭐ Preflight support
        config.setAllowedMethods(List.of(
                "GET", "POST", "PUT", "DELETE", "OPTIONS"
        ));

        // ⭐ Allow all headers (important for OPTIONS)
        config.setAllowedHeaders(List.of("*"));

        config.setExposedHeaders(List.of("Authorization"));

        UrlBasedCorsConfigurationSource source =
                new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);

        return new CorsFilter(source);
    }
}
