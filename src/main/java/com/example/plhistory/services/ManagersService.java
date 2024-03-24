package com.example.plhistory.services;

import com.example.plhistory.entities.Managers;

import java.util.List;

public interface ManagersService {

    List<Managers> getManagers();

    List<String> getAllDifferentNations();

    Managers getManager(String name);
}
