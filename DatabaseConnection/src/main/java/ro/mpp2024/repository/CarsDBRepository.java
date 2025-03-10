package ro.mpp2024.repository;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.checkerframework.checker.units.qual.C;
import ro.mpp2024.domain.Car;
import ro.mpp2024.utils.JdbcUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class CarsDBRepository implements CarRepository{

    private JdbcUtils dbUtils;



    private static final Logger logger= LogManager.getLogger();

    public CarsDBRepository(Properties props) {
        logger.info("Initializing CarsDBRepository with properties: {} ",props);
        dbUtils=new JdbcUtils(props);
    }

    @Override
    public List<Car> findByManufacturer(String manufacturerN) {
 	    logger.traceEntry("find car by Manufacturer {}", manufacturerN);
         Connection conn = dbUtils.getConnection();
         List<Car> cars = new ArrayList<>();
         try(PreparedStatement preparedStatement = conn.prepareStatement("select * from Masini where manufacturer = ?")){
             preparedStatement.setString(1, manufacturerN);
             try(ResultSet resultSet = preparedStatement.executeQuery()){
                 while (resultSet.next()){
                     int id = resultSet.getInt("id");
                     String model = resultSet.getString("model");
                     String manufacturer = resultSet.getString("manufacturer");
                     Integer year = resultSet.getInt("year");
                     Car car = new Car(manufacturer, model, year);
                     car.setId(id);
                     cars.add(car);
                 }
             }
         }catch(SQLException e){
             logger.error(e);
             System.out.println("Error db: "+e.getMessage());
         }
         logger.traceExit("find cars by Manufacturer");
         return cars;
    }

    @Override
    public List<Car> findBetweenYears(int min, int max) {
	//to do
        return null;
    }

    @Override
    public void add(Car elem) {
        logger.traceEntry("saving car{}",elem);
        Connection conn=dbUtils.getConnection();
        try(PreparedStatement preparedStatement = conn.prepareStatement("insert into Masini(manufacturer, model, year) values (?, ?, ?)")){
            preparedStatement.setString(1,elem.getManufacturer());
            preparedStatement.setString(2, elem.getModel());
            preparedStatement.setInt(3, elem.getYear());
            int result = preparedStatement.executeUpdate();
            logger.trace("Saved {} instances",result);
        }catch(SQLException e){
            logger.error(e);
            System.out.println("Error db: " + e);
        }
        logger.traceExit();
    }

    @Override
    public void update(Integer integer, Car elem) {
      //to do
    }

    @Override
    public Iterable<Car> findAll() {
    logger.traceEntry();
    Connection conn=dbUtils.getConnection();
    List<Car> cars = new ArrayList<>();
    try(PreparedStatement preparedStatement = conn.prepareStatement("select * from Masini")){
        try(ResultSet resultSet = preparedStatement.executeQuery())
        {
            while(resultSet.next()){
                int id = resultSet.getInt("id");
                String manufacturer = resultSet.getString("manufacturer");
                String model = resultSet.getString("model");
                Integer year = resultSet.getInt("year");
                Car car = new Car(manufacturer, model, year);
                car.setId(id);
                cars.add(car);
            }
        }
    }catch(SQLException e){
        logger.error(e);
        System.out.println("Error db: " + e);
    }
    logger.traceExit();
    return cars;

    }
}
