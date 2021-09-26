package com.kk08.demo.service;

import com.kk08.demo.dao.AnimalDao;
import com.kk08.demo.model.Animal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class AnimalService {

    private final AnimalDao animalDao;

    @Autowired
    public AnimalService(@Qualifier("animalDao") AnimalDao animalDao) {
        this.animalDao = animalDao;
    }

    public int addAnimal(Animal animal) {
        return animalDao.insertAnimal(animal);
    }

    public List<Animal> getAllAnimals() {
        return animalDao.selectAllAnimals();
    }

    public Optional<Animal> getAnimalById(UUID id) {
        return animalDao.selectAnimalById(id);
    }

    public int deleteAnimal(UUID id) {
        return animalDao.deleteAnimalById(id);
    }

    public int updateAnimal(UUID id, Animal newAnimal) {
        return animalDao.updateAnimalById(id, newAnimal);
    }

}
