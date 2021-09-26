package com.kk08.demo.dao;

import com.kk08.demo.model.Animal;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository("animalDao")
public class FakeAnimalDataAccessService implements AnimalDao {
    private static List<Animal> DB = new ArrayList<>();

    @Override
    public int insertAnimal(UUID id, Animal animal) {
        DB.add(new Animal(id, animal.getName(), animal.getSpecies()));
        return 1;
    }

    @Override
    public List<Animal> selectAllAnimals() {
        return DB;
    }

    @Override
    public Optional<Animal> selectAnimalById(UUID id) {
        return DB.stream().filter(animal -> animal.getId().equals(id)).findFirst();
    }

    @Override
    public int deleteAnimalById(UUID id) {
        Optional<Animal> animalMaybe = selectAnimalById(id);

        if (!animalMaybe.isPresent()) {
            return 0;
        }

        DB.remove(animalMaybe.get());

        return 1;
    }

    @Override
    public int updateAnimalById(UUID id, Animal animalToUpdate) {
        return selectAnimalById(id).map(animal -> {
            int indexOfAnimalToUpdate = DB.indexOf(animal);
            if (indexOfAnimalToUpdate >= 0) {
                DB.set(indexOfAnimalToUpdate, new Animal(id, animalToUpdate.getName(), animalToUpdate.getSpecies()));
                return 1;
            }

            return 0;
        }).orElse(0);
    }
}
