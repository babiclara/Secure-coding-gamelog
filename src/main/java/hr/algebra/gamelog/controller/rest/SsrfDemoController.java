package hr.algebra.gamelog.controller.rest;

import hr.algebra.gamelog.security.SsrfProtectionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/fetch")
public class SsrfDemoController {

    private final SsrfProtectionService ssrfProtectionService;

    public SsrfDemoController(SsrfProtectionService ssrfProtectionService) {
        this.ssrfProtectionService = ssrfProtectionService;
    }

    @GetMapping
    public ResponseEntity<String> fetch(@RequestParam String url) {
        if (!ssrfProtectionService.isSafeUrl(url)) {
            return ResponseEntity.status(403).body("Blocked: internal or private URL not allowed");
        }
        return ResponseEntity.ok("URL is safe to fetch: " + url);
    }
}