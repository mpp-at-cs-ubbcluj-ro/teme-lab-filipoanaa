package ro.mpp2024;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ro.mpp2024.repository.ComputerRepairRequestRepository;
import ro.mpp2024.repository.ComputerRepairedFormRepository;
import ro.mpp2024.repository.file.ComputerRepairRequestFileRepository;
import ro.mpp2024.repository.file.ComputerRepairedFormFileRepository;
import ro.mpp2024.repository.jdbc.ComputerRepairRequestJdbcRepository;
import ro.mpp2024.repository.jdbc.ComputerRepairedFormJdbcRepository;
import ro.mpp2024.services.ComputerRepairServices;


import java.io.*;
import java.util.Properties;

@Configuration
public class RepairShopConfig {
    @Bean
    Properties getProps() {
        Properties prop = new Properties();
        try{
            System.out.println("searching bd config in dir:" + ((new File(".")).getAbsolutePath()));
            prop.load(new FileReader("bd.config"));
        }catch(IOException e){
            System.err.println(e);
        }
        return prop;

    }

    @Bean
    ComputerRepairRequestRepository requestsRepo(){
       // return new ComputerRepairRequestFileRepository("ComputerRequests.txt");
        return new ComputerRepairRequestJdbcRepository(getProps());

    }

    @Bean
    ComputerRepairedFormRepository formsRepo(){
        return new ComputerRepairedFormJdbcRepository(getProps());
      // return new ComputerRepairedFormFileRepository("RepairedForms.txt", requestsRepo());

    }

    @Bean
    ComputerRepairServices services(){
        return new ComputerRepairServices(requestsRepo(), formsRepo());
    }

}
