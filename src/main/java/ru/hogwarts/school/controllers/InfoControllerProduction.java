package ru.hogwarts.school.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.context.annotation.Profile;
import org.springframework.web.bind.annotation.*;
import ru.hogwarts.school.services.InfoService;
@Profile("production")
@Tag(name = "Инфо",description = "Информационный контроллер")
@RestController
@RequestMapping("/")
public class InfoControllerProduction {
    private final InfoService infoService;

    public InfoControllerProduction(InfoService infoService) {
        this.infoService = infoService;
    }
    @GetMapping("/port")
    @Operation(summary = "Получение порта")
    public String portInfo() {
        return infoService.portInfo();
    }
}
