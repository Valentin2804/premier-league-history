package com.example.plhistory.config.dataImportConfig.storageImportConfig;

import com.example.plhistory.entities.ManagerAppointments;
import com.example.plhistory.services.impl.ManagerAppointmentsServiceImpl;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;

@Configuration
//@Order(4)
public class ManagerAppointmentsStorageConfig {
    private final GoogleConnect googleConnect = new GoogleConnect();
    private final CloudManagement cloudManagement = new CloudManagement();

    private final ManagerAppointmentsServiceImpl service;

    public ManagerAppointmentsStorageConfig(ManagerAppointmentsServiceImpl service) {
        this.service = service;
    }

    //@Bean
    public CommandLineRunner fillManagerAppointmentsStorage(){
        return args -> {
            for(ManagerAppointments managerAppointment : service.getManagerAppointments()){
                byte[] appointmentPhoto = googleConnect
                        .searchImage(managerAppointment.getManager().getName() + " " + managerAppointment.getTeam().getName() + " appointment " + managerAppointment.getHireDate().toString());
                cloudManagement.upload(
                        managerAppointment.getManager().getName()+ "_" + managerAppointment.getTeam().getName() + "_appointment_" + managerAppointment.getHireDate().toString(),
                        appointmentPhoto,
                        "managerappointments"
                );

                if(managerAppointment.getDepartureDate() != null){
                    byte[] departurePhoto = googleConnect
                            .searchImage(managerAppointment.getManager().getName() + " " + managerAppointment.getTeam().getName() + " departure " + managerAppointment.getDepartureDate().toString());
                    cloudManagement.upload(
                            managerAppointment.getTeam().getName() + "_" + managerAppointment.getTeam().getName() + "_departure_" + managerAppointment.getDepartureDate().toString(),
                            departurePhoto,
                            "managerdepartures"
                    );
                }
            }
        };
    }
}
