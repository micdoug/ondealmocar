package web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import web.dao.DAO;
import web.entity.Funcionario;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/funcionarios")
public class FuncionarioController {

    @Autowired
    private DAO<Funcionario> funcionarioDAO;

    @RequestMapping(method = RequestMethod.GET)
    public Collection<Funcionario> getAll() {
        return funcionarioDAO.getAll();
    }

    @RequestMapping(method = RequestMethod.GET, value = "/{id}")
    public ResponseEntity get(@PathVariable("id") int id) {
        Funcionario funcionario = this.funcionarioDAO.getById(id);
        if (funcionario == null) {
            return ResponseEntity.notFound().build();
        }
        else {
            return ResponseEntity.ok(funcionario);
        }
    }

    @RequestMapping(method = RequestMethod.POST)
    public void post(@RequestBody Funcionario funcionario) {
        this.funcionarioDAO.create(funcionario);
    }

    @RequestMapping(method = RequestMethod.PUT)
    public void put(@RequestBody Funcionario funcionario) {
        this.funcionarioDAO.update(funcionario);
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "/{id}")
    public void delete(@PathVariable("id") int id) {
        this.funcionarioDAO.delete(id);
    }

    @RequestMapping(value = "/filtered", method = RequestMethod.POST)
    public Collection<Funcionario> getFiltered(@RequestBody Funcionario funcionario) {
        Map<String, Object> filters = new HashMap<String, Object>();
        if (funcionario.getNome() != null) {
            filters.put("nome", funcionario.getNome());
        }

        return this.funcionarioDAO.getFiltered(filters);
    }

}
