package com.tienda.demo.controller;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.tienda.demo.entities.UsuarioVO;

@Controller
@RequestMapping("/acciones")
public class ControladorAcciones {
	@GetMapping("/profile")
	public String profile() {
		return "perfil";
	}

	@GetMapping("/disconnect")
	public String disconnect(HttpSession session) {
		UsuarioVO anonimo = new UsuarioVO();
		anonimo.setId_rol(1);
		session.setAttribute("userSession", anonimo);
		return "redirect:/catalogo";
	}
}