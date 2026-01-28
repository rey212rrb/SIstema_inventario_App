package implementacion.Inventario.modelo;

import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;

import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "ajusteinventario")

public class AjusteInventario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idAjusteInventario")
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "idproducto",  nullable = false)
    @ToString.Exclude
    private Producto producto;

    @Column(name = "cantidadAjustada", nullable = false)
    private Integer cantidadAjustada;

    @Column(name = "motivo")
    private String motivo;

    @Column(name = "fecha")
    private LocalDateTime fecha;

    @Column(name = "stockAlMomento")
    private Integer stockAlMomento;

    @PrePersist
    public void prePersist() {

        this.fecha = LocalDateTime.now();
    }


}
