package com.tienda.demo.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.tienda.demo.conexion.Conexion;
import com.tienda.demo.entities.CategoriaVO;

public class CategoriaDAO {
	private static Connection conexion = Conexion.getConexion();

	public static ArrayList<CategoriaVO> obtenerCategorias() throws SQLException {
		ArrayList<CategoriaVO> listaCategorias = null;
		if (conexion != null) {
			listaCategorias = new ArrayList<CategoriaVO>();

			PreparedStatement pst = conexion.prepareStatement("SELECT * FROM categorias");
			ResultSet rs = pst.executeQuery();

			while (rs.next()) {
				listaCategorias
						.add(new CategoriaVO(rs.getInt("id"), rs.getString("nombre"), rs.getString("descripcion")));
			}

		} else {
			System.out.println("Conexion no realizada");
		}
		return listaCategorias;
	}
}