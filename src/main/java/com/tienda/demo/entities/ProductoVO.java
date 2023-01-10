package com.tienda.demo.entities;

import java.sql.Timestamp;

public class ProductoVO {
	private int id;
	private int id_categoria;
	private String nombre;
	private String descripcion;
	private double precio;
	private int stock;
	private Timestamp fecha_alta;
	private Timestamp fecha_baja;
	private float impuesto;
	private String imagen;
	
	private int cantidadAComprar = 0;

	public int getId() {
		return id;
	}

	public int getId_categoria() {
		return id_categoria;
	}

	public String getNombre() {
		return nombre;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public double getPrecio() {
		return precio;
	}

	public int getStock() {
		return stock;
	}

	public String getImagen() {
		return imagen;
	}

	public float getImpuesto() {
		return impuesto;
	}

	public void setImpuesto(float impuesto) {
		this.impuesto = impuesto;
	}

	public ProductoVO(int id, int id_categoria, String nombre, String descripcion, double precio, int stock,
			Timestamp fecha_alta, Timestamp fecha_baja, float impuesto, String imagen) {
		super();
		this.id = id;
		this.id_categoria = id_categoria;
		this.nombre = nombre;
		this.descripcion = descripcion;
		this.precio = precio;
		this.stock = stock;
		this.fecha_alta = fecha_alta;
		this.fecha_baja = fecha_baja;
		this.impuesto = impuesto;
		this.imagen = imagen;
	}

	public ProductoVO(int id, int id_categoria, String nombre, String descripcion, double precio, int stock,
			float impuesto, String imagen) {
		super();
		this.id = id;
		this.id_categoria = id_categoria;
		this.nombre = nombre;
		this.descripcion = descripcion;
		this.precio = precio;
		this.stock = stock;
		this.impuesto = impuesto;
		this.imagen = imagen;
	}
	
	public ProductoVO(int id_categoria, String nombre, String descripcion, double precio, int stock,
			float impuesto, String imagen) {
		super();
		this.id_categoria = id_categoria;
		this.nombre = nombre;
		this.descripcion = descripcion;
		this.precio = precio;
		this.stock = stock;
		this.impuesto = impuesto;
		this.imagen = imagen;
	}

	public ProductoVO() {
	}

	/* Cantidad a comprar a la hora de añadirlo al carrito */
	public int getCantidadAComprar() {
		return cantidadAComprar;
	}

	public void setCantidadAComprar(int cantidadAComprar) {
		this.cantidadAComprar = cantidadAComprar;
	}
}