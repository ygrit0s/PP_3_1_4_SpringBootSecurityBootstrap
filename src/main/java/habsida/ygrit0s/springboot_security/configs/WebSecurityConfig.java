package habsida.ygrit0s.springboot_security.configs;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {
	private final SuccessUserHandler successUserHandler;

	public WebSecurityConfig(SuccessUserHandler successUserHandler) {
		this.successUserHandler = successUserHandler;
	}

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http
				.authorizeHttpRequests()
					.requestMatchers("/", "/login").permitAll()
					.requestMatchers("/admin/**").hasRole("ADMIN")
					.requestMatchers("/user/**").hasAnyRole("ADMIN", "USER")
					.anyRequest().authenticated()
					.and()
				.formLogin().successHandler(successUserHandler)
					.permitAll();
		return http.build();
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
}