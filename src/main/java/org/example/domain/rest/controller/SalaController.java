package org.example.domain.rest.controller;

import org.example.domain.entity.Sala;
import org.example.domain.rest.dto.SalaDTO;
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
    public Integer save(@RequestBody @Valid SalaDTO salaDTO){
        return salaService.save(salaDTO);
    }

    @GetMapping("{id}")
    public SalaDTO findById(@PathVariable Integer id){
        return salaService.findById(id);
    }

    @GetMapping
    public List<Sala> filterAll(@RequestBody Sala sala){
        return salaService.filterAll(sala);
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
