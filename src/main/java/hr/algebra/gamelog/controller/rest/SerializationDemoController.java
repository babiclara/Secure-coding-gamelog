package hr.algebra.gamelog.controller.rest;

import hr.algebra.gamelog.dto.GameSnapshot;
import hr.algebra.gamelog.service.SecureDeserializationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;

@RestController
@RequestMapping("/api/serialization")
public class SerializationDemoController {

    private final SecureDeserializationService serializationService;

    public SerializationDemoController(SecureDeserializationService serializationService) {
        this.serializationService = serializationService;
    }

    @PostMapping("/serialize")
    public ResponseEntity<String> serialize() throws Exception {
        GameSnapshot snapshot = new GameSnapshot(1L, "Elden Ring", "COMPLETED", 120);
        String path = System.getProperty("java.io.tmpdir") + File.separator + "snapshot.ser";
        serializationService.serialize(snapshot, path);
        return ResponseEntity.ok("Serialized to: " + path);
    }

    @GetMapping("/deserialize")
    public ResponseEntity<String> deserialize() throws Exception {
        String path = System.getProperty("java.io.tmpdir") + File.separator + "snapshot.ser";
        GameSnapshot snapshot = (GameSnapshot) serializationService.deserialize(path);
        return ResponseEntity.ok("Deserialized: " + snapshot.getTitle() + " - " + snapshot.getStatus());
    }

    @PostMapping("/deserialize-file")
    public ResponseEntity<String> deserializeFile(@RequestParam("file") MultipartFile file) throws Exception {
        Path tempFile = Files.createTempFile("upload_", ".ser");
        file.transferTo(tempFile.toFile());
        Object result = serializationService.deserialize(tempFile.toString());
        return ResponseEntity.ok("Deserialized successfully: " + result.getClass().getSimpleName());
    }
}