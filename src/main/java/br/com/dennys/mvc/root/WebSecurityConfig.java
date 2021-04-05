package br.com.dennys.mvc.root;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@Configuration
@EnableWebMvc
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
	
	@Autowired
	private DataSource dataSource;
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {

		http.authorizeRequests()
				.antMatchers("/home/**", "/novo").hasAnyAuthority("ROLE_ADM")
				.antMatchers("/usuario/cadastro").hasAnyAuthority("ROLE_ADM")
				.antMatchers("/api/**").permitAll()
				.antMatchers("/acessos/**").permitAll()
				.antMatchers("/actuator/**").permitAll()
				.anyRequest()
				.authenticated()
				.and()
				// .httpBasic()
				.formLogin(form -> form
						.loginPage("/login")
						.defaultSuccessUrl("/usuario/home", true)
						.permitAll())
						
						.logout(logout -> logout.logoutUrl("/logout").logoutSuccessUrl("/home"))
						.csrf().disable();
						;
		// .csrf().disable();
	}
	
//Para usar usuário em memória!
//	@Bean
//	@Override
//	public UserDetailsService userDetailsService() {
//		UserDetails user = User.withDefaultPasswordEncoder()
//				.username("dennys").password("12345").roles("ADM").build();
//
//		return new InMemoryUserDetailsManager(user);
//	}
	
	
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		
//		UserDetails user = User.builder()
//				.username("fernanda.arantes")
//				.password(encoder.encode("12345"))
//				.roles("ADM")
//				.build();
		
		
		
		auth.jdbcAuthentication()
			.dataSource(dataSource)
			.passwordEncoder(encoder);
			//.withUser(user);
	}
	
}