package hr.algebra.gamelog.service;

import hr.algebra.gamelog.dto.GameDto;
import hr.algebra.gamelog.entity.Game;
import hr.algebra.gamelog.entity.User;
import hr.algebra.gamelog.enums.GameStatus;
import hr.algebra.gamelog.enums.Genre;
import hr.algebra.gamelog.enums.Platform;
import hr.algebra.gamelog.repository.GameRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class GameService {

    private final GameRepository gameRepository;

    public GameService(GameRepository gameRepository) {
        this.gameRepository = gameRepository;
    }

    public List<GameDto> findAll() {
        return gameRepository.findAllByOrderByStatusAscRatingDescTitleAsc()
            .stream()
            .map(GameDto::from)
            .toList();
    }

    public GameDto findById(Long id) {
        return gameRepository.findById(id)
            .map(GameDto::from)
            .orElseThrow(() -> new NoSuchElementException("Game not found: " + id));
    }

    public List<GameDto> search(String query, Genre genre, Platform platform, GameStatus status, boolean gotyOnly) {
        String nq = (query != null && query.isBlank()) ? null : query;
        return gameRepository.search(nq, genre, platform, status, gotyOnly)
            .stream()
            .map(GameDto::from)
            .toList();
    }

    @Transactional
    public GameDto create(GameDto dto, User creator) {
        Game game = new Game();
        dto.applyTo(game);
        game.setAddedBy(creator);
        return GameDto.from(gameRepository.save(game));
    }

    @Transactional
    public GameDto update(Long id, GameDto dto) {
        Game game = gameRepository.findById(id)
            .orElseThrow(() -> new NoSuchElementException("Game not found: " + id));
        dto.applyTo(game);
        return GameDto.from(gameRepository.save(game));
    }

    @Transactional
    public void delete(Long id) {
        if (!gameRepository.existsById(id)) {
            throw new NoSuchElementException("Game not found: " + id);
        }
        gameRepository.deleteById(id);
    }
}
