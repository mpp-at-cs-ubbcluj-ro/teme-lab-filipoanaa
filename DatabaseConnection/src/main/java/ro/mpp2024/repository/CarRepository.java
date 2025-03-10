package ro.mpp2024.repository;

import ro.mpp2024.domain.Car;
import ro.mpp2024.repository.Repository;

import java.util.List;


public interface CarRepository extends Repository<Integer,Car> {
    List<Car> findByManufacturer(String manufacturer);
    List<Car> findBetweenYears(int min, int max);
}
