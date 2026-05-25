package hr.algebra.gamelog.controller.rest;

import hr.algebra.gamelog.dto.GameDto;
import hr.algebra.gamelog.entity.User;
import hr.algebra.gamelog.enums.GameStatus;
import hr.algebra.gamelog.enums.Genre;
import hr.algebra.gamelog.enums.Platform;
import hr.algebra.gamelog.service.GameService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/api/games")
@SecurityRequirement(name = "bearerAuth")
@Tag(name = "Games", description = "Video game CRUD and search")
public class GameRestController {

    private final GameService gameService;

    public GameRestController(GameService gameService) {
        this.gameService = gameService;
    }

    @GetMapping
    @Operation(summary = "Get all games")
    public ResponseEntity<List<GameDto>> getAll() {
        return ResponseEntity.ok(gameService.findAll());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get a single game by ID")
    public ResponseEntity<GameDto> getById(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(gameService.findById(id));
        } catch (NoSuchElementException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/search")
    @Operation(summary = "Search games by title, developer, publisher, protagonist, genre, platform, status, or GOTY filter")
    public ResponseEntity<List<GameDto>> search(
        @RequestParam(required = false) String query,
        @RequestParam(required = false) Genre genre,
        @RequestParam(required = false) Platform platform,
        @RequestParam(required = false) GameStatus status,
        @RequestParam(defaultValue = "false") boolean gotyOnly
    ) {
        return ResponseEntity.ok(gameService.search(query, genre, platform, status, gotyOnly));
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Add a new game (admin only)")
    public ResponseEntity<GameDto> create(
        @Valid @RequestBody GameDto dto,
        @AuthenticationPrincipal User currentUser
    ) {
        return ResponseEntity.status(HttpStatus.CREATED).body(gameService.create(dto, currentUser));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Update a game (admin only)")
    public ResponseEntity<GameDto> update(
        @PathVariable Long id,
        @Valid @RequestBody GameDto dto
    ) {
        try {
            return ResponseEntity.ok(gameService.update(id, dto));
        } catch (NoSuchElementException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Delete a game (admin only)")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        try {
            gameService.delete(id);
            return ResponseEntity.noContent().build();
        } catch (NoSuchElementException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
