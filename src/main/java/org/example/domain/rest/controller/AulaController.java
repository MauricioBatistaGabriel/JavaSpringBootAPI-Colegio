package org.example.domain.rest.controller;

import org.example.domain.entity.Aula;
import org.example.domain.rest.dto.AulaDTO;
import org.example.domain.rest.dto.InformacoesAulaDTO;
import org.example.domain.service.AulaService;
import org.springframework.beans.factory.annotation.Autowired;
import static org.springframework.http.HttpStatus.*;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/aula")
public class AulaController {

    @Autowired
    private AulaService aulaService;

    @PostMapping
    @ResponseStatus(CREATED)
    public Integer save(@RequestBody @Valid AulaDTO aulaDTO){
        return aulaService.save(aulaDTO);
    }

    @GetMapping("{id}")
    public InformacoesAulaDTO findById(@PathVariable Integer id){
        return aulaService.findById(id);
    }

    @GetMapping
    public List<Aula> filterAll(@RequestBody Aula aula){
        return aulaService.filterAll(aula);
    }

    @PutMapping("{id}")
    @ResponseStatus(OK)
    public Aula update(@PathVariable Integer id, @RequestBody @Valid Aula aula){
        return aulaService.update(id, aula);
    }

    @DeleteMapping("{id}")
    @ResponseStatus(NO_CONTENT)
    public void deleteById(@PathVariable Integer id){
        aulaService.deleteById(id);
    }
}
