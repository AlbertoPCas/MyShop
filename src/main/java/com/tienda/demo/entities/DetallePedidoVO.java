package com.tienda.demo.entities;

public class DetallePedidoVO {
	private int id;
	private int id_pedido;
	private int id_producto;
	private double precio_unidad;
	private int unidades;
	private float impuesto;
	private double total;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getId_pedido() {
		return id_pedido;
	}

	public void setId_pedido(int id_pedido) {
		this.id_pedido = id_pedido;
	}

	public int getId_producto() {
		return id_producto;
	}

	public void setId_producto(int id_producto) {
		this.id_producto = id_producto;
	}

	public double getPrecio_unidad() {
		return precio_unidad;
	}

	public void setPrecio_unidad(double precio_unidad) {
		this.precio_unidad = precio_unidad;
	}

	public int getUnidades() {
		return unidades;
	}

	public void setUnidades(int unidades) {
		this.unidades = unidades;
	}

	public float getImpuesto() {
		return impuesto;
	}

	public void setImpuesto(float impuesto) {
		this.impuesto = impuesto;
	}

	public double getTotal() {
		return total;
	}

	public void setTotal(double total) {
		this.total = total;
	}

	public DetallePedidoVO(int id, int id_pedido, int id_producto, double precio_unidad, int unidades, float impuesto,
			double total) {
		super();
		this.id = id;
		this.id_pedido = id_pedido;
		this.id_producto = id_producto;
		this.precio_unidad = precio_unidad;
		this.unidades = unidades;
		this.impuesto = impuesto;
		this.total = total;
	}

	public DetallePedidoVO(int id_pedido, int id_producto, double precio_unidad, int unidades, float impuesto,
			double total) {
		super();
		this.id_pedido = id_pedido;
		this.id_producto = id_producto;
		this.precio_unidad = precio_unidad;
		this.unidades = unidades;
		this.impuesto = impuesto;
		this.total = total;
	}
}