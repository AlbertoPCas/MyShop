package com.tienda.demo.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;

import com.tienda.demo.conexion.Conexion;
import com.tienda.demo.entities.ProductoVO;

public class ProductoDAO {
	private static Connection conexion = Conexion.getConexion();
	private static PreparedStatement st;
	private static ResultSet rs;

	public static ArrayList<ProductoVO> obtenerProductos() throws SQLException {
		ArrayList<ProductoVO> listaProductos = null;
		if (conexion != null) {
			listaProductos = new ArrayList<ProductoVO>();

			PreparedStatement pst = conexion.prepareStatement("SELECT * FROM productos");
			ResultSet rs = pst.executeQuery();

			while (rs.next()) {
				ProductoVO producto = new ProductoVO(rs.getInt("id"), rs.getInt("id_categoria"), rs.getString("nombre"),
						rs.getString("descripcion"), rs.getDouble("precio"), rs.getInt("stock"),
						rs.getFloat("impuesto"), rs.getString("imagen"));

				listaProductos.add(producto);
			}

		} else {
			System.out.println("Conexion no realizada");
		}
		return listaProductos;
	}

	public static ProductoVO obtenerProducto(int id) throws SQLException {
		ProductoVO tempProd = null;
		if (conexion != null) {
			PreparedStatement pst = conexion.prepareStatement("SELECT * FROM productos WHERE id = ?");
			pst.setInt(1, id);

			ResultSet rs = pst.executeQuery();

			while (rs.next()) {
				tempProd = new ProductoVO(rs.getInt("id"), rs.getInt("id_categoria"), rs.getString("nombre"),
						rs.getString("descripcion"), rs.getDouble("precio"), rs.getInt("stock"),
						rs.getFloat("impuesto"), rs.getString("imagen"));
			}
		} else {
			System.out.println("Conexion no realizada");
		}
		return tempProd;
	}

	public static ArrayList<ProductoVO> getRecords(int start, int total, String categoria, String orden) {
		ArrayList<ProductoVO> listaProductos = new ArrayList<ProductoVO>();
		try {
			conexion = Conexion.getConexion();
			if (conexion != null) {
				String consulta = "SELECT * FROM productos";
				if (!categoria.equals("")) {
					consulta += " WHERE id_categoria = (SELECT id FROM categorias WHERE nombre = '" + categoria + "')";
				}
				if (!orden.equals("")) {
					consulta += " ORDER BY " + orden + " ASC";
				}
				consulta += " LIMIT " + (start - 1) + "," + total;

				ProductoDAO.st = ProductoDAO.conexion.prepareStatement(consulta);
				ProductoDAO.rs = ProductoDAO.st.executeQuery();
				while (rs.next()) {
					ProductoVO producto = new ProductoVO(rs.getInt("id"), rs.getInt("id_categoria"),
							rs.getString("nombre"), rs.getString("descripcion"), rs.getDouble("precio"),
							rs.getInt("stock"), rs.getFloat("impuesto"), rs.getString("imagen"));
					listaProductos.add(producto);
				}
			}
		} catch (Exception e) {
			System.out.println(e);
		}
		return listaProductos;
	}

	public static int getNumberRecords(String categoria) {
		try {
			conexion = Conexion.getConexion();
			if (conexion != null) {
				String consulta = "SELECT count(id) FROM productos";
				if (!categoria.equals("")) {
					consulta += " WHERE id_categoria = (SELECT id FROM categorias WHERE nombre = '" + categoria + "')";
				}
				st = conexion.prepareStatement(consulta);
				rs = st.executeQuery();
				if (rs.next()) {
					return rs.getInt(1);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return 0;
	}

	public static void bajarCantidad(int idProducto, int cantidad) throws SQLException {
		if (conexion != null) {
			PreparedStatement pst = conexion.prepareStatement("SELECT * FROM productos WHERE id = ?");
			pst.setInt(1, idProducto);
			ResultSet rs = pst.executeQuery();

			if (rs.next()) {
				int stock = rs.getInt("stock");

				pst = conexion.prepareStatement("UPDATE productos SET stock = ? WHERE id =  ?");
				pst.setInt(1, (stock - cantidad));
				pst.setInt(2, idProducto);

				pst.executeUpdate();
				conexion.commit();
			}

		} else {
			System.out.println("Conexion no realizada");
		}
	}

	public static boolean insertarProducto(ProductoVO producto) throws SQLException {
		if (conexion != null) {
			PreparedStatement stmt = conexion.prepareStatement("INSERT INTO productos (id_categoria, nombre, descripcion, precio, stock, fecha_alta, impuesto, imagen) VALUES (?,?,?,?,?,?,?,?)");
			stmt.setInt(1, producto.getId_categoria());
			stmt.setString(2, producto.getNombre());
			stmt.setString(3, producto.getDescripcion());
			stmt.setDouble(4, producto.getPrecio());
			stmt.setInt(5, producto.getStock());
			stmt.setTimestamp(6, new Timestamp(System.currentTimeMillis()));
			stmt.setFloat(7, producto.getImpuesto());
			stmt.setString(8, producto.getImagen());
			stmt.executeUpdate();
			conexion.commit();
			return true;
		} else {
			System.out.println("Conexion no realizada");
			return false;
		}
	}

	public static boolean darBajaProducto(int idProducto) throws SQLException {
		if (conexion != null) {
			PreparedStatement pst = conexion
					.prepareStatement("UPDATE productos SET fecha_baja = ? WHERE id = ?");
			pst.setTimestamp(1, new Timestamp(System.currentTimeMillis()));
			pst.setInt(2, idProducto);

			pst.executeUpdate();
			conexion.commit();
			return true;
		} else {
			System.out.println("Conexion no realizada");
		}
		return false;
	}

	public static boolean actualizarProducto(int idProducto, ProductoVO producto) throws SQLException {
		if (conexion != null) {
			PreparedStatement pst = conexion.prepareStatement("UPDATE productos SET id_categoria = ?, nombre = ?, descripcion = ?, precio = ?, stock = ?, impuesto = ?, imagen = ? WHERE id = ?");
			pst.setInt(1, producto.getId_categoria());
			pst.setString(2, producto.getNombre());
			pst.setString(3, producto.getDescripcion());
			pst.setDouble(4, producto.getPrecio());
			pst.setInt(5, producto.getStock());
			pst.setFloat(6, producto.getImpuesto());
			pst.setString(7, producto.getImagen());
			pst.setInt(8, idProducto);

			pst.executeUpdate();
			conexion.commit();
			return true;
		} else {
			System.out.println("Conexion no realizada");
		}
		return false;
	}

	public static boolean eliminarProducto(int idProducto) throws SQLException {
		if (conexion != null) {
			PreparedStatement pst = conexion.prepareStatement("DELETE FROM productos WHERE id = ?");
			pst.setInt(1, idProducto);

			pst.executeUpdate();
			conexion.commit();
			return true;
		} else {
			System.out.println("Conexion no realizada");
		}
		return false;
	}
}