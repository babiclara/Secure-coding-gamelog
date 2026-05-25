package hr.algebra.gamelog.repository;

import hr.algebra.gamelog.entity.Game;
import hr.algebra.gamelog.enums.GameStatus;
import hr.algebra.gamelog.enums.Genre;
import hr.algebra.gamelog.enums.Platform;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface GameRepository extends JpaRepository<Game, Long> {

    @Query("""
        SELECT g FROM Game g
        WHERE (:query IS NULL OR LOWER(g.title)            LIKE LOWER(CONCAT('%', :query, '%'))
                              OR LOWER(g.developer)        LIKE LOWER(CONCAT('%', :query, '%'))
                              OR LOWER(g.publisher)        LIKE LOWER(CONCAT('%', :query, '%'))
                              OR LOWER(g.mainProtagonist)  LIKE LOWER(CONCAT('%', :query, '%')))
          AND (:genre IS NULL OR g.genre = :genre OR g.secondaryGenre = :genre)
          AND (:platform IS NULL OR g.platform = :platform)
          AND (:status IS NULL OR g.status = :status)
          AND (:gotyOnly = false OR g.gotyContender = true)
        ORDER BY g.status ASC, g.rating DESC NULLS LAST, g.hoursPlayed DESC NULLS LAST, g.title ASC
        """)
    List<Game> search(
        @Param("query") String query,
        @Param("genre") Genre genre,
        @Param("platform") Platform platform,
        @Param("status") GameStatus status,
        @Param("gotyOnly") boolean gotyOnly
    );

    List<Game> findAllByOrderByStatusAscRatingDescTitleAsc();
}
