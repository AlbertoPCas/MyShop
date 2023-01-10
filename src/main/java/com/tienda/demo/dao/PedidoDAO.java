package com.tienda.demo.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;

import com.tienda.demo.conexion.Conexion;
import com.tienda.demo.entities.PedidoVO;

public class PedidoDAO {
	private static Connection conexion = Conexion.getConexion();

	public static boolean insertarPedido(PedidoVO pedido) throws SQLException {
		if (conexion != null) {
			PreparedStatement stmt = conexion.prepareStatement(
					"INSERT INTO pedidos (id_usuario, fecha, metodo_pago, estado, num_factura, total) VALUES (?,?,?,?,?,?)");
			stmt.setInt(1, pedido.getId_usuario());
			stmt.setTimestamp(2, pedido.getFecha());
			stmt.setString(3, pedido.getMetodo_pago());
			stmt.setString(4, pedido.getEstado());
			stmt.setString(5, pedido.getNum_factura());
			stmt.setDouble(6, pedido.getTotal());
			stmt.executeUpdate();
			conexion.commit();
			return true;
		} else {
			System.out.println("Conexion no realizada");
			return false;
		}
	}

	public static int obtenerIdPedido(int idUser, Timestamp fechaPedido) throws SQLException {
		if (conexion != null) {
			PreparedStatement pst = conexion
					.prepareStatement("SELECT * FROM pedidos WHERE id_usuario = ? AND fecha = ?");
			pst.setInt(1, idUser);
			pst.setTimestamp(2, fechaPedido);

			ResultSet rs = pst.executeQuery();

			if (rs.next()) {
				return rs.getInt("id");
			}
		} else {
			System.out.println("Conexion no realizada");
		}
		return -1;
	}

	public static ArrayList<PedidoVO> obtenerPedidos(int idUser) throws SQLException {
		ArrayList<PedidoVO> listaPedidos = null;
		if (conexion != null) {
			listaPedidos = new ArrayList<PedidoVO>();

			PreparedStatement pst = conexion.prepareStatement("SELECT * FROM pedidos WHERE id_usuario = ?");
			pst.setInt(1, idUser);
			ResultSet rs = pst.executeQuery();

			while (rs.next()) {
				PedidoVO pedido = new PedidoVO(rs.getInt("id"), rs.getInt("id_usuario"), rs.getTimestamp("fecha"),
						rs.getString("metodo_pago"), rs.getString("estado"), rs.getString("num_factura"),
						rs.getDouble("total"));
				listaPedidos.add(pedido);
			}

		} else {
			System.out.println("Conexion no realizada");
		}
		return listaPedidos;
	}

	public static ArrayList<PedidoVO> obtenerPedidos(String estado) throws SQLException {
		ArrayList<PedidoVO> listaPedidos = null;
		if (conexion != null) {
			listaPedidos = new ArrayList<PedidoVO>();

			PreparedStatement pst = conexion.prepareStatement("SELECT * FROM pedidos WHERE estado = ?");
			pst.setString(1, estado);
			ResultSet rs = pst.executeQuery();

			while (rs.next()) {
				PedidoVO pedido = new PedidoVO(rs.getInt("id"), rs.getInt("id_usuario"), rs.getTimestamp("fecha"),
						rs.getString("metodo_pago"), rs.getString("estado"), rs.getString("num_factura"),
						rs.getDouble("total"));
				listaPedidos.add(pedido);
			}

		} else {
			System.out.println("Conexion no realizada");
		}
		return listaPedidos;
	}

	public static boolean actualizarEstado(int idProducto, String estado) throws SQLException {
		if (conexion != null) {
			PreparedStatement pst = conexion.prepareStatement("UPDATE pedidos SET estado = ? WHERE id =  ?");
			pst.setString(1, estado);
			pst.setInt(2, idProducto);

			pst.executeUpdate();
			conexion.commit();
			return true;
		} else {
			System.out.println("Conexion no realizada");
		}
		return false;
	}
}