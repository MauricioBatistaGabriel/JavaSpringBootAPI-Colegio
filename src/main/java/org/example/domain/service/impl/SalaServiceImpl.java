package org.example.domain.service.impl;

import org.example.domain.entity.Sala;
import org.example.domain.repository.SalaRepository;
import org.example.domain.rest.dto.CompleteSalaDTO;
import org.example.domain.service.SalaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;
import javax.persistence.EntityNotFoundException;
import java.util.List;

@Service
public class SalaServiceImpl implements SalaService {

    @Autowired
    private SalaRepository salaRepository;

    @Override
    public Integer save(CompleteSalaDTO salaDTO) {
        Sala sala = new Sala(salaDTO.getSala());
        return salaRepository.save(sala).getId();
    }

    @Override
    public CompleteSalaDTO findById(Integer id) {
        return salaRepository.findById(id)
                .map( sala -> {
                    CompleteSalaDTO salaDTO = new CompleteSalaDTO(sala.getSala());
                    return salaDTO;
                }).orElseThrow( () ->
                        new EntityNotFoundException("Sala com o ID:" + id + " não encontrada"));
    }

    @Override
    public List<Sala> filterAll(Sala sala) {
        ExampleMatcher matcher = ExampleMatcher
                .matching()
                .withIgnoreCase()
                .withStringMatcher(
                        ExampleMatcher.StringMatcher.CONTAINING
                );
        Example example = Example.of(sala, matcher);
        return salaRepository.findAll(example);
    }

    @Override
    public Sala update(Integer id, Sala sala) {
        return salaRepository.findById(id)
                .map( sala1 -> {
                    sala.setId(sala1.getId());
                    return salaRepository.save(sala);
                }).orElseThrow( () ->
                        new EntityNotFoundException("Sala com o ID:" + id + " não encontrada"));
    }

    @Override
    public void deleteById(Integer id) {
        salaRepository.findById(id)
                .map( sala -> {
                    salaRepository.deleteById(id);
                    return Void.TYPE;
                }).orElseThrow( () ->
                        new EntityNotFoundException("Sala com o ID:" + id + " não encontrada"));
    }
}
