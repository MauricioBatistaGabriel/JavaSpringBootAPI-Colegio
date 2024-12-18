package org.example.domain.rest.controller;

import org.example.domain.entity.Sala;
import org.example.domain.enums.Periodo;
import org.example.domain.rest.dto.CompleteSalaDTO;
import org.example.domain.service.SalaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

import static org.springframework.http.HttpStatus.*;

@RestController
@RequestMapping("/api/sala")
public class SalaController {

    @Autowired
    private SalaService salaService;

    @PostMapping
    @ResponseStatus(CREATED)
    public Integer save(@RequestBody @Valid CompleteSalaDTO salaDTO){
        return salaService.save(salaDTO);
    }

    @GetMapping("{id}")
    public CompleteSalaDTO findById(@PathVariable Integer id){
        return salaService.findByIdReturnDTO(id);
    }

    @GetMapping("byPeriodo")
    public List<CompleteSalaDTO> findByPeriodo(@RequestBody Periodo periodo){
        return salaService.findByPeriodo(periodo);
    }

    @GetMapping
    public List<CompleteSalaDTO> filterAll(@RequestBody CompleteSalaDTO salaDTO){
        return salaService.filterAll(salaDTO);
    }

    @PutMapping("{id}")
    @ResponseStatus(OK)
    public Sala update(@PathVariable Integer id, @RequestBody @Valid Sala sala){
        return salaService.update(id, sala);
    }

    @DeleteMapping("{id}")
    @ResponseStatus(NO_CONTENT)
    public void deleteById(@PathVariable Integer id){
        salaService.deleteById(id);
    }
}
