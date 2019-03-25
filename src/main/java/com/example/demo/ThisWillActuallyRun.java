package com.example.demo;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ThisWillActuallyRun {

	@RequestMapping("/")
	public String home() {
		return "Hello World!";
	}

}
