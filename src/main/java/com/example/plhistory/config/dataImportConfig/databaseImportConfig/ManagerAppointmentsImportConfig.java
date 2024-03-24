package com.example.plhistory.config.dataImportConfig.databaseImportConfig;

import com.example.plhistory.config.dataImportConfig.storageImportConfig.CloudManagement;
import com.example.plhistory.config.dataImportConfig.storageImportConfig.GoogleConnect;
import com.example.plhistory.entities.ManagerAppointments;
import com.example.plhistory.entities.Managers;
import com.example.plhistory.entities.Teams;
import com.example.plhistory.repositories.ManagerAppointmentsRepository;
import com.example.plhistory.services.impl.ManagersServiceImpl;
import com.example.plhistory.services.impl.TeamsServiceImpl;
import jakarta.transaction.Transactional;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;

import java.io.FileReader;
import java.text.DateFormatSymbols;
import java.text.ParseException;
import java.time.LocalDate;
import java.util.Locale;

@Configuration
//@Order(3)
public class ManagerAppointmentsImportConfig {

    private final ManagersServiceImpl managersService;

    private final TeamsServiceImpl teamsService;

    public ManagerAppointmentsImportConfig(ManagersServiceImpl managersService, TeamsServiceImpl teamsService) {
        this.managersService = managersService;
        this.teamsService = teamsService;
    }

    @Transactional
    //@Bean
    CommandLineRunner fillManagerAppointments(ManagerAppointmentsRepository repository){
        return args -> {
            String manAppoints = "src/main/resources/data/managersStats.csv";

            int indexOfManagerName = 0;
            int indexOfTeamName = 1;
            int indexOfAppointmentDate = 2;
            int indexOfDepartureDate = 3;


            try (CSVParser csvParser = CSVFormat.DEFAULT.parse(new FileReader(manAppoints))) {

                for (CSVRecord record : csvParser) {
                    if(!record.get(indexOfManagerName).equals("Name")){
                        String name = record.get(indexOfManagerName);
                        String club = record.get(indexOfTeamName);
                        String appointment = record.get(indexOfAppointmentDate);
                        String departure = record.get(indexOfDepartureDate);

                        Managers manager = managersService.getManager(name);
                        Teams team = teamsService.getTeamByName(club);
                        LocalDate appointmentDate = parseDate(appointment);
                        LocalDate departureDate = null;
                        if(!departure.equals("Present*")){
                            departureDate = parseDate(departure);
                        }

                        ManagerAppointments managerAppointment = new ManagerAppointments();

                        managerAppointment.setManager(manager);
                        managerAppointment.setTeam(team);
                        managerAppointment.setHireDate(appointmentDate);
                        managerAppointment.setDepartureDate(departureDate);

                        repository.save(managerAppointment);
                    }
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        };
    }

    private LocalDate parseDate(String date) throws ParseException {
        String[] dateParts = date.split("\\s+");

        int expectedPartsOfGivenDate = 3;

        int indexOfDay = 0;
        int indexOfYear = 2;
        int indexOfMonth = 1;

        int errorOffset = 0;

        int partsOfGivenDateForPresentString = 1;

        if (dateParts.length == expectedPartsOfGivenDate) {
            int day = Integer.parseInt(dateParts[indexOfDay]);
            int year = Integer.parseInt(dateParts[indexOfYear]);

            String monthName = dateParts[indexOfMonth];

            int monthIndex = getMonthIndex(monthName);

            return LocalDate.of(year, monthIndex, day);
        } else if (dateParts.length == partsOfGivenDateForPresentString) {
            return null;
        } else {
            throw new ParseException("Invalid date format", errorOffset);
        }
    }

    private int getMonthIndex(String monthName) {
        DateFormatSymbols symbols = new DateFormatSymbols(Locale.ENGLISH);
        String[] monthNames = symbols.getMonths();

        int indexOfFirstMonthInYear = 0;

        int differenceToRealMonthIndex = 1;

        for (int i = indexOfFirstMonthInYear; i < monthNames.length; i++) {
            if (monthNames[i].equalsIgnoreCase(monthName)) {
                return i + differenceToRealMonthIndex;
            }
        }

        throw new IllegalArgumentException("Invalid month name: " + monthName);
    }
}
