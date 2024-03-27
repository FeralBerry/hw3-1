package ru.hogwarts.school.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.hogwarts.school.services.InfoService;
@Tag(name = "Инфо",description = "Информационный контроллер")
@RestController
@RequestMapping("/")
public class InfoController {
    private final InfoService infoService;

    public InfoController(InfoService infoService) {
        this.infoService = infoService;
    }
    @GetMapping("/port")
    @Operation(summary = "Получение порта")
    public String portInfo() {
        return infoService.portInfo();
    }
}
