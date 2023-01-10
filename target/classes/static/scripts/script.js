function recargarPaginaSelect() {
	document.getElementById("cargarServletSelect").submit();
}

function cambiarCategoriaMetodo() {
	document.getElementById("cambiarCategoria").submit();
}

function cambiarOrdenMetodo() {
	document.getElementById("cambiarOrden").submit();
}

function recuperarProductoMetodo() {
	var valorID = document.getElementById("inputID").value;
	document.getElementById("idEnviar").value = valorID;
	document.getElementById("recuperarProducto").submit();
}

function recuperarClienteMetodo() {
	var valorID = document.getElementById("inputID").value;
	document.getElementById("idEnviar").value = valorID;
	document.getElementById("recuperarCliente").submit();
}

function recuperarEmpleadoMetodo() {
	var valorID = document.getElementById("inputID").value;
	document.getElementById("idEnviar").value = valorID;
	document.getElementById("recuperarEmpleado").submit();
}