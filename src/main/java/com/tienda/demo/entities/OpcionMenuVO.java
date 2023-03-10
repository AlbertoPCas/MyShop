package com.tienda.demo.entities;

public class OpcionMenuVO {
	private int id;
	private int id_rol;
	private String nombre_opcion;
	private String url_opcion;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getId_rol() {
		return id_rol;
	}

	public void setId_rol(int id_rol) {
		this.id_rol = id_rol;
	}

	public String getNombre_opcion() {
		return nombre_opcion;
	}

	public void setNombre_opcion(String nombre_opcion) {
		this.nombre_opcion = nombre_opcion;
	}

	public String getUrl_opcion() {
		return url_opcion;
	}

	public void setUrl_opcion(String url_opcion) {
		this.url_opcion = url_opcion;
	}

	public OpcionMenuVO(int id, int id_rol, String nombre_opcion, String url_opcion) {
		super();
		this.id = id;
		this.id_rol = id_rol;
		this.nombre_opcion = nombre_opcion;
		this.url_opcion = url_opcion;
	}
}