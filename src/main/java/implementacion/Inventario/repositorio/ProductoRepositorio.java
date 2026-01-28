package implementacion.Inventario.repositorio;

import implementacion.Inventario.modelo.Producto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ProductoRepositorio extends JpaRepository<Producto, Integer> {

    @Query("SELECT p FROM Producto p WHERE p.stockActual <= p.stockMinimo")
    List<Producto> buscarProductosConBajoStock();

}
