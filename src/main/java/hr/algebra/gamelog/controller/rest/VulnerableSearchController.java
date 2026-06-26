package hr.algebra.gamelog.controller.rest;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class VulnerableSearchController {

    @GetMapping("/search")
    public String search(@RequestParam String username) {
        String query = "SELECT * FROM users WHERE username = '" + username + "'";
        return query;
    }
}