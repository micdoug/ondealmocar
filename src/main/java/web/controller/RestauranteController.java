package web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import web.dao.DAO;
import web.dao.RestauranteDAO;
import web.entity.Restaurante;

import javax.xml.ws.Response;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/restaurantes")
public class RestauranteController {

    @Autowired
    private DAO<Restaurante> restauranteDAO;

    @RequestMapping(method = RequestMethod.GET)
    public Collection<Restaurante> get() {
        return this.restauranteDAO.getAll();
    }

    @RequestMapping(method = RequestMethod.GET, value = "/{id}")
    public ResponseEntity get(@PathVariable("id") int id) {
        Restaurante restaurante = restauranteDAO.getById(id);

        if (restaurante == null) {
            return ResponseEntity.notFound().build();
        }
        else {
            return ResponseEntity.ok(restaurante);
        }
    }

    @RequestMapping(method = RequestMethod.POST)
    public void post(@RequestBody Restaurante restaurante) {
        this.restauranteDAO.create(restaurante);
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "/{id}")
    public void delete(@PathVariable("id") int id) {
        this.restauranteDAO.delete(id);
    }

    @RequestMapping(method = RequestMethod.PUT)
    public void put(@RequestBody Restaurante restaurante) {
        this.restauranteDAO.update(restaurante);
    }

    @RequestMapping(value = "/filtered", method = RequestMethod.POST)
    public Collection<Restaurante> getFiltered(@RequestBody Restaurante restaurante) {
        Map<String, Object> filters = new HashMap<>();
        if (restaurante.getNome() != null) {
            filters.put("nome", restaurante.getNome());
        }
        if (restaurante.getEndereco() != null) {
            filters.put("endereco", restaurante.getEndereco());
        }
        return this.restauranteDAO.getFiltered(filters);
    }

}
