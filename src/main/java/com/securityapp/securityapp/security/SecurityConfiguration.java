package com.securityapp.securityapp.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.securityapp.securityapp.jwt.JwtAuthenticationEntryPoint;
import com.securityapp.securityapp.jwt.JwtRequestFilter;
import com.securityapp.securityapp.jwt.user.MyUserDetailsService;

/*
 * knote: tells Spring that this is Security configuration
 * 		- will allow to create multiple usernames and passswords, instead of default pair we had in application.properties 
 */
@EnableWebSecurity
//@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

	@Autowired
	private JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
	
	@Autowired //knote: works for Jwt example code with hardcoded user
	private UserDetailsService jwtUserDetailsService;
	
	@Autowired
	private MyUserDetailsService myUserDetailsService;
	
	
	@Autowired
	private JwtRequestFilter jwtRequestFilter;
	
	/*
	 * knote: since we have password encoder, user will pass unencryoted normal password.
	 * 		-but we should have mechanism to store encrypted password in DB when we create user, like we are doing in SecurityappApplication.run method
	 */
	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		// configure AuthenticationManager so that it knows from where to load
		// user for matching credentials
		// Use BCryptPasswordEncoder
		//auth.userDetailsService(jwtUserDetailsService).passwordEncoder(getPasswordEncoder());
		auth.userDetailsService(myUserDetailsService).passwordEncoder(getPasswordEncoder());
	}
	
	/*
	 * knote: tell Spring framework that we have this kind of bean, 
	 * 		so someone wherever is doing "Autowired" injection for this bean, provide it
	 */
	@Bean
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}
	
	/*
	 * knote: config for JWT
	 */
	@Override
	protected void configure(HttpSecurity httpSecurity) throws Exception {
		/*
		//h2 console
		httpSecurity.authorizeRequests()
        .antMatchers("/h2-console/**").hasRole("ADMIN")//allow h2 console access to admins only
        .anyRequest().authenticated()//all other urls can be access by any authenticated role
        .and().formLogin()//enable form login instead of basic login
        .and().csrf().ignoringAntMatchers("/h2-console/**")//don't apply CSRF protection to /h2-console
        .and().headers().frameOptions().sameOrigin();//allow use of frame to same origin urls
		*/
		
		// We don't need CSRF for this example
		httpSecurity.csrf().disable()
		// dont authenticate this particular request
		//.authorizeRequests().antMatchers("/h2c/**").permitAll().	//knote: specify multiple urls with "antMatchers"
		.authorizeRequests().antMatchers("/authenticate", "/h2c/**").permitAll().	//knote: specify multiple urls with "antMatchers"
		// all other requests need to be authenticated
		anyRequest().authenticated()
		//h2 console related
		//.and().csrf().ignoringAntMatchers("/h2-console/**")//don't apply CSRF protection to /h2-console
        .and().headers().frameOptions().sameOrigin()//allow use of frame to same origin urls		
		// make sure we use stateless session; session won't be used to
		// store user's state.
		.and().exceptionHandling().authenticationEntryPoint(jwtAuthenticationEntryPoint).and().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
		// Add a filter to validate the tokens with every request
		httpSecurity.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);
	}
	
	/*
	 * -need to comment following for JWT , because we have method above: configureGlobal
	 * knote: configuration for users and roles 
	 
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		/* 
		 * knote: set custom/additional configuration on auth object
		 * 		-once we do this, Spring will not add default username password account
		 */
		/*
		auth.inMemoryAuthentication()
			.withUser("username")
			.password("password")
			.roles("USER");
			
	}
	*/
	
	/*
	 * knote: we should not deal with plain text passwords (like we did above)
	 * 		-so Spring provides various passwordEncoders to hash/encode passwords
	 * 			-example: BCryptPasswordEncoder
	 */
	@Bean
	public PasswordEncoder getPasswordEncoder() {
		return new BCryptPasswordEncoder();//for JWT
		//return NoOpPasswordEncoder.getInstance();	//for now we have no encoding
	}
}
