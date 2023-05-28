package m7.graduatework.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.bind.annotation.CrossOrigin;

@Configuration
@EnableWebSecurity
@CrossOrigin(value = "http://localhost:3000")
public class WebSecurityConfig {

    private static final String [] PERMIT_ALL ={
        "/swagger-resources/**",
        "/swagger-ui.html",
        "/v3/api-docs",
        "/webjars/**",
        "/login",
        "/register"
};
    private static final String [] PERMIT_AUTHENTICATED ={
            "/ads/**",
            "/users/**"
    };

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http
                .csrf(AbstractHttpConfigurer::disable)
                .cors(AbstractHttpConfigurer::disable)
                .httpBasic(Customizer.withDefaults())
                .authorizeHttpRequests((requests) -> requests
                        .requestMatchers(PERMIT_ALL).permitAll()
//                        .requestMatchers(PERMIT_AUTHENTICATED).authenticated()
                        .anyRequest().permitAll()
                );

        return http.build();
    }

    @Bean
    public InMemoryUserDetailsManager userDetailsService() {
        UserDetails user = User.withDefaultPasswordEncoder()
                .username("user@gmail.com")
                .password("password")
                .passwordEncoder((plainText) -> passwordEncoder().encode(plainText))
                .roles("USER")
                .build();
        return new InMemoryUserDetailsManager(user);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}