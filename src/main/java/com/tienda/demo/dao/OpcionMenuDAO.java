package com.tienda.demo.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.tienda.demo.conexion.Conexion;
import com.tienda.demo.entities.OpcionMenuVO;

public class OpcionMenuDAO {
	private static Connection conexion = Conexion.getConexion();

	public static ArrayList<OpcionMenuVO> obtenerOpciones(int id_rol) throws SQLException {
		ArrayList<OpcionMenuVO> listaOpciones = null;
		if (conexion != null) {
			listaOpciones = new ArrayList<OpcionMenuVO>();

			String consulta = "SELECT * FROM opciones_menu WHERE id_rol ";
			if(id_rol == 2) {
				consulta+= "= 2";
			} else if (id_rol == 3) {
				consulta+= "= 3";
			} else if (id_rol == 4) {
				consulta+= "= 3 OR id_rol = 4";
			}
			
			PreparedStatement pst = conexion.prepareStatement(consulta);
			ResultSet rs = pst.executeQuery();

			while (rs.next()) {
				OpcionMenuVO opcion = new OpcionMenuVO(rs.getInt("id"), rs.getInt("id_rol"),
						rs.getString("nombre_opcion"), rs.getString("url_opcion"));
				listaOpciones.add(opcion);
			}

			/* Añadir opciones comunes como Perfil y Desconectarse: */
			pst = conexion.prepareStatement("SELECT * FROM opciones_menu WHERE id_rol = 1");
			rs = pst.executeQuery();

			while (rs.next()) {
				OpcionMenuVO opcion = new OpcionMenuVO(rs.getInt("id"), rs.getInt("id_rol"),
						rs.getString("nombre_opcion"), rs.getString("url_opcion"));
				listaOpciones.add(opcion);
			}
		} else {
			System.out.println("Conexion no realizada");
		}
		return listaOpciones;
	}
}
