package web.entity;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Calendar;

@Entity
public class Votacao {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name="restauranteId")
    private Restaurante restaurante;

    @Column
    private int numeroVotos;

    @Column
    private LocalDate data;

    public int getId() {
        return id;
    }

    public Restaurante getRestaurante() {
        return restaurante;
    }

    public void setRestaurante(Restaurante restaurante) {
        this.restaurante = restaurante;
    }

    public int getNumeroVotos() {
        return numeroVotos;
    }

    public void setNumeroVotos(int numeroVotos) {
        this.numeroVotos = numeroVotos;
    }

    public LocalDate getData() {
        return data;
    }

    public void setData(LocalDate data) {
        this.data = data;
    }
}
