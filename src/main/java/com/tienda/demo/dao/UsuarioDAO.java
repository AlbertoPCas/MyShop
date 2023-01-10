package com.tienda.demo.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.tienda.demo.conexion.Conexion;
import com.tienda.demo.entities.UsuarioVO;

public class UsuarioDAO {
	private static Connection conexion = Conexion.getConexion();

	public static boolean insertarUsuario(UsuarioVO usuario) throws SQLException {
		if (conexion != null) {
			PreparedStatement stmt = conexion.prepareStatement(
					"INSERT INTO usuarios (id_rol, email, clave, nombre, apellido1, apellido2, direccion, provincia, localidad, telefono, dni, activo) VALUES (?,?,?,?,?,?,?,?,?,?,?,?)");
			stmt.setInt(1, usuario.getId_rol());
			stmt.setString(2, usuario.getEmail());
			stmt.setString(3, usuario.getClave());
			stmt.setString(4, usuario.getNombre());
			stmt.setString(5, usuario.getApellido1());
			stmt.setString(6, usuario.getApellido2());
			stmt.setString(7, usuario.getDireccion());
			stmt.setString(8, usuario.getProvincia());
			stmt.setString(9, usuario.getLocalidad());
			stmt.setString(10, usuario.getTelefono());
			stmt.setString(11, usuario.getDni());
			stmt.setInt(12, usuario.getActivo());
			stmt.executeUpdate();
			conexion.commit();
			return true;
		} else {
			System.out.println("Conexion no realizada");
			return false;
		}
	}

	public static UsuarioVO obtenerUsuario(String email) throws SQLException {
		UsuarioVO tempUser = null;
		if (conexion != null) {
			PreparedStatement pst = conexion.prepareStatement("SELECT * FROM usuarios WHERE email = ?");
			pst.setString(1, email);

			ResultSet rs = pst.executeQuery();

			while (rs.next()) {
				tempUser = new UsuarioVO(rs.getInt(1), rs.getInt(2), rs.getString(3), rs.getString(4), rs.getString(5),
						rs.getString(6), rs.getString(7), rs.getString(8), rs.getString(9), rs.getString(10),
						rs.getString(11), rs.getString(12), rs.getInt("activo"));
			}
		} else {
			System.out.println("Conexion no realizada");
		}
		return tempUser;
	}

	public static UsuarioVO obtenerUsuario(int id) throws SQLException {
		UsuarioVO tempUser = null;
		if (conexion != null) {
			PreparedStatement pst = conexion.prepareStatement("SELECT * FROM usuarios WHERE id = ?");
			pst.setInt(1, id);

			ResultSet rs = pst.executeQuery();

			while (rs.next()) {
				tempUser = new UsuarioVO(rs.getInt(1), rs.getInt(2), rs.getString(3), rs.getString(4), rs.getString(5),
						rs.getString(6), rs.getString(7), rs.getString(8), rs.getString(9), rs.getString(10),
						rs.getString(11), rs.getString(12), rs.getInt("activo"));
			}
		} else {
			System.out.println("Conexion no realizada");
		}
		return tempUser;
	}

	public static boolean darBajaUsuario(int idCliente) throws SQLException {
		if (conexion != null) {
			PreparedStatement pst = conexion.prepareStatement("UPDATE usuarios SET activo = 0 WHERE id = ?");
			pst.setInt(1, idCliente);

			pst.executeUpdate();
			conexion.commit();
			return true;
		} else {
			System.out.println("Conexion no realizada");
		}
		return false;
	}

	public static boolean actualizarUsuario(int idUser, UsuarioVO usuario) throws SQLException {
		if (conexion != null) {
			PreparedStatement pst = conexion.prepareStatement(
					"UPDATE usuarios SET id_rol = ?, email = ?, nombre = ?, apellido1 = ?, apellido2 = ?, direccion = ?, provincia = ?, localidad = ?, telefono = ?, dni = ? WHERE id = ?");
			pst.setInt(1, usuario.getId_rol());
			pst.setString(2, usuario.getEmail());
			pst.setString(3, usuario.getNombre());
			pst.setString(4, usuario.getApellido1());
			pst.setString(5, usuario.getApellido2());
			pst.setString(6, usuario.getDireccion());
			pst.setString(7, usuario.getProvincia());
			pst.setString(8, usuario.getLocalidad());
			pst.setString(9, usuario.getTelefono());
			pst.setString(10, usuario.getDni());
			pst.setInt(11, idUser);

			pst.executeUpdate();
			conexion.commit();
			return true;
		} else {
			System.out.println("Conexion no realizada");
		}
		return false;
	}

	public static boolean eliminarUsuario(int idUsuario) throws SQLException {
		if (conexion != null) {
			PreparedStatement pst = conexion.prepareStatement("DELETE FROM usuarios WHERE id = ?");
			pst.setInt(1, idUsuario);

			pst.executeUpdate();
			conexion.commit();
			return true;
		} else {
			System.out.println("Conexion no realizada");
		}
		return false;
	}
}
