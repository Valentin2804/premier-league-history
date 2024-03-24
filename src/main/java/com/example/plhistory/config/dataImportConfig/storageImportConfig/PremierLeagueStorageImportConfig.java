package com.example.plhistory.config.dataImportConfig.storageImportConfig;

import com.example.plhistory.entities.PremierLeague;
import com.example.plhistory.services.impl.PremierLeagueServiceImpl;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;

@Configuration
//@Order(4)
public class PremierLeagueStorageImportConfig {

    private final GoogleConnect googleConnect = new GoogleConnect();
    private final CloudManagement cloudManagement = new CloudManagement();
    private final PremierLeagueServiceImpl service;


    public PremierLeagueStorageImportConfig(PremierLeagueServiceImpl service) {
        this.service = service;
    }

    //@Bean
    public CommandLineRunner fillPremierLeagues(){
        return args -> {
            for (PremierLeague premierLeague : service.getPremierLeagues()){
                byte[] winnersPhoto = googleConnect
                        .searchImage(premierLeague.getWinner().getName() + " premier league winners " + premierLeague.getSeason());
                cloudManagement.upload(premierLeague.getWinner().getName() + "_" + premierLeague.getSeason().replace("/", "-"),
                        winnersPhoto, "premierleaguewinners");

                byte[] runnersUpPhoto = googleConnect
                        .searchImage(premierLeague.getRunnerUp().getName() + " runner up premier league " + premierLeague.getSeason());
                cloudManagement.upload(premierLeague.getRunnerUp().getName() + "_" + premierLeague.getSeason().replace("/", "-"),
                        runnersUpPhoto, "premierleaguerunnersup");
            }
        };
    }
}
