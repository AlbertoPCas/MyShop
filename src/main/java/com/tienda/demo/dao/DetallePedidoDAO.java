package com.tienda.demo.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.tienda.demo.conexion.Conexion;
import com.tienda.demo.entities.DetallePedidoVO;

public class DetallePedidoDAO {
	private static Connection conexion = Conexion.getConexion();

	public static boolean insertarDetallePedido(DetallePedidoVO detallePedido) throws SQLException {
		if (conexion != null) {
			PreparedStatement stmt = conexion.prepareStatement(
					"INSERT INTO detalles_pedido (id_pedido, id_producto, precio_unidad, unidades, impuesto, total) VALUES (?,?,?,?,?,?)");
			stmt.setInt(1, detallePedido.getId_pedido());
			stmt.setInt(2, detallePedido.getId_producto());
			stmt.setDouble(3, detallePedido.getPrecio_unidad());
			stmt.setInt(4, detallePedido.getUnidades());
			stmt.setFloat(5, detallePedido.getImpuesto());
			stmt.setDouble(6, detallePedido.getTotal());
			stmt.executeUpdate();
			conexion.commit();
			return true;
		} else {
			System.out.println("Conexion no realizada");
			return false;
		}
	}

	public static ArrayList<DetallePedidoVO> obtenerDetallesPedido(int idPedido) throws SQLException {
		ArrayList<DetallePedidoVO> listaDetallesPedido = null;
		if (conexion != null) {
			listaDetallesPedido = new ArrayList<DetallePedidoVO>();

			PreparedStatement pst = conexion.prepareStatement("SELECT * FROM detalles_pedido WHERE id_pedido = ?");
			pst.setInt(1, idPedido);
			ResultSet rs = pst.executeQuery();

			while (rs.next()) {
				listaDetallesPedido.add(new DetallePedidoVO(rs.getInt("id"), rs.getInt("id_pedido"),
						rs.getInt("id_producto"), rs.getDouble("precio_unidad"), rs.getInt("unidades"),
						rs.getFloat("impuesto"), rs.getDouble("total")));
			}
		} else {
			System.out.println("Conexion no realizada");
		}
		return listaDetallesPedido;
	}
}