package com.kk08.demo.api;

import com.kk08.demo.model.Animal;
import com.kk08.demo.service.AnimalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RequestMapping("api/v1/animal")
@RestController
public class AnimalController {

    private final AnimalService animalService;

    @Autowired
    public AnimalController(AnimalService animalService) {
        this.animalService = animalService;
    }

    @PostMapping
    public void addAnimal(@RequestBody Animal animal) {
        animalService.addAnimal(animal);
    }

    @GetMapping
    public List<Animal> getAllAnimals() {
        return animalService.getAllAnimals();
    }

    @GetMapping(path = "{id}")
    public Animal getAnimalbyId(@PathVariable("id") UUID id) {
        return animalService.getAnimalById(id).orElse(null);
    }

    @DeleteMapping(path = "{id}")
    public void deleteAnimalById(@PathVariable("id")UUID id) {
        animalService.deleteAnimal(id);
    }

    @PutMapping(path = "{id}")
    public void updateAnimal(@PathVariable("id")UUID id, @RequestBody Animal animalToUpdate) {
        animalService.updateAnimal(id, animalToUpdate);
    }
}
