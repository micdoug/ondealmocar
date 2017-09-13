package web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import web.dao.DAO;
import web.dao.VotacaoDAO;
import web.entity.Funcionario;
import web.entity.Restaurante;
import web.entity.Votacao;
import web.entity.Voto;
import web.helpers.VotoModel;

import java.time.LocalDate;
import java.time.temporal.TemporalField;
import java.util.*;

@RestController
@RequestMapping("/api/votacao")
public class VotacaoController {

    @Autowired
    private VotacaoDAO votacaoDAO;

    @Autowired
    private DAO<Restaurante> restauranteDAO;

    @Autowired
    private DAO<Funcionario> funcionarioDAO;

    @Autowired
    private DAO<Voto> votoDAO;

    @RequestMapping(method = RequestMethod.GET)
    public Collection<Votacao> get() {
        // Pega o dia atual
        LocalDate date = LocalDate.now();
        // Dia da semana varia de 1 (sunday) a 7 (saturday)
        int dayofweek = date.getDayOfWeek().getValue();

        int daysback = (dayofweek-1) * (-1);
        List<Integer> blacklist = new ArrayList<>();
        List<Votacao> votacoes = new ArrayList<>();
        for (int i = 0; i < 7; i++) {
            LocalDate day = LocalDate.now();
            day = day.plusDays(daysback);
            daysback++;
            Votacao votacao = this.votacaoDAO.getTopOfDay(day, blacklist);
            if (votacao != null) {
                blacklist.add(votacao.getRestaurante().getId());
                votacoes.add(votacao);
            }

        }

        return votacoes;
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity votar(@RequestBody VotoModel model) {
        // Verifica se o restaurante e o funcionário existem
        Restaurante restaurante = this.restauranteDAO.getById(model.getRestauranteId());
        if (restaurante == null) {
            return new ResponseEntity("Restaurante não encontrado", HttpStatus.BAD_REQUEST);
        }
        Funcionario funcionario = this.funcionarioDAO.getById(model.getFuncionarioId());
        if (funcionario == null) {
            return new ResponseEntity("Funcionário não encontrado", HttpStatus.BAD_REQUEST);
        }
        else if(!funcionario.isEnabled()) {
            return new ResponseEntity("Funcionário está inativo, não pode votar.", HttpStatus.BAD_REQUEST);
        }

        // Verifica se o funcionário já votou naquele dia
        HashMap<String, Object> filtros = new HashMap<>();
        filtros.put("funcionario.id", funcionario.getId());
        LocalDate today = LocalDate.now();
        filtros.put("data", today);
        Collection<Voto> votos = this.votoDAO.getFiltered(filtros);
        if (votos.size() > 0) {
            return new ResponseEntity("Este funcionário já votou hoje.", HttpStatus.BAD_REQUEST);
        }

        // Se estiver tudo certo efetua a votacao
        this.votacaoDAO.votar(model);

        return ResponseEntity.ok().build();
    }


}
