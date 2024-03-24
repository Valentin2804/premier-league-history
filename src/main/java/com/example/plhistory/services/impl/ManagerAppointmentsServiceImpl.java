package com.example.plhistory.services.impl;

import com.example.plhistory.entities.ManagerAppointments;
import com.example.plhistory.repositories.ManagerAppointmentsRepository;
import com.example.plhistory.services.ManagerAppointmentsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ManagerAppointmentsServiceImpl implements ManagerAppointmentsService {

    private final ManagerAppointmentsRepository repository;

    @Autowired
    public ManagerAppointmentsServiceImpl(ManagerAppointmentsRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<ManagerAppointments> getManagerAppointments(){
        return repository.findAll();
    }
}
