package com.testing.cuatroEnLinea.view;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class Views {

	@GetMapping("/home")
	public String index() {
		return "index";
	}

	@GetMapping("/partido")
	public String partidoView() {
		return "partido";
	}


}
