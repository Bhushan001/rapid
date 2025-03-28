package com.techie.rapid.core.service;

import com.techie.rapid.constants.ErrorConstants;
import com.techie.rapid.core.dto.UserDto;
import com.techie.rapid.core.entity.Workspace;
import com.techie.rapid.core.exceptions.WorkspaceNotFoundException;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.UUID;

@Service
public class UserClientService {

    @Autowired
    private RestTemplate restTemplate;

    public UserDto getUserById(UUID userId) {
        // Replace with your User microservice URL
        String userMicroserviceUrl = "http://localhost:8081/api/users";
        String url = userMicroserviceUrl + "/" + userId;
        return restTemplate.getForObject(url, UserDto.class);
    }
}
