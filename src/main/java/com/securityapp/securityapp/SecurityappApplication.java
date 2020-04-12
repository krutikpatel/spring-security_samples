package com.securityapp.securityapp;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.securityapp.securityapp.jwt.user.MyUser;
import com.securityapp.securityapp.jwt.user.MyUserDetailsService;

@SpringBootApplication
public class SecurityappApplication implements CommandLineRunner{

	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	MyUserDetailsService userDetailsService;
	
	@Autowired
	PasswordEncoder bcrypt;
	
	public static void main(String[] args) {
		SpringApplication.run(SecurityappApplication.class, args);
				
	}

	@Override
	public void run(String... args) throws Exception {
		//create sample user, which will otherwise happen through POST register user method
		logger.info("################### pw = "+bcrypt.encode("abcd"));
		String pw = bcrypt.encode("abcd");
		
		MyUser myUser = new MyUser("krutik", pw);
		userDetailsService.saveUser(myUser);		
		
	}

}
