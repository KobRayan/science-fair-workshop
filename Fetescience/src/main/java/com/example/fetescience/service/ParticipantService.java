package com.example.fetescience.service;

import com.example.fetescience.model.Participant;
import com.example.fetescience.repository.ParticipantRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ParticipantService {
    private final ParticipantRepository repo;
    public ParticipantService(repo){
        this.repo=repo;
    }
}