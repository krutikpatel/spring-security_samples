package com.securityapp.securityapp.security;

import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/*
 * knote: tells Spring that this is Security configuration
 * 		- will allow to create multiple usernames and passswords, instead of default pair we had in application.properties 
 */
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		/* 
		 * knote: set custom/additional configuration on auth object
		 * 		-once we do this, Spring will not add default username password account
		 */
		auth.inMemoryAuthentication()
			.withUser("username")
			.password("password")
			.roles("USER");
	}
	
	/*
	 * knote: we should not deal with plain text passwords (like we did above)
	 * 		-so Spring provides various passwordEncoders to hash/encode passwords
	 * 			-example: BCryptPasswordEncoder
	 */
	@Bean
	public PasswordEncoder getPasswordEncoder() {
		return new BCryptPasswordEncoder();
		//return NoOpPasswordEncoder.getInstance();	//for now we have no encoding
	}
}
