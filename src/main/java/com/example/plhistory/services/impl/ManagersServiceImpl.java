package com.example.plhistory.services.impl;

import com.example.plhistory.entities.Managers;
import com.example.plhistory.repositories.ManagersRepository;
import com.example.plhistory.services.ManagersService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ManagersServiceImpl implements ManagersService {

    private final ManagersRepository managersRepository;

    public ManagersServiceImpl(ManagersRepository managersRepository) {
        this.managersRepository = managersRepository;
    }

    @Override
    public List<Managers> getManagers() {
        return managersRepository.findAll();
    }

    @Override
    public List<String> getAllDifferentNations() {

        return managersRepository.findDistinctNation();
    }

    @Override
    public Managers getManager(String name) {
        return managersRepository.findByName(name);
    }
}
