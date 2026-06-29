package hr.algebra.gamelog;

import hr.algebra.gamelog.dto.GameDto;
import hr.algebra.gamelog.entity.Game;
import hr.algebra.gamelog.entity.User;
import hr.algebra.gamelog.enums.GameStatus;
import hr.algebra.gamelog.enums.Genre;
import hr.algebra.gamelog.enums.Platform;
import hr.algebra.gamelog.enums.Role;
import hr.algebra.gamelog.repository.GameRepository;
import hr.algebra.gamelog.service.GameService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class GameServiceTest {

    @Mock
    private GameRepository gameRepository;

    @InjectMocks
    private GameService gameService;

    private User testUser() {
        User user = new User();
        user.setUsername("testuser");
        user.setRole(Role.USER);
        return user;
    }

    private Game testGame() {
        Game game = new Game();
        game.setId(1L);
        game.setTitle("Elden Ring");
        game.setGenre(Genre.RPG);
        game.setPlatform(Platform.PC_STEAM);
        game.setStatus(GameStatus.PLAYING);
        return game;
    }

    @Test
    void testFindAllReturnsGames() {
        when(gameRepository.findAllByOrderByStatusAscRatingDescTitleAsc()).thenReturn(List.of(testGame()));
        var result = gameService.findAll();
        assertEquals(1, result.size());
        assertEquals("Elden Ring", result.get(0).title());
    }

    @Test
    void testFindByIdReturnsGame() {
        when(gameRepository.findById(1L)).thenReturn(Optional.of(testGame()));
        var result = gameService.findById(1L);
        assertEquals("Elden Ring", result.title());
    }

    @Test
    void testFindByIdThrowsWhenNotFound() {
        when(gameRepository.findById(99L)).thenReturn(Optional.empty());
        assertThrows(NoSuchElementException.class, () -> gameService.findById(99L));
    }

    @Test
    void testDeleteCallsRepository() {
        when(gameRepository.existsById(1L)).thenReturn(true);
        doNothing().when(gameRepository).deleteById(1L);
        gameService.delete(1L);
        verify(gameRepository, times(1)).deleteById(1L);
    }
    @Test
    void testCreateGame() {
        Game game = testGame();
        when(gameRepository.save(any())).thenReturn(game);

        GameDto dto = new GameDto(
                null, "Elden Ring", "", "", null,
                Genre.RPG, null, Platform.PC_STEAM, GameStatus.PLAYING, null, null,
                0, 0, 0, 0,
                null, null, null, null, null, null,
                false, false, false, false,
                null, null, null, null,
                "", "", "", "", "", "",
                null, null, null
        );

        GameDto result = gameService.create(dto, testUser());
        assertNotNull(result);
        verify(gameRepository, times(1)).save(any());
    }

    @Test
    void testUpdateGame() {
        Game game = testGame();
        when(gameRepository.findById(1L)).thenReturn(Optional.of(game));
        when(gameRepository.save(any())).thenReturn(game);

        GameDto dto = new GameDto(
                1L, "Updated Game", "", "", null,
                Genre.RPG, null, Platform.PC_STEAM, GameStatus.ON_HOLD, null, null,
                0, 0, 0, 0,
                null, null, null, null, null, null,
                false, false, false, false,
                null, null, null, null,
                "", "", "", "", "", "",
                null, null, null
        );

        GameDto result = gameService.update(1L, dto);
        assertNotNull(result);
    }

    @Test
    void testDeleteThrowsWhenNotFound() {
        when(gameRepository.existsById(99L)).thenReturn(false);
        assertThrows(NoSuchElementException.class, () -> gameService.delete(99L));
    }
}