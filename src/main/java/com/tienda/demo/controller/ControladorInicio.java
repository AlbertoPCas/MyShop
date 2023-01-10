package com.tienda.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("")
public class ControladorInicio {
	@GetMapping("")
	public String inicio() {
		return "redirect:/catalogo";
	}

	@GetMapping("/error")
	public String error() {
		return "redirect:/catalogo";
	}
}