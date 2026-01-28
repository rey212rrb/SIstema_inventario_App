package implementacion.Inventario;

import implementacion.Inventario.modelo.Producto;
import implementacion.Inventario.servicio.InventarioService;
import implementacion.Inventario.servicio.ProductoServicio;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.util.List;
import java.util.Scanner;

@SpringBootApplication
public class InventarioApplication implements CommandLineRunner {


    @Autowired
    private ProductoServicio  productoServicio;
    private static final Logger logger = LoggerFactory.getLogger(InventarioApplication.class);
    private String salto = System.lineSeparator();
    private Scanner sc = new Scanner(System.in);

    @Autowired
    private InventarioService inventarioService;

	public static void main(String[] args) {

        SpringApplication.run(InventarioApplication.class, args);

	}

    @Override
    public void run(String... args) throws Exception {

        var op = -1;

        do{

            op = getOpcion();

            ejecutarOpcion(op);

            logger.info(salto);


        }while (op != 0);


    }

    public Integer getOpcion(){

        logger.info(salto);

        logger.info("""
                
                ***** Modulo Inventario *****
                
                1.- Agregar Poducto
                2.- Modificar Poducto
                3.- Borrar Poducto
                4.- Consultar Poducto
                5.- Listar Poductos
                6.- Ajuste Inventario
                7.- Reporte Inventario
                0.- Salir 
                
                Seleccionar una opcion: """);

        return Integer.parseInt(sc.nextLine());

    }

    public void ejecutarOpcion(Integer opcion) {

        switch (opcion) {

            case 1 -> agregar();
            case 2 -> modificar();
            case 3 -> borrar();
            case 4 -> consultar();
            case 5 -> listar();
            case 6 -> ajusteInvetnario();
            case 7 -> generarReporteStock();
            case 0 -> logger.info("Adios.");

            default -> throw new IllegalStateException("Valor inesperado: " + opcion);

        }
    }

    public void agregar(){

        logger.info(" Agregar nuevo Producto: " + salto);

        productoServicio.guardarProducto(leerDatosProducto(null));

    }

    private Producto leerDatosProducto(Integer id){

        Producto producto = new Producto();
        producto.setId(id);

        logger.info("Ingresar datos de la producto: " + salto);

        logger.info("Nombre: ");
        producto.setNombre(sc.nextLine());

        logger.info("Categoria: ");
        producto.setCategoria(sc.nextLine());

        logger.info("Ubicacion: ");
        producto.setUbicacion(sc.nextLine());

        logger.info("Stock Actual: ");
        producto.setStockActual(sc.nextInt());

        logger.info("Stock Minimo: ");
        producto.setStockMinimo(sc.nextInt());

        logger.info("Precio: ");
        producto.setPrecio(sc.nextBigDecimal());

        sc.nextLine();

        return producto;

    }

    public void modificar(){

        logger.info("Modificar Producto: " + salto);

        var id = leerId("Modificar");

        productoServicio.guardarProducto(leerDatosProducto(id));

        logger.info("Producto " + id + " modificado correctamente. " +  salto);

    }

    public void borrar(){

        logger.info("Borrar Producto: ");

        var id = leerId("Borrar");

        productoServicio.borrarProducto(id);

        logger.info("Producto " + id + " borrado correctamente. " +  salto);


    }

    public void consultar(){

        logger.info("Consultar Producto: " + salto);

        var id = leerId("Consultar");

        var producto = productoServicio.getProducto(id);

        if(producto != null){

            logger.info(producto.toString() + salto);

        }else{

            logger.info("No existe el producto: " + salto);

        }

    }

    public void listar(){

        List<Producto> listaProductos = productoServicio.getProductos();

        listaProductos.forEach(producto -> logger.info(producto.toString() + salto));

    }

    public void ajusteInvetnario(){

        logger.info("Ajuste Inventario" + salto);

        var id = leerId("Ajuste Inventario");

        logger.info("Cantidad a ajustar (Positivo = Entrada, Negativo = Salida): ");
        int cantidad = Integer.parseInt(sc.nextLine());

        logger.info("Motivo del ajuste: ");
        String motivo = sc.nextLine();

        try {

            inventarioService.realizarAjuste(id, cantidad, motivo);

            logger.info("Ajuste registrado correctamente para el producto ID: " + id + salto);

        } catch (Exception e) {

            logger.info("Error al realizar el ajuste: " + e.getMessage() + salto);

        }

    }

    private void generarReporteStock() {

        logger.info("Reporte Stock Bajo" + salto);

        var lista = inventarioService.obtenerReporteBajoStock();

        if(lista.isEmpty()){

            logger.info("No hay productos con stock bajo." + salto);

        } else {

            logger.info("ATENCION: Los siguientes productos están por agotarse:" + salto);

            // Formato tabla simple
            System.out.printf("%-5s %-20s %-10s %-10s%n", "ID", "NOMBRE", "ACTUAL", "MINIMO");
            System.out.println("-------------------------------------------------------");

            for (Producto p : lista) {
                System.out.printf("%-5d %-20s %-10d %-10d%n",
                        p.getId(),
                        p.getNombre(),
                        p.getStockActual(),
                        p.getStockMinimo()
                );
            }

            logger.info(salto);

        }
    }

    public Integer leerId(String operacion){

        logger.info("Ingrese el ID del producto " + operacion + " : " + salto);
        return Integer.parseInt(sc.nextLine());

    }


}
