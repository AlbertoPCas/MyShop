package com.tienda.demo.controller;

import java.util.ArrayList;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.tienda.demo.entities.DetallePedidoVO;
import com.tienda.demo.entities.PedidoVO;
import com.tienda.demo.entities.UsuarioVO;
import com.tienda.demo.service.Service;

@Controller
@RequestMapping("/pedidos")
public class ControladorPedidos {

	@GetMapping("")
	public String mostrarPedidos(Model model, HttpSession session) {
		UsuarioVO user = (UsuarioVO) session.getAttribute("userSession");
		ArrayList<PedidoVO> listaPedidos = Service.obtenerPedidos(user.getId());
		model.addAttribute("listaPedidos", listaPedidos);
		return "pedidos";
	}

	@GetMapping("/cancelarProducto")
	public String cancelarProducto(@RequestParam int id, Model model, HttpSession session) {
		UsuarioVO user = (UsuarioVO) session.getAttribute("userSession");
		if (user.getId_rol() == 2) {
			Service.actualizarEstado(id, "Cancelación solicitada");
			return "redirect:/pedidos/";
		} else if (user.getId_rol() == 4) {
			Service.actualizarEstado(id, "Cancelado");
			return "redirect:/pedidos/procesarCancelaciones";
		}
		return "redirect:/catalogo/";
	}

	@GetMapping("/verDetalles")
	public String verDetalles(@RequestParam int id, Model model) {
		ArrayList<DetallePedidoVO> listaDetallesPedido = Service.obtenerDetallesPedido(id);
		model.addAttribute("listaDetallesPedido", listaDetallesPedido);
		return "detallespedido";
	}

	@GetMapping("/procesarPedido")
	public String procesarPedidos(Model model, HttpSession session) {
		UsuarioVO user = (UsuarioVO) session.getAttribute("userSession");
		ArrayList<PedidoVO> listaPedidos = Service.obtenerPedidos("Pendiente");
		model.addAttribute("listaPedidos", listaPedidos);
		return "pedidos";
	}

	@GetMapping("/validarProducto")
	public String validarProducto(@RequestParam int id, Model model) {
		Service.actualizarEstado(id, "Enviado");
		return "redirect:/pedidos/procesarPedido";
	}

	@GetMapping("/procesarCancelaciones")
	public String procesarCancelaciones(Model model, HttpSession session) {
		UsuarioVO user = (UsuarioVO) session.getAttribute("userSession");
		ArrayList<PedidoVO> listaPedidos = Service.obtenerPedidos("Cancelación solicitada");
		model.addAttribute("listaPedidos", listaPedidos);
		return "pedidos";
	}
}