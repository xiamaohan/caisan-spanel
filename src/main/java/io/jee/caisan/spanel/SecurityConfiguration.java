package io.jee.caisan.spanel;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
	
	@Autowired
	private UserDetailsService userDetailsService;

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Override
	protected UserDetailsService userDetailsService() {
		return userDetailsService;
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests().antMatchers("/register*", "/login*").permitAll().anyRequest().authenticated().and()
				.formLogin().loginPage("/login").permitAll().and()
				.logout().permitAll().and().rememberMe().key("fdgfgd");
	}

	@Override
	public void configure(WebSecurity web) throws Exception {
		web.ignoring().antMatchers(new String[] { "/static/**", "/webjars/**", "/api/**" });
	}

}