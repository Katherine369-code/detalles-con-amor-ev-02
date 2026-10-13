<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="java.util.List,co.detallesconamor.model.Producto" %>
<% List<Producto> productos = (List<Producto>) request.getAttribute("productos"); Producto editar = (Producto) request.getAttribute("productoEditar"); String mensaje = request.getParameter("mensaje"); %>
<!DOCTYPE html><html lang="es"><head><meta charset="UTF-8"><meta name="viewport" content="width=device-width, initial-scale=1.0"><title>Detalles con Amor | Productos</title><link rel="stylesheet" href="<%= request.getContextPath() %>/assets/css/estilos.css"></head>
<body><header class="hero"><div class="marca">♡ Detalles con Amor</div><p>Un detalle especial empieza con una idea bonita</p><h1>Administración de productos</h1><p>Registra y organiza desayunos sorpresa, regalos y detalles.</p></header>
<main class="contenedor">
<% if ("creado".equals(mensaje)) { %><div class="alerta">Producto creado correctamente.</div><% } else if ("actualizado".equals(mensaje)) { %><div class="alerta">Producto actualizado correctamente.</div><% } else if ("eliminado".equals(mensaje)) { %><div class="alerta">Producto eliminado correctamente.</div><% } %>
<section class="panel"><h2><%= editar == null ? "Agregar producto" : "Editar producto" %></h2>
<form action="<%= request.getContextPath() %>/productos" method="post" class="formulario">
<input type="hidden" name="accion" value="<%= editar == null ? "crear" : "actualizar" %>"><% if (editar != null) { %><input type="hidden" name="id" value="<%= editar.getId() %>"><% } %>
<label>Nombre del producto<input type="text" name="nombre" maxlength="100" required value="<%= editar == null ? "" : escapar(editar.getNombre()) %>" placeholder="Ej. Desayuno sorpresa"></label>
<label>Descripción<textarea name="descripcion" rows="3" maxlength="500" placeholder="Describe el producto"><%= editar == null ? "" : escapar(editar.getDescripcion()) %></textarea></label>
<div class="dos-columnas"><label>Precio (COP)<input type="number" name="precio" min="0" step="0.01" required value="<%= editar == null ? "" : editar.getPrecio() %>" placeholder="35000"></label><label>Unidades disponibles<input type="number" name="stock" min="0" step="1" required value="<%= editar == null ? "" : editar.getStock() %>" placeholder="10"></label></div>
<div class="acciones"><button class="boton" type="submit"><%= editar == null ? "Guardar producto" : "Guardar cambios" %></button><% if (editar != null) { %><a class="boton secundario" href="<%= request.getContextPath() %>/productos">Cancelar</a><% } %></div></form></section>
<section class="panel"><div class="titulo-lista"><h2>Catálogo de productos</h2><span class="contador"><%= productos == null ? 0 : productos.size() %> productos</span></div>
<div class="tabla-contenedor"><table><thead><tr><th>ID</th><th>Producto</th><th>Descripción</th><th>Precio</th><th>Stock</th><th>Acciones</th></tr></thead><tbody>
<% if (productos == null || productos.isEmpty()) { %><tr><td colspan="6" class="vacio">Todavía no hay productos. Agrega el primero en el formulario.</td></tr><% } else { for (Producto p : productos) { %>
<tr><td><%= p.getId() %></td><td><strong><%= escapar(p.getNombre()) %></strong></td><td><%= escapar(p.getDescripcion()) %></td><td>$<%= p.getPrecio().toPlainString() %></td><td><%= p.getStock() %></td><td><div class="acciones-tabla"><a class="enlace" href="<%= request.getContextPath() %>/productos?accion=editar&id=<%= p.getId() %>">Editar</a><form action="<%= request.getContextPath() %>/productos" method="post" onsubmit="return confirm('¿Deseas eliminar este producto?');"><input type="hidden" name="accion" value="eliminar"><input type="hidden" name="id" value="<%= p.getId() %>"><button class="enlace peligro" type="submit">Eliminar</button></form></div></td></tr>
<% } } %></tbody></table></div></section>
</main><footer>© Detalles con Amor · Módulo académico CRUD con Servlets, JSP y JDBC</footer></body></html>
<%!
private String escapar(String valor) {
 if (valor == null) return "";
 return valor.replace("&", "&amp;").replace("<", "&lt;").replace(">", "&gt;").replace("\"", "&quot;").replace("'", "&#39;");
}
%>
