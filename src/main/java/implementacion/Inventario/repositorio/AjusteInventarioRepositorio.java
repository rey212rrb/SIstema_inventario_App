package implementacion.Inventario.repositorio;

import implementacion.Inventario.modelo.AjusteInventario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AjusteInventarioRepositorio extends JpaRepository<AjusteInventario, Integer> {

    List<AjusteInventario> findByProductoId(Integer idProducto);

}
