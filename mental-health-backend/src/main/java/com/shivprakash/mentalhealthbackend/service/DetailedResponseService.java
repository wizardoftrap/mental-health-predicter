package com.shivprakash.mentalhealthbackend.service;

import com.shivprakash.mentalhealthbackend.model.DetailedResponse;
import com.shivprakash.mentalhealthbackend.repository.DetailedResponseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class DetailedResponseService {

    @Autowired
    private DetailedResponseRepository detailedResponseRepository;

    public DetailedResponse saveDetailedResponse(DetailedResponse detailedResponse) {
        return detailedResponseRepository.save(detailedResponse);
    }

    public Optional<DetailedResponse> getResponseByUserId(Long userId) {
        return detailedResponseRepository.findByUserId(userId);
    }
    public void deleteDetailedResponseByUserId(Long userId) {

        Optional<DetailedResponse> optionalDetailedResponse = detailedResponseRepository.findByUserId(userId);

        optionalDetailedResponse.ifPresent(detailedResponse -> detailedResponseRepository.delete(detailedResponse));

    }
}