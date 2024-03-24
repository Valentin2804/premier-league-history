package com.example.plhistory.config.dataImportConfig.storageImportConfig;

import com.example.plhistory.entities.Managers;
import com.example.plhistory.services.impl.ManagersServiceImpl;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
//@Order(2)
public class ManagersStorageImportConfig {

    private final GoogleConnect googleConnect = new GoogleConnect();
    private final CloudManagement cloudManagement = new CloudManagement();

    private final ManagersServiceImpl service;

    public ManagersStorageImportConfig(ManagersServiceImpl service) {
        this.service = service;
    }

    //@Bean
    public CommandLineRunner fillManagersStorage(){
        return args -> {
            List<Managers> managers = service.getManagers();

            for(Managers manager : managers){
                byte[] imageBytes = googleConnect.searchImage(manager.getName() + " football manager");
                cloudManagement.upload(manager.getName(), imageBytes, "managers");
            }
        };
    }
}
