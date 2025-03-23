package demo1.demo1.controller;


import demo1.demo1.dto.TemaDTO;
import demo1.demo1.model.Tema;
import demo1.demo1.service.TemaService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/temas")
public class TemaController {
    private final TemaService temaService;

    public TemaController(TemaService temaService) {
        this.temaService = temaService;
    }

    @GetMapping("/categoria/{categoriaId}")
    public List<Tema> listarPorCategoria(@PathVariable Long categoriaId) {
        return temaService.listarPorCategoria(categoriaId);
    }

    @PostMapping("/crear")
    public Tema crearTema(@RequestBody TemaDTO temaDTO) {
        return temaService.crearTema(temaDTO);
    }
}
