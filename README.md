# spring-security_samples
spring-security related samples

# Important Links

* https://spring.io/guides/topicals/spring-security-architecture/ - Spring Security Internals
  * https://github.com/spring-guides/top-spring-security-architecture
  
  
* Spring Security Guides : https://www.baeldung.com/security-spring
* Springboot Security + JWT : https://www.javainuse.com/spring/boot-jwt
* Baeldung repos :
  * https://github.com/eugenp/learn-spring-security
  * https://github.com/Baeldung/spring-security-registration

* Database backed UserDetailsService : https://www.baeldung.com/spring-security-authentication-with-a-database

# JWT notes
We can store more info in JWT, be adding "claims" to token

* package com.securityapp.securityapp.jwt has all code related to JWT based authentication
* now project supports saving User object with credentials to H2 DB, H2Console is enabled (and url /h2c , is given unauthorized access for now)
* password will be stored Encrypted in DB using: PasswordEncoder bean (see example in SecurityappApplication.java)
* important classes :
  * SecurityConfiguration - all security config here, we specify password encoder, userDetailService, authenticated and un-authenticated urls, roles etc 
  * JwtRequestFilter - does authentication for any incoming request by matching token
  * JwtTokenUtil - actual JWT generation, token expiry time and related logic
