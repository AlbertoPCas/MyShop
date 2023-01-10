package com.tienda.demo.conexion;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexion {
	static String bd = "tienda_alberto_perez_castaneda";
	static String login = "root";
	static String password = "";
	static String host = "127.0.0.1";

	static String url = "jdbc:mysql://";
	static Connection conexion;

	public static Connection getConexion() {
		if (conexion == null) {
			crearConexion();
		}
		return conexion;
	}

	public static boolean crearConexion() {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			conexion = DriverManager.getConnection(url + host + "/" + bd, login, password);
			conexion.setAutoCommit(false);
		} catch (SQLException e) {
			return false;
		} catch (Exception e) {
			return false;
		}
		return true;
	}

	public static void desconectar() {
		try {
			conexion.close();
			conexion = null;
		} catch (SQLException e) {
			System.out.println("Error al cerrar la conexion");
		}
	}
}