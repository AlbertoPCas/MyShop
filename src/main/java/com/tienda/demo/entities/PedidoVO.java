package com.tienda.demo.entities;

import java.sql.Timestamp;

public class PedidoVO {
	private int id;
	private int id_usuario;
	private Timestamp fecha;
	private String metodo_pago;
	private String estado;
	private String num_factura;
	private double total;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getId_usuario() {
		return id_usuario;
	}

	public void setId_usuario(int id_usuario) {
		this.id_usuario = id_usuario;
	}

	public Timestamp getFecha() {
		return fecha;
	}

	public void setFecha(Timestamp fecha) {
		this.fecha = fecha;
	}

	public String getMetodo_pago() {
		return metodo_pago;
	}

	public void setMetodo_pago(String metodo_pago) {
		this.metodo_pago = metodo_pago;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public String getNum_factura() {
		return num_factura;
	}

	public void setNum_factura(String num_factura) {
		this.num_factura = num_factura;
	}

	public double getTotal() {
		return total;
	}

	public void setTotal(double total) {
		this.total = total;
	}

	public PedidoVO(int id, int id_usuario, Timestamp fecha, String metodo_pago, String estado, String num_factura,
			double total) {
		super();
		this.id = id;
		this.id_usuario = id_usuario;
		this.fecha = fecha;
		this.metodo_pago = metodo_pago;
		this.estado = estado;
		this.num_factura = num_factura;
		this.total = total;
	}

	public PedidoVO(int id_usuario, Timestamp fecha, String metodo_pago, String estado, String num_factura,
			double total) {
		super();
		this.id_usuario = id_usuario;
		this.fecha = fecha;
		this.metodo_pago = metodo_pago;
		this.estado = estado;
		this.num_factura = num_factura;
		this.total = total;
	}
}
