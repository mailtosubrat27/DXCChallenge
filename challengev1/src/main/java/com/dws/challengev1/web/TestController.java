package com.dws.challengev1.web;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {
	
	@GetMapping("/msg")
	public String showMessage() {
		return "done";
	}

}
