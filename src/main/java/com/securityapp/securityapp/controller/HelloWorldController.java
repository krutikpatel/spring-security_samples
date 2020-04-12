package com.securityapp.securityapp.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloWorldController {

	@GetMapping("/")
	public String home() {
		return ("<h1> Jay Swaminarayan </h1>");
	}
	
	@GetMapping("/hello")
	public String hello() {
		return ("<h1> Jay Swaminarayan </h1>");
	}
}
