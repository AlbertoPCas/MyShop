package com.tienda.demo.controller;

import java.sql.Timestamp;
import java.util.ArrayList;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.tienda.demo.entities.CategoriaVO;
import com.tienda.demo.entities.DetallePedidoVO;
import com.tienda.demo.entities.MetodoPagoVO;
import com.tienda.demo.entities.OpcionMenuVO;
import com.tienda.demo.entities.PedidoVO;
import com.tienda.demo.entities.ProductoVO;
import com.tienda.demo.entities.UsuarioVO;
import com.tienda.demo.service.Service;

@Controller
@RequestMapping("/catalogo")
public class ControladorCatalogo {
	private static int selectItemsGuardado = 20;
	private static int pageGuardada = 1;
	private static String categoriaGuardada = "";
	private static String ordenGuardado = "";

	private ArrayList<ProductoVO> listaProductos;

	@GetMapping("/inicio")
	public String inicio() {
		pageGuardada = 1;
		return "redirect:/catalogo";
	}

	@GetMapping("")
	public String inicioApp(Model model, HttpSession session, @RequestParam(defaultValue = "-1") Integer page) {
		if (session.getAttribute("carritoProd") == null) {
			session.setAttribute("carritoProd", new ArrayList<ProductoVO>());
		}

		if (session.getAttribute("userSession") == null) {
			UsuarioVO anonimo = new UsuarioVO();
			anonimo.setId_rol(1);
			session.setAttribute("userSession", anonimo);
		}

		cargarOpciones(session);
		cargarCategorias(session);

		if (pageGuardada == 0) {
			pageGuardada = 1;
		} else if (page != -1) {
			pageGuardada = page;
		}

		double numPaginasDouble = (Service.getNumberRecords(categoriaGuardada) / selectItemsGuardado);
		int numPaginas;
		if (Math.round(numPaginasDouble) > numPaginasDouble) {
			numPaginas = (int) Math.round(numPaginasDouble);
		} else {
			numPaginas = (int) Math.round(numPaginasDouble) + 1;
		}

		if (pageGuardada > numPaginas) {
			pageGuardada = numPaginas;
		}

		listaProductos = cargarLista(pageGuardada, selectItemsGuardado, categoriaGuardada, ordenGuardado);

		model.addAttribute("selectItems", selectItemsGuardado);
		model.addAttribute("page", pageGuardada);
		model.addAttribute("numPaginas", numPaginas);
		model.addAttribute("listaProductos", listaProductos);
		model.addAttribute("categoria", categoriaGuardada);
		model.addAttribute("orden", ordenGuardado);

		return "index";
	}

	/* CONFIGURACIÓN */
	private ArrayList<ProductoVO> cargarLista(int page, int selectItems, String categoria, String ordenGuardado) {
		int inicio = ((page - 1) * selectItems) + 1;
		return Service.getRecords(inicio, selectItems, categoria, ordenGuardado);
	}

	private void cargarOpciones(HttpSession session) {
		UsuarioVO user = (UsuarioVO) session.getAttribute("userSession");
		ArrayList<OpcionMenuVO> listaOpciones = Service.obtenerOpciones(user.getId_rol());
		session.setAttribute("listaOpciones", listaOpciones);
	}

	private void cargarCategorias(HttpSession session) {
		ArrayList<CategoriaVO> listaCategorias = Service.obtenerCategorias();
		session.setAttribute("listaCategorias", listaCategorias);
	}

	@GetMapping("/cambiarCategoria")
	public String cambiarCategoria(@RequestParam String categoria, RedirectAttributes redirectAttributes) {
		categoriaGuardada = categoria;
		pageGuardada = 1;
		redirectAttributes.addFlashAttribute("categoria", categoria);
		return "redirect:/catalogo";
	}

	@GetMapping("/cambiarOrden")
	public String cambiarOrden(@RequestParam String orden, RedirectAttributes redirectAttributes) {
		ordenGuardado = orden;
		pageGuardada = 1;
		redirectAttributes.addFlashAttribute("orden", orden);
		return "redirect:/catalogo";
	}

	@GetMapping("/selectItems")
	public String selectItems(@RequestParam Integer selectItems) {
		selectItemsGuardado = selectItems;
		return "redirect:/catalogo?page=" + pageGuardada;
	}

	@GetMapping("/addProducto")
	public String addProducto(@RequestParam int id, HttpSession session) {
		addProducto(id, 1, session);
		return "redirect:/catalogo/";
	}

	@GetMapping("/addProductoDetail")
	public String addProducto(@RequestParam int id, @RequestParam int cantidad, @RequestParam String add,
			HttpSession session) {
		addProducto(id, cantidad, session);
		if (add.equals("Añadir al carrito")) {
			return "redirect:/catalogo/verProducto?id=" + id;
		} else if (add.equals("Comprar ahora")) {
			return "redirect:/catalogo/verCarrito";
		}
		return "redirect:/catalogo/";
	}

	private void addProducto(int id, int cantidad, HttpSession session) {
		ArrayList<ProductoVO> carritoProd = (ArrayList<ProductoVO>) session.getAttribute("carritoProd");
		ProductoVO producto = null;

		/* Comprobación de si ha sido añadido a la cesta */
		for (ProductoVO prod : carritoProd) {
			if (prod.getId() == id) {
				producto = prod;
			}
		}
		if (producto == null) {
			producto = Service.obtenerProducto(id);
			carritoProd.add(producto);
		}

		for (int i = 0; i < cantidad; i++) {
			producto.setCantidadAComprar((producto.getCantidadAComprar() + 1));
		}
		session.setAttribute("carritoProd", carritoProd);
	}

	@GetMapping("/verProducto")
	public String verProducto(@RequestParam int id, Model model) {
		model.addAttribute("producto", Service.obtenerProducto(id));
		return "producto";
	}

	@GetMapping("/verCarrito")
	public String verCarrito() {
		return "carrito";
	}

	@GetMapping("/borrarProducto")
	public String borrarProducto(@RequestParam int id, HttpSession session) {
		ArrayList<ProductoVO> carritoProd = (ArrayList<ProductoVO>) session.getAttribute("carritoProd");
		for (ProductoVO producto : carritoProd) {
			if (producto.getId() == id) {
				carritoProd.remove(producto);
			}
		}
		session.setAttribute("carritoProd", carritoProd);
		return "carrito";
	}

	/* CONFIGURAR PAGO */
	@GetMapping("/pagarCarrito")
	public String pagarProducto(HttpSession session, Model model) {
		ArrayList<ProductoVO> carritoProd = (ArrayList<ProductoVO>) session.getAttribute("carritoProd");
		double precioTotal = 0;
		for (ProductoVO producto : carritoProd) {
			precioTotal += (producto.getCantidadAComprar() * producto.getPrecio());
		}
		model.addAttribute("precioTotal", precioTotal);

		ArrayList<MetodoPagoVO> listaMetodosPago = Service.obtenerMetodosPago();
		model.addAttribute("listaMetodosPago", listaMetodosPago);
		return "pago";
	}

	@GetMapping("/confirmarPago")
	public String confirmarPago(@RequestParam String metodo, @RequestParam double total, HttpSession session) {
		if (metodo.equals("Tarjeta")) {
			// Pago con tarjeta
		} else if (metodo.equals("PayPal")) {
			// Pago con PayPal
		}

		ArrayList<ProductoVO> carritoProd = (ArrayList<ProductoVO>) session.getAttribute("carritoProd");
		UsuarioVO usuario = (UsuarioVO) session.getAttribute("userSession");
		int idUser = usuario.getId();
		Timestamp fechaPedido = new Timestamp(System.currentTimeMillis());
		PedidoVO pedido = new PedidoVO(idUser, fechaPedido, metodo, "Pendiente", "00000", total);
		Service.insertarPedido(pedido);
		int idPedido = Service.obtenerIdPedido(idUser, fechaPedido);

		while (carritoProd.size() > 0) {
			ProductoVO prodActual = carritoProd.get(0);
			Service.bajarCantidad(prodActual.getId(), prodActual.getCantidadAComprar());
			DetallePedidoVO detalleActual = new DetallePedidoVO(idPedido, prodActual.getId(), prodActual.getPrecio(),
					prodActual.getCantidadAComprar(), prodActual.getImpuesto(),
					(prodActual.getCantidadAComprar() * prodActual.getPrecio()));
			Service.insertarDetallePedido(detalleActual);
			carritoProd.remove(0);
		}

		session.setAttribute("carritoProd", carritoProd);
		return "redirect:/catalogo";
	}

}
