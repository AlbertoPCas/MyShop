package com.tienda.demo.service;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;

import com.tienda.demo.dao.CategoriaDAO;
import com.tienda.demo.dao.DetallePedidoDAO;
import com.tienda.demo.dao.MetodoPagoDAO;
import com.tienda.demo.dao.OpcionMenuDAO;
import com.tienda.demo.dao.PedidoDAO;
import com.tienda.demo.dao.ProductoDAO;
import com.tienda.demo.dao.UsuarioDAO;
import com.tienda.demo.entities.CategoriaVO;
import com.tienda.demo.entities.DetallePedidoVO;
import com.tienda.demo.entities.MetodoPagoVO;
import com.tienda.demo.entities.OpcionMenuVO;
import com.tienda.demo.entities.PedidoVO;
import com.tienda.demo.entities.ProductoVO;
import com.tienda.demo.entities.UsuarioVO;

public class Service {
	public static ArrayList<ProductoVO> getRecords(int start, int total, String categoria, String orden) {
		return ProductoDAO.getRecords(start, total, categoria, orden);
	}

	public static int getNumberRecords(String categoria) {
		return ProductoDAO.getNumberRecords(categoria);
	}

	public static boolean insertarUsuario(UsuarioVO usuario) {
		try {
			return UsuarioDAO.insertarUsuario(usuario);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	public static UsuarioVO obtenerUsuario(String email) {
		try {
			return UsuarioDAO.obtenerUsuario(email);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static UsuarioVO obtenerUsuario(int id) {
		try {
			return UsuarioDAO.obtenerUsuario(id);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static boolean darBajaUsuario(int idCliente) {
		try {
			return UsuarioDAO.darBajaUsuario(idCliente);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	public static boolean actualizarUsuario(int idUser, UsuarioVO usuario) {
		try {
			return UsuarioDAO.actualizarUsuario(idUser, usuario);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	public static boolean eliminarUsuario(int idUsuario) {
		try {
			return UsuarioDAO.eliminarUsuario(idUsuario);
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return false;
	}

	public static ProductoVO obtenerProducto(int id) {
		try {
			return ProductoDAO.obtenerProducto(id);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static boolean insertarProducto(ProductoVO producto) {
		try {
			return ProductoDAO.insertarProducto(producto);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	public static boolean darBajaProducto(int idProducto) {
		try {
			return ProductoDAO.darBajaProducto(idProducto);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	public static boolean actualizarProducto(int idProducto, ProductoVO producto) {
		try {
			ProductoDAO.actualizarProducto(idProducto, producto);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	public static boolean eliminarProducto(int idProducto) {
		try {
			return ProductoDAO.eliminarProducto(idProducto);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	public static ArrayList<OpcionMenuVO> obtenerOpciones(int id_rol) {
		try {
			return OpcionMenuDAO.obtenerOpciones(id_rol);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static ArrayList<MetodoPagoVO> obtenerMetodosPago() {
		try {
			return MetodoPagoDAO.obtenerMetodosPago();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static void bajarCantidad(int idProducto, int cantidad) {
		try {
			ProductoDAO.bajarCantidad(idProducto, cantidad);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static boolean insertarPedido(PedidoVO pedido) {
		try {
			return PedidoDAO.insertarPedido(pedido);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	public static int obtenerIdPedido(int idUser, Timestamp fechaPedido) {
		try {
			return PedidoDAO.obtenerIdPedido(idUser, fechaPedido);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return -1;
	}

	/* DETALLES PEDIDO */
	public static boolean insertarDetallePedido(DetallePedidoVO detallePedido) {
		try {
			return DetallePedidoDAO.insertarDetallePedido(detallePedido);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	public static ArrayList<DetallePedidoVO> obtenerDetallesPedido(int idPedido) {
		try {
			return DetallePedidoDAO.obtenerDetallesPedido(idPedido);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	public static ArrayList<PedidoVO> obtenerPedidos(int idUser) {
		try {
			return PedidoDAO.obtenerPedidos(idUser);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static boolean actualizarEstado(int idProducto, String estado) {
		try {
			return PedidoDAO.actualizarEstado(idProducto, estado);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	public static ArrayList<PedidoVO> obtenerPedidos(String estado) {
		try {
			return PedidoDAO.obtenerPedidos(estado);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static ArrayList<CategoriaVO> obtenerCategorias() {
		try {
			return CategoriaDAO.obtenerCategorias();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
}
