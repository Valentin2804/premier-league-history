package com.example.plhistory.config.dataImportConfig.databaseImportConfig;

import com.example.plhistory.config.dataImportConfig.storageImportConfig.CloudManagement;
import com.example.plhistory.config.dataImportConfig.storageImportConfig.GoogleConnect;
import com.example.plhistory.entities.Managers;
import com.example.plhistory.repositories.ManagersRepository;
import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvException;
import jakarta.transaction.Transactional;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;

import java.io.*;
import java.time.LocalDate;
import java.util.List;

@Configuration
//@Order(1)
public class ManagersImportConfig {

    @Transactional
    //@Bean
    CommandLineRunner fillManagers(ManagersRepository repository){

        return args -> {
            try (CSVReader reader = new CSVReader(new FileReader("src/main/resources/data/managerNew.csv"))) {
                List<String[]> lines = reader.readAll();

                int headerLine = 0;
                int indexOfManagerName = 1;
                int indexOfManagerBirthday = 5;
                int indexOfManagerNation = 2;


                lines.remove(headerLine);

                for (String[] columns : lines) {
                    String name = columns[indexOfManagerName].trim();
                    LocalDate birthday = LocalDate.parse(columns[indexOfManagerBirthday].trim());
                    String nation = columns[indexOfManagerNation].trim();

                    Managers manager = new Managers();
                    manager.setName(name);
                    manager.setBirthday(birthday);
                    manager.setNation(nation);

                    repository.save(manager);
                }
            } catch (IOException | CsvException e) {
                throw new RuntimeException(e);
            }
        };
    }
}
