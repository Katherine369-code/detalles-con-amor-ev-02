package co.detallesconamor.web;

import co.detallesconamor.dao.ProductoDAO;
import co.detallesconamor.model.Producto;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.SQLException;

@WebServlet(name = "ProductoServlet", urlPatterns = {"/productos"})
public class ProductoServlet extends HttpServlet {
    private final ProductoDAO productoDAO = new ProductoDAO();

    /** GET: lista productos o muestra el formulario de edición. */
    @Override protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        try {
            String accion = request.getParameter("accion");
            if ("editar".equals(accion)) {
                int id = Integer.parseInt(request.getParameter("id"));
                Producto producto = productoDAO.buscarPorId(id);
                if (producto == null) { response.sendError(HttpServletResponse.SC_NOT_FOUND, "Producto no encontrado"); return; }
                request.setAttribute("productoEditar", producto);
            }
            request.setAttribute("productos", productoDAO.listar());
            request.getRequestDispatcher("/WEB-INF/views/productos.jsp").forward(request, response);
        } catch (NumberFormatException e) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Identificador inválido");
        } catch (SQLException e) {
            throw new ServletException("No fue posible consultar los productos. Verifique la base de datos.", e);
        }
    }

    /** POST: inserta, actualiza o elimina; después redirige para evitar reenvíos. */
    @Override protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String accion = request.getParameter("accion");
        try {
            if ("eliminar".equals(accion)) {
                int id = Integer.parseInt(request.getParameter("id"));
                productoDAO.eliminar(id);
                response.sendRedirect(request.getContextPath() + "/productos?mensaje=eliminado");
                return;
            }
            String nombre = request.getParameter("nombre");
            String descripcion = request.getParameter("descripcion");
            String precioTexto = request.getParameter("precio");
            String stockTexto = request.getParameter("stock");
            if (nombre == null || nombre.isBlank() || precioTexto == null || stockTexto == null) {
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Nombre, precio y stock son obligatorios"); return;
            }
            BigDecimal precio = new BigDecimal(precioTexto);
            int stock = Integer.parseInt(stockTexto);
            if (precio.signum() < 0 || stock < 0) { response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Precio y stock no pueden ser negativos"); return; }
            Producto producto = new Producto();
            producto.setNombre(nombre.trim()); producto.setDescripcion(descripcion == null ? "" : descripcion.trim()); producto.setPrecio(precio); producto.setStock(stock);
            if ("actualizar".equals(accion)) {
                producto.setId(Integer.parseInt(request.getParameter("id")));
                productoDAO.actualizar(producto);
                response.sendRedirect(request.getContextPath() + "/productos?mensaje=actualizado");
            } else {
                productoDAO.insertar(producto);
                response.sendRedirect(request.getContextPath() + "/productos?mensaje=creado");
            }
        } catch (NumberFormatException e) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Precio, stock o identificador tienen un formato inválido");
        } catch (SQLException e) {
            throw new ServletException("No fue posible guardar el producto. Verifique la conexión y la base de datos.", e);
        }
    }
}
