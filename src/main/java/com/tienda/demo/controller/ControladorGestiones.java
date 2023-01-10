package com.tienda.demo.controller;

import java.util.regex.Pattern;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.tienda.demo.entities.ProductoVO;
import com.tienda.demo.entities.UsuarioVO;
import com.tienda.demo.service.Service;

@Controller
@RequestMapping("/gestionar")
public class ControladorGestiones {
	@GetMapping("/productos")
	public String gestionProductos(Model model) {
		model.addAttribute("gestion", "productos");
		return "menugestionar";
	}

	@GetMapping("/clientes")
	public String gestionClientes(Model model) {
		model.addAttribute("gestion", "clientes");
		return "menugestionar";
	}

	@GetMapping("/empleados")
	public String gestionEmpleados(Model model) {
		model.addAttribute("gestion", "empleados");
		return "menugestionar";
	}

	@GetMapping("")
	public String operaciones(@RequestParam String opcion, @RequestParam String gestion,
			@RequestParam(defaultValue = "-1") int id, Model model) {
		if (gestion.equals("productos")) {
			ProductoVO producto;
			if (id != -1) {
				producto = Service.obtenerProducto(id);
			} else {
				producto = new ProductoVO();
			}
			model.addAttribute("producto", producto);
		} else if (gestion.equals("clientes") || gestion.equals("empleados")) {
			UsuarioVO usuario;
			if (id != -1) {
				usuario = Service.obtenerUsuario(id);
			} else {
				usuario = new UsuarioVO();
			}
			model.addAttribute("usuario", usuario);
		}

		model.addAttribute("opcion", opcion);
		model.addAttribute("gestion", gestion);
		return "opciongestionar";
	}

	/* PRODUCTOS */
	@PostMapping("/insertarProducto")
	public String insertarProducto(@RequestParam int categoria, @RequestParam(defaultValue = "") String inputNombre,
			@RequestParam(defaultValue = "") String inputDescripcion,
			@RequestParam(defaultValue = "0") double inputPrecio, @RequestParam(defaultValue = "0") int inputStock,
			@RequestParam(defaultValue = "0") float inputImpuesto,
			@RequestParam(defaultValue = "") String inputImagen) {

		try {
			ProductoVO producto = new ProductoVO(categoria, inputNombre, inputDescripcion, inputPrecio, inputStock,
					inputImpuesto, inputImagen);
			Service.insertarProducto(producto);
			return "redirect:/gestionar/productos";
		} catch (Exception e) {
			return "redirect:/error";
		}
	}

	@GetMapping("/bajaProducto")
	public String bajaProducto(@RequestParam String id) {
		try {
			int nuevoId = Integer.valueOf(id);
			Service.darBajaProducto(nuevoId);
			return "redirect:/gestionar/productos";
		} catch (Exception e) {
			return "redirect:/error";
		}
	}

	@GetMapping("/recuperarProducto")
	public String recuperarProducto(@RequestParam String id) {
		try {
			int nuevoId = Integer.valueOf(id);
			return "redirect:/gestionar?gestion=productos&opcion=actualizacion&id=" + nuevoId;
		} catch (Exception e) {
			return "redirect:/gestionar?gestion=productos&opcion=actualizacion";
		}
	}

	@PostMapping("/actualizarProducto")
	public String actualizarProducto(@RequestParam String inputID, @RequestParam(defaultValue = "0") int categoria,
			@RequestParam(defaultValue = "") String inputNombre,
			@RequestParam(defaultValue = "") String inputDescripcion,
			@RequestParam(defaultValue = "0") double inputPrecio, @RequestParam(defaultValue = "0") int inputStock,
			@RequestParam(defaultValue = "0") float inputImpuesto,
			@RequestParam(defaultValue = "") String inputImagen) {
		try {
			int nuevoId = Integer.valueOf(inputID);
			ProductoVO producto = new ProductoVO(nuevoId, categoria, inputNombre, inputDescripcion, inputPrecio,
					inputStock, inputImpuesto, inputImagen);
			Service.actualizarProducto(nuevoId, producto);
		} catch (Exception e) {
			return "redirect:/error";
		}

		return "redirect:/gestionar/productos";
	}

	@GetMapping("/eliminarProducto")
	public String eliminarProducto(@RequestParam String id) {
		try {
			int nuevoId = Integer.valueOf(id);
			Service.eliminarProducto(nuevoId);
		} catch (Exception e) {
			return "redirect:/error";
		}

		return "redirect:/gestionar/productos";
	}
	/* --------- */

	/* CLIENTES */
	@PostMapping("/altaCliente")
	public String altaCliente(RedirectAttributes redirectAttributes,
			@RequestParam(defaultValue = "-1") String inputEmail, @RequestParam(defaultValue = "-1") String inputPass,
			@RequestParam(defaultValue = "-1") String inputPass2, @RequestParam(defaultValue = "-1") String inputNombre,
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
				redirectAttributes.addFlashAttribute("mensaje", "(!) Error en el formato del correo electrónico.");
			} else if (!inputPass.equals(inputPass2)) {
				redirectAttributes.addFlashAttribute("mensaje", "(!) Las contraseñas deben coincidir.");
			} else if (Service.obtenerUsuario(inputEmail) != null) {
				redirectAttributes.addFlashAttribute("mensaje",
						"(!) El correo electrónico ya se encuentra registrado.");
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
				redirectAttributes.addFlashAttribute("mensaje", "Se ha registrado correctamente el usuario.");
			}
		} else {
			redirectAttributes.addFlashAttribute("mensaje", "(!) Error, datos incompletos.");
		}
		return "redirect:/gestionar?gestion=clientes&opcion=alta";
	}

	@GetMapping("/bajaCliente")
	public String bajaCliente(@RequestParam String id) {
		try {
			int nuevoId = Integer.valueOf(id);
			UsuarioVO usuario = Service.obtenerUsuario(nuevoId);
			if (usuario.getId_rol() == 2) {
				Service.darBajaUsuario(nuevoId);
			}
			return "redirect:/gestionar/clientes";
		} catch (Exception e) {
			return "redirect:/error";
		}
	}

	@GetMapping("/recuperarCliente")
	public String recuperarCliente(@RequestParam String id) {
		try {
			int nuevoId = Integer.valueOf(id);
			return "redirect:/gestionar?gestion=clientes&opcion=actualizacion&id=" + nuevoId;
		} catch (Exception e) {
			return "redirect:/gestionar?gestion=clientes&opcion=actualizacion";
		}
	}

	@PostMapping("/actualizarCliente")
	public String actualizarCliente(RedirectAttributes redirectAttributes, @RequestParam String inputID,
			@RequestParam(defaultValue = "-1") String inputEmail, @RequestParam(defaultValue = "-1") String inputNombre,
			@RequestParam(defaultValue = "-1") String inputApellido1,
			@RequestParam(defaultValue = "-1") String inputApellido2,
			@RequestParam(defaultValue = "-1") String inputTelefono, @RequestParam(defaultValue = "-1") String inputDNI,
			@RequestParam(defaultValue = "-1") String inputDireccion,
			@RequestParam(defaultValue = "-1") String inputLocalidad,
			@RequestParam(defaultValue = "-1") String inputProvincia) {
		if (!inputEmail.equals("-1") && !inputNombre.equals("-1") && !inputApellido1.equals("-1")
				&& !inputApellido2.equals("-1") && !inputTelefono.equals("-1") && !inputDNI.equals("-1")) {
			if (!Pattern.compile(
					"^(?=.{1,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@[^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$")
					.matcher(inputEmail).matches()) {
				redirectAttributes.addFlashAttribute("mensaje", "(!) Error en el formato del correo electrónico.");
			} else {
				UsuarioVO newUser = new UsuarioVO(2, inputEmail, inputNombre, inputApellido1, inputApellido2,
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

				try {
					int nuevoId = Integer.valueOf(inputID);
					Service.actualizarUsuario(nuevoId, newUser);
					redirectAttributes.addFlashAttribute("mensaje", "Se ha actualizado correctamente el usuario.");
				} catch (Exception e) {
					return "redirect:/error";
				}
			}
		} else {
			redirectAttributes.addFlashAttribute("mensaje", "(!) Error, datos incompletos.");
		}
		return "redirect:/gestionar?gestion=clientes&opcion=actualizacion";
	}

	@GetMapping("/eliminarCliente")
	public String eliminarCliente(@RequestParam String id) {
		try {
			int nuevoId = Integer.valueOf(id);
			if (Service.obtenerUsuario(nuevoId).getId_rol() == 2) {
				Service.eliminarUsuario(nuevoId);
			}
		} catch (Exception e) {
			return "redirect:/error";
		}

		return "redirect:/gestionar/clientes";
	}
	/* --------- */

	/* EMPLEADOS */
	@PostMapping("/altaEmpleado")
	public String altaEmpleado(RedirectAttributes redirectAttributes,
			@RequestParam(defaultValue = "-1") String inputEmail, @RequestParam(defaultValue = "-1") String inputPass,
			@RequestParam(defaultValue = "-1") String inputPass2, @RequestParam(defaultValue = "-1") String inputNombre,
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
				redirectAttributes.addFlashAttribute("mensaje", "(!) Error en el formato del correo electrónico.");
			} else if (!inputPass.equals(inputPass2)) {
				redirectAttributes.addFlashAttribute("mensaje", "(!) Las contraseñas deben coincidir.");
			} else if (Service.obtenerUsuario(inputEmail) != null) {
				redirectAttributes.addFlashAttribute("mensaje",
						"(!) El correo electrónico ya se encuentra registrado.");
			} else {
				UsuarioVO newUser = new UsuarioVO(3, inputEmail, inputPass, inputNombre, inputApellido1, inputApellido2,
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
				redirectAttributes.addFlashAttribute("mensaje", "Se ha registrado correctamente el usuario.");
			}
		} else {
			redirectAttributes.addFlashAttribute("mensaje", "(!) Error, datos incompletos.");
		}
		return "redirect:/gestionar?gestion=empleados&opcion=alta";
	}

	@GetMapping("/bajaEmpleado")
	public String bajaEmpleado(@RequestParam String id) {
		try {
			int nuevoId = Integer.valueOf(id);
			UsuarioVO usuario = Service.obtenerUsuario(nuevoId);
			if (usuario.getId_rol() == 3) {
				Service.darBajaUsuario(nuevoId);
			}
			return "redirect:/gestionar/clientes";
		} catch (Exception e) {
			return "redirect:/error";
		}
	}

	@GetMapping("/recuperarEmpleado")
	public String recuperarEmpleado(@RequestParam String id) {
		try {
			int nuevoId = Integer.valueOf(id);
			return "redirect:/gestionar?gestion=empleados&opcion=actualizacion&id=" + nuevoId;
		} catch (Exception e) {
			return "redirect:/gestionar?gestion=empleados&opcion=actualizacion";
		}
	}

	@PostMapping("/actualizarEmpleado")
	public String actualizarEmpleado(RedirectAttributes redirectAttributes, @RequestParam String inputID,
			@RequestParam(defaultValue = "-1") String inputEmail, @RequestParam(defaultValue = "-1") String inputNombre,
			@RequestParam(defaultValue = "-1") String inputApellido1,
			@RequestParam(defaultValue = "-1") String inputApellido2,
			@RequestParam(defaultValue = "-1") String inputTelefono, @RequestParam(defaultValue = "-1") String inputDNI,
			@RequestParam(defaultValue = "-1") String inputDireccion,
			@RequestParam(defaultValue = "-1") String inputLocalidad,
			@RequestParam(defaultValue = "-1") String inputProvincia) {
		if (!inputEmail.equals("-1") && !inputNombre.equals("-1") && !inputApellido1.equals("-1")
				&& !inputApellido2.equals("-1") && !inputTelefono.equals("-1") && !inputDNI.equals("-1")) {
			if (!Pattern.compile(
					"^(?=.{1,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@[^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$")
					.matcher(inputEmail).matches()) {
				redirectAttributes.addFlashAttribute("mensaje", "(!) Error en el formato del correo electrónico.");
			} else {
				UsuarioVO newUser = new UsuarioVO(2, inputEmail, inputNombre, inputApellido1, inputApellido2,
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

				try {
					int nuevoId = Integer.valueOf(inputID);
					Service.actualizarUsuario(nuevoId, newUser);
					redirectAttributes.addFlashAttribute("mensaje", "Se ha actualizado correctamente el empleado.");
				} catch (Exception e) {
					return "redirect:/error";
				}
			}
		} else {
			redirectAttributes.addFlashAttribute("mensaje", "(!) Error, datos incompletos.");
		}
		return "redirect:/gestionar?gestion=empleados&opcion=actualizacion";
	}

	@GetMapping("/eliminarEmpleado")
	public String eliminarEmpleado(@RequestParam String id) {
		try {
			int nuevoId = Integer.valueOf(id);
			if (Service.obtenerUsuario(nuevoId).getId_rol() == 3) {
				Service.eliminarUsuario(nuevoId);
			}
		} catch (Exception e) {
			return "redirect:/error";
		}

		return "redirect:/gestionar/empleados";
	}
	/* --------- */
}
