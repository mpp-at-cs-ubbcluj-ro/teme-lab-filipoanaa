package ro.mpp2024;

import ro.mpp2024.domain.Car;
import ro.mpp2024.repository.CarRepository;
import ro.mpp2024.repository.CarsDBRepository;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

public class Main {
    public static void main(String[] args) {

        Properties prop = new Properties();
        try (FileInputStream fis = new FileInputStream("dbConfig.config")) {
            prop.load(fis);
            prop.list(System.out);
        } catch (FileNotFoundException e) {
            throw new RuntimeException("File not found: " + e.getMessage(), e);
        } catch (IOException e) {
            System.out.println("IOException: " + e.getMessage());
        }

       CarRepository carRepo=new CarsDBRepository(prop);
//        carRepo.add(new Car("Tesla","Model S", 2019));
        System.out.println("Toate masinile din db");
        for(Car car:carRepo.findAll())
            System.out.println(car);
        String manufacturer="Tesla";
        System.out.println("Masinile produse de "+manufacturer);
        for(Car car:carRepo.findByManufacturer(manufacturer))
            System.out.println(car);

    }
}
