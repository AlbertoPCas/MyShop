package com.tienda.demo.controller;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.tienda.demo.entities.UsuarioVO;
import com.tienda.demo.service.Service;

@Controller
public class ControladorLogin {
	@GetMapping("/login")
	public String entradaLogin() {
		return "login";
	}

	@PostMapping("/loginUser")
	public String entradaUser(Model model, @RequestParam(defaultValue = "-1") String inputEmail,
			@RequestParam(defaultValue = "-1") String inputPass, HttpSession session) {
		if (!inputEmail.equals("-1") && !inputPass.equals("-1")) {
			if (Service.obtenerUsuario(inputEmail) != null) {
				UsuarioVO user = Service.obtenerUsuario(inputEmail);
				if (user.getClave().equals(inputPass)) {
					session.setAttribute("userSession", user);
					return "redirect:/catalogo";
				} else {
					model.addAttribute("mensaje", "(!) Error, datos incorrectos.");
				}
			} else {
				model.addAttribute("mensaje", "(!) Error, datos incorrectos.");
			}
		} else {
			model.addAttribute("mensaje", "(!) Por favor, rellena todos los campos.");
		}
		return "login";
	}
}