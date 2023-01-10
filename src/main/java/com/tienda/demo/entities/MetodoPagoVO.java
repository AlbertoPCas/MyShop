package com.tienda.demo.entities;

public class MetodoPagoVO {
	private int id;
	private String metodo_pago;
	private String icono;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getMetodo_pago() {
		return metodo_pago;
	}

	public void setMetodo_pago(String metodo_pago) {
		this.metodo_pago = metodo_pago;
	}

	public String getIcono() {
		return icono;
	}

	public void setIcono(String icono) {
		this.icono = icono;
	}

	public MetodoPagoVO(int id, String metodo_pago, String icono) {
		super();
		this.id = id;
		this.metodo_pago = metodo_pago;
		this.icono = icono;
	}
}