package id.co.askrindo.spreadingofrecoveries.controller;

import id.co.askrindo.spreadingofrecoveries.service.TSubrogationSorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/sor")
public class SorController {

    @Autowired
    private TSubrogationSorService tSubrogationSorService;

    @PostMapping("/create")
    public ResponseEntity<?> create(@RequestParam String message){
        return tSubrogationSorService.recoveriesSorProsess(message);
    }
}
