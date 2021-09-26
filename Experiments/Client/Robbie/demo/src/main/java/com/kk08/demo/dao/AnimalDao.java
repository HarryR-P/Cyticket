package com.kk08.demo.dao;

import com.kk08.demo.model.Animal;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface AnimalDao {

    int insertAnimal(UUID id, Animal animal);

    default int insertAnimal(Animal animal) {
        UUID id = UUID.randomUUID();
        return insertAnimal(id, animal);
    }

    List<Animal> selectAllAnimals();

    Optional<Animal> selectAnimalById(UUID id);

    int deleteAnimalById(UUID id);

    int updateAnimalById(UUID id, Animal animal);
}
