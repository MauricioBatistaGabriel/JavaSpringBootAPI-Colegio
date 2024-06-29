package org.example.domain.rest.controller;

import org.example.domain.entity.Aula;
import org.example.domain.entity.Professor;
import org.example.domain.rest.dto.AulaByIdProfessorDTO;
import org.example.domain.rest.dto.InformacoesAulaDTO;
import org.example.domain.rest.dto.ProfessorDTO;
import org.example.domain.rest.dto.InformacoesProfessorDTO;
import org.example.domain.service.ProfessorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.util.List;
import static org.springframework.http.HttpStatus.*;

@RestController
@RequestMapping("/api/professor")
public class ProfessorController {

    @Autowired
    private ProfessorService professorService;

    @PostMapping
    @ResponseStatus(CREATED)
    public Integer save(@RequestBody @Valid ProfessorDTO professorDTO){
        return professorService.save(professorDTO);
    }

    @GetMapping("{id}")
    public InformacoesProfessorDTO findById(@PathVariable Integer id){
        return professorService.findById(id);
    }

    @GetMapping("/aulas/{id}")
    public List<AulaByIdProfessorDTO> findAulaByIdProfessor(@PathVariable Integer id){
        return professorService.findAulaByIdProfessor(id);
    }

    @GetMapping
    public List<Professor> filterAll(@RequestBody Professor professor){
        return professorService.filterAll(professor);
    }

    @PutMapping("{id}")
    @ResponseStatus(OK)
    public Professor update(@PathVariable Integer id, @RequestBody @Valid Professor professor){
        return professorService.update(id, professor);
    }

    @DeleteMapping("{id}")
    @ResponseStatus(NO_CONTENT)
    public void deleteById(@PathVariable Integer id){
        professorService.deleteById(id);
    }
}
