package com.tienda.demo.controller;

import java.util.regex.Pattern;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.tienda.demo.entities.UsuarioVO;
import com.tienda.demo.service.Service;

@Controller
public class ControladorRegistro {

	@GetMapping("/registro")
	public String entradaRegistro() {
		return "registro";
	}

	@PostMapping("/registroUser")
	public String registroUser(Model model, @RequestParam(defaultValue = "-1") String inputEmail,
			@RequestParam(defaultValue = "-1") String inputPass, @RequestParam(defaultValue = "-1") String inputPass2,
			@RequestParam(defaultValue = "-1") String inputNombre,
			@RequestParam(defaultValue = "-1") String inputApellido1,
			@RequestParam(defaultValue = "-1") String inputApellido2,
			@RequestParam(defaultValue = "-1") String inputTelefono, @RequestParam(defaultValue = "-1") String inputDNI,
			@RequestParam(defaultValue = "-1") String inputDireccion,
			@RequestParam(defaultValue = "-1") String inputLocalidad,
			@RequestParam(defaultValue = "-1") String inputProvincia) {
		if (!inputEmail.equals("-1") && !inputPass.equals("-1") && !inputPass2.equals("-1") && !inputNombre.equals("-1")
				&& !inputApellido1.equals("-1") && !inputApellido2.equals("-1") && !inputTelefono.equals("-1")
				&& !inputDNI.equals("-1")) {
			if (!Pattern.compile(
					"^(?=.{1,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@[^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$")
					.matcher(inputEmail).matches()) {
				model.addAttribute("mensaje", "(!) Error en el formato del correo electrónico.");
			} else if (!inputPass.equals(inputPass2)) {
				model.addAttribute("mensaje", "(!) Las contraseñas deben coincidir.");
			} else if (Service.obtenerUsuario(inputEmail) != null) {
				model.addAttribute("mensaje", "(!) El correo electrónico ya se encuentra registrado.");
			} else {
				UsuarioVO newUser = new UsuarioVO(2, inputEmail, inputPass, inputNombre, inputApellido1, inputApellido2,
						inputTelefono, inputDNI, 1);
				if (!inputDireccion.equals("-1")) {
					newUser.setDireccion(inputDireccion);
				}
				if (!inputLocalidad.equals("-1")) {
					newUser.setLocalidad(inputLocalidad);
				}
				if (!inputProvincia.equals("-1")) {
					newUser.setProvincia(inputProvincia);
				}
				Service.insertarUsuario(newUser);
				model.addAttribute("mensaje", "Se ha registrado correctamente el usuario.");
			}
		} else {
			model.addAttribute("mensaje", "(!) Error, datos incompletos.");
		}
		return "registro";
	}
}
