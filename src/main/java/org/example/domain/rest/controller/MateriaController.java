package org.example.domain.rest.controller;

import org.example.domain.entity.Materia;
import org.example.domain.rest.dto.CompleteMateriaDTO;
import org.example.domain.service.MateriaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.util.List;
import static org.springframework.http.HttpStatus.*;

@RestController
@RequestMapping("/api/materia")
public class MateriaController {

    @Autowired
    private MateriaService materiaService;

    @PostMapping
    @ResponseStatus(CREATED)
    public Integer save(@RequestBody @Valid CompleteMateriaDTO materiaDTO){
        return materiaService.save(materiaDTO);
    }

    @GetMapping("{id}")
    public CompleteMateriaDTO findById(@PathVariable Integer id){
        return materiaService.findByID(id);
    }

    @GetMapping
    public List<Materia> filterAll(@RequestBody Materia materia){
        return materiaService.filterAll(materia);
    }

    @PutMapping("{id}")
    @ResponseStatus(OK)
    public Materia update(@PathVariable Integer id, @RequestBody @Valid Materia materia){
        return materiaService.update(id, materia);
    }

    @DeleteMapping("{id}")
    @ResponseStatus(NO_CONTENT)
    public void deleteById(@PathVariable Integer id){
        materiaService.deleteByID(id);
    }
}
