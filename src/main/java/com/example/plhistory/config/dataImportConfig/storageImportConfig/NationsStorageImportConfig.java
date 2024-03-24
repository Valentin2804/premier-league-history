package com.example.plhistory.config.dataImportConfig.storageImportConfig;

import com.example.plhistory.services.impl.ManagersServiceImpl;
import jakarta.transaction.Transactional;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.*;


@Configuration
//@Order(1)
public class NationsStorageImportConfig {

    private final CloudManagement cloudManagement = new CloudManagement();

    private final GoogleConnect googleConnect = new GoogleConnect();

    private final ManagersServiceImpl service;

    public NationsStorageImportConfig(ManagersServiceImpl service) {
        this.service = service;
    }


    @Transactional
    //@Bean
    CommandLineRunner fillNations() {

        return args -> {

            List<String> nations = service.getAllDifferentNations();

            for (String nation : nations){
                byte[] imageBytes = googleConnect.searchImage(nation + " flag");
                cloudManagement.upload(nation + "_flag", imageBytes, "nations");
            }
        };
    }

}
