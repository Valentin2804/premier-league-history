package com.example.plhistory.config.dataImportConfig.databaseImportConfig;

import com.example.plhistory.config.dataImportConfig.storageImportConfig.CloudManagement;
import com.example.plhistory.config.dataImportConfig.storageImportConfig.GoogleConnect;
import com.example.plhistory.entities.Teams;
import com.example.plhistory.repositories.TeamsRepository;
import jakarta.transaction.Transactional;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Configuration
//@Order(2)
public class TeamsImportConfig {

    @Transactional
    //@Bean
    CommandLineRunner fillTeams(TeamsRepository teamsRepository){
        return args -> {
            HashMap<String, Integer> clubsCreation = searchYearOfCreation();
            HashMap<String, List<String>> stadiums = new HashMap<>();

            String clubsCsvFilePath = "src/main/resources/data/club.csv";
            String stadiumsCsvFilePath = "src/main/resources/data/stadium.csv";

            int indexOfStadiumIds = 0;
            int indexOfStadiumName = 1;
            int indexOfStadiumCity = 2;
            int indexOfStadiumCapacity = 3;

            int indexOfStadiumIdInClubsCSV = 1;
            int indexOfClubName = 2;
            int indexOfClubWebsite = 3;

            int stadiumNameInListFromHashMap = 0;
            int stadiumCityInListFromHashMap = 1;
            int stadiumCapacityInListFromHashMap = 2;

            try (CSVParser csvParser = CSVFormat.DEFAULT.parse(new FileReader(stadiumsCsvFilePath))) {

                for (CSVRecord record : csvParser) {
                    if(!record.get(indexOfStadiumIds).equals("stadium_id")){
                        List<String> stadiumProperties = new ArrayList<>();
                        stadiumProperties.add(record.get(indexOfStadiumName));
                        stadiumProperties.add(record.get(indexOfStadiumCity));
                        stadiumProperties.add(record.get(indexOfStadiumCapacity));

                        stadiums.put(record.get(indexOfStadiumIds), stadiumProperties);
                    }
                }

            } catch (Exception e) {
                e.printStackTrace();
            }

            try (FileReader fileReader = new FileReader(clubsCsvFilePath);
                 CSVParser csvParser = CSVFormat.DEFAULT.parse(fileReader)) {

                for (CSVRecord record : csvParser) {
                    if (!record.get(indexOfClubName).equals("club_name")){
                        int yearOfCreation;
                        if(record.get(indexOfClubName).equals("Huddersfield Town"))
                        {
                            yearOfCreation = 1908;
                        } else if (record.get(indexOfClubName).equals("Hull City")) {
                            yearOfCreation = 1904;
                        }else {
                            yearOfCreation = clubsCreation.get(record.get(indexOfClubName));
                        }

                        Teams team = new Teams();
                        String name = record.get(indexOfClubName);
                        team.setName(name);
                        team.setYearOfCreating(yearOfCreation);
                        team.setWebsite(record.get(indexOfClubWebsite));
                        String stadium = stadiums.get(record.get(indexOfStadiumIdInClubsCSV)).get(stadiumNameInListFromHashMap);
                        team.setStadium(stadium);
                        team.setCity(stadiums.get(record.get(indexOfStadiumIdInClubsCSV)).get(stadiumCityInListFromHashMap));
                        if(stadiums.get(record.get(indexOfStadiumIdInClubsCSV)).get(stadiumCapacityInListFromHashMap).equals("\\N")){
                            team.setStadiumCapacity(null);
                        }else {
                            team.setStadiumCapacity(Integer.parseInt(stadiums.get(record.get(indexOfStadiumIdInClubsCSV)).get(stadiumCapacityInListFromHashMap)));
                        }

                        teamsRepository.save(team);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        };
    }

    private HashMap<String, Integer> searchYearOfCreation() throws IOException {
        HashMap<String, Integer> clubsCreation = new HashMap<>();

        String htmlSourceLink = "https://www.footballhistory.org/club/establishment.html";

        Document document = Jsoup.connect(htmlSourceLink).get();

        Elements rows = document.getElementsByTag("tr");

        int indexOfTeamName = 2;
        int indexOfCreationYear = 1;
        int startIndexOfString = 0;
        int lastDigitFromCreationYear = 4;
        String FCStringToRemove = " FC";
        String AFCStringToRemove = " AFC";

        for(Element row : rows){
            Elements columns = row.getAllElements();

            if(columns.text().contains("England") || columns.text().contains("Wales")){
                if(columns.get(indexOfTeamName).text().equals("Sheffield FC"))
                {
                    Integer integer = Integer
                            .parseInt(columns
                                    .get(indexOfCreationYear)
                                    .text()
                                    .substring(startIndexOfString, lastDigitFromCreationYear));
                    clubsCreation.put(columns.get(indexOfTeamName).text(), integer);
                }else
                {
                    String name = columns.get(indexOfTeamName).text();
                    if(columns.get(indexOfTeamName).text().endsWith(FCStringToRemove)) {
                        name = columns.get(indexOfTeamName)
                                .text()
                                .substring(startIndexOfString, name.length() - FCStringToRemove.length());
                    } else if (columns.get(indexOfTeamName).text().endsWith(AFCStringToRemove)) {
                        name = columns.get(indexOfTeamName)
                                .text()
                                .substring(startIndexOfString, name.length() - AFCStringToRemove.length());
                    }

                    if(columns.get(indexOfTeamName).text().contains("&")){
                        name = name.replace("&", "and");
                    }

                    clubsCreation.put(name, Integer.parseInt(columns.get(indexOfCreationYear).text()));
                }
            }
        }

        return clubsCreation;
    }
}
