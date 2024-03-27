package ru.hogwarts.school.services;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class InfoService {
    @Value("${server.port}")
    private int serverPort;
    public String portInfo(){
        return "Порт: " + serverPort;
    }
}
