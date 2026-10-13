# Detalles con Amor — Módulo web AA2_EV02

Aplicación web académica para administrar productos de **Detalles con Amor** mediante Java Servlets, JSP, formularios HTML, JDBC y MySQL. Incluye operaciones CRUD: crear, consultar, actualizar y eliminar.

## Tecnologías
- Java 17
- Maven, empaquetado WAR
- Jakarta Servlet 6.0 y JSP/JSTL 3
- MySQL 8+
- Apache Tomcat 10.1+
- Git para control de versiones

## Requisitos previos
Instala JDK 17, Maven, MySQL 8 y Apache Tomcat 10.1 o compatible con Jakarta EE 10.

## Configuración de base de datos
1. Abre MySQL Workbench o una consola MySQL.
2. Ejecuta `database/schema.sql`.
3. Configura usuario y contraseña para tu entorno. La aplicación acepta variables de entorno `DAC_DB_URL`, `DAC_DB_USER` y `DAC_DB_PASSWORD`. Si no se definen, usa `jdbc:mysql://localhost:3306/detalles_con_amor?useSSL=false&serverTimezone=UTC`, usuario `root` y contraseña vacía. Cambia estos valores según tu equipo; no subas contraseñas reales a Git.

## Compilar y ejecutar
Desde la raíz del proyecto:
```bash
mvn clean package
```
Copia `target/detalles-con-amor-web.war` a la carpeta `webapps` de Tomcat 10.1 e inicia Tomcat. Abre:
`http://localhost:8080/detalles-con-amor-web/`

El `index.jsp` redirige al servlet `/productos`. El servlet utiliza `doGet()` para listar/mostrar edición y `doPost()` para crear, actualizar y eliminar.

## Estructura
- `config/DatabaseConnection.java`: conexión JDBC.
- `model/Producto.java`: entidad de dominio.
- `dao/ProductoDAO.java`: consultas SQL parametrizadas con `PreparedStatement`.
- `web/ProductoServlet.java`: controlador Servlet GET/POST.
- `WEB-INF/views/productos.jsp`: vista JSP con formularios HTML y tabla.
- `assets/css/estilos.css`: interfaz adaptable con identidad rosada.
- `database/schema.sql`: creación de base de datos y tabla.

## Uso
1. En el formulario, registra nombre, descripción, precio y stock.
2. Usa **Editar** para cargar un producto y guardar cambios.
3. Usa **Eliminar** para retirar un producto (solicita confirmación).
4. La tabla presenta los productos consultados desde MySQL.

## Control de versiones
El proyecto está preparado para Git. Consulta `INSTRUCCIONES_GIT.txt`. Actualiza `REPOSITORIO.txt` con la URL real del repositorio que publiques.

## Alcance y seguridad
Este es un módulo CRUD de formación, no un sistema de producción. Antes de publicarlo en internet se recomienda añadir autenticación/autorización, protección CSRF, validación robusta, registro de auditoría y configuración segura de secretos.
