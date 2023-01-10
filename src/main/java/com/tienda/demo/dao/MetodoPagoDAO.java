package com.tienda.demo.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.tienda.demo.conexion.Conexion;
import com.tienda.demo.entities.MetodoPagoVO;

public class MetodoPagoDAO {
	private static Connection conexion = Conexion.getConexion();

	public static ArrayList<MetodoPagoVO> obtenerMetodosPago() throws SQLException {
		ArrayList<MetodoPagoVO> listaMetodosPago = null;
		if (conexion != null) {
			listaMetodosPago = new ArrayList<MetodoPagoVO>();

			PreparedStatement pst = conexion.prepareStatement("SELECT * FROM metodos_pago");
			ResultSet rs = pst.executeQuery();

			while (rs.next()) {
				MetodoPagoVO metodo = new MetodoPagoVO(rs.getInt("id"), rs.getString("metodo_pago"),
						rs.getString("icono"));
				listaMetodosPago.add(metodo);
			}

		} else {
			System.out.println("Conexion no realizada");
		}
		return listaMetodosPago;
	}
}