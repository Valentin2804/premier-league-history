package com.example.plhistory.config.dataImportConfig.storageImportConfig;

import com.example.plhistory.entities.Teams;
import com.example.plhistory.services.impl.TeamsServiceImpl;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
//@Order(3)
public class TeamsStorageImportConfig {

    private final GoogleConnect googleConnect = new GoogleConnect();
    private final CloudManagement cloudManagement = new CloudManagement();

    private final TeamsServiceImpl service;

    public TeamsStorageImportConfig(TeamsServiceImpl service) {
        this.service = service;
    }

    //@Bean
    public CommandLineRunner fillTeamsStorage(){
        return args -> {
            List<Teams> teams = service.getTeams();

            for (Teams team : teams){
                byte[] teamLogo = googleConnect.searchImage(team.getName() + " logo");
                cloudManagement.upload(team.getName(), teamLogo, "teams");

                byte[] stadiumPhoto = googleConnect.searchImage(team.getStadium() + " stadium");
                cloudManagement.upload(team.getStadium(), stadiumPhoto, "stadiums");
            }
        };
    }
}
