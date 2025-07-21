package romasan.homework_4.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import romasan.homework_4.security.JwtAuthenticationFilter;
import romasan.homework_4.repository.UserRepository;
import romasan.homework_4.service.JwtService;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {
    private final JwtService jwtService;
    private final UserRepository repository;

    public SecurityConfiguration(final JwtService jwtService, final UserRepository repository) {
        this.jwtService = jwtService;
        this.repository = repository;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(final HttpSecurity http) throws Exception {
        return http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/api/v1/registration", "/api/v1/authorization").permitAll()
                        .anyRequest().authenticated())
                .sessionManagement(sess -> sess
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .addFilterBefore(new JwtAuthenticationFilter(this.jwtService, this.repository), UsernamePasswordAuthenticationFilter.class)
                .build();
    }
}
