package web.entity;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Calendar;

@Entity
public class Voto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "funcionarioId")
    private Funcionario funcionario;

    @Column
    private LocalDate data;

    public int getId() {
        return id;
    }

    public Funcionario getFuncionario() {
        return funcionario;
    }

    public void setFuncionario(Funcionario funcionario) {
        this.funcionario = funcionario;
    }


    public LocalDate getData() {
        return data;
    }

    public void setData(LocalDate data) {
        this.data = data;
    }
}
