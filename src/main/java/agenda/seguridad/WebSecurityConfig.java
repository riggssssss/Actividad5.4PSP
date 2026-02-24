package agenda.seguridad;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import agenda.entidades.Rol;
import agenda.seguridad.Constans;

@EnableWebSecurity
@Configuration
public class WebSecurityConfig {

    @Autowired
    private JWTAuthorizationFilter jwtAuthorizationFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(HttpMethod.POST, Constans.LOGIN_URL).permitAll()
                        .requestMatchers(HttpMethod.DELETE, "/contactos/**").hasAuthority("ROLE_" + Rol.ADMIN.name())
                        .anyRequest().authenticated()
                )
                .addFilterAfter(jwtAuthorizationFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}
