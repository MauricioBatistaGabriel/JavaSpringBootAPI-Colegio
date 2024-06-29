package org.example.domain.rest.controller;

import org.example.domain.entity.Turma;
import org.example.domain.rest.dto.TurmaDTO;
import org.example.domain.rest.dto.InformacoesTurmaDTO;
import org.example.domain.service.TurmaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.util.List;
import static org.springframework.http.HttpStatus.*;

@RestController
@RequestMapping("/api/turma")
public class TurmaController {

    @Autowired
    private TurmaService turmaService;

    @PostMapping
    @ResponseStatus(CREATED)
    public Integer save(@RequestBody @Valid TurmaDTO turmaDTO){
        return turmaService.save(turmaDTO);
    }

    @GetMapping("{id}")
    public InformacoesTurmaDTO findById(@PathVariable Integer id){
        return turmaService.findById(id);
    }

    @GetMapping
    public List<Turma> filterAll(@RequestBody Turma turma){
        return turmaService.filterAll(turma);
    }

    @PutMapping("{id}")
    @ResponseStatus(OK)
    public Turma update(@PathVariable Integer id, @RequestBody @Valid Turma turma){
        return turmaService.update(id, turma);
    }

    @DeleteMapping("{id}")
    @ResponseStatus(NO_CONTENT)
    public void deleteById(@PathVariable Integer id){
        turmaService.deleteById(id);
    }
}
