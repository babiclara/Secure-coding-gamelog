package hr.algebra.gamelog.dto;

import hr.algebra.gamelog.entity.Game;
import hr.algebra.gamelog.enums.DifficultyTier;
import hr.algebra.gamelog.enums.GameStatus;
import hr.algebra.gamelog.enums.Genre;
import hr.algebra.gamelog.enums.PlayStyle;
import hr.algebra.gamelog.enums.Platform;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Schema(description = "Video game data transfer object")
public record GameDto(

    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
    Long id,

    @NotBlank @Size(max = 200)
    @Schema(description = "Game title", example = "Elden Ring")
    String title,

    @Size(max = 100)
    @Schema(description = "Developer", example = "FromSoftware")
    String developer,

    @Size(max = 100)
    @Schema(description = "Publisher", example = "Bandai Namco")
    String publisher,

    @Min(1980) @Max(2030)
    @Schema(description = "Release year", example = "2022")
    Integer releaseYear,

    @NotNull
    @Schema(description = "Primary genre")
    Genre genre,

    @Schema(description = "Secondary genre")
    Genre secondaryGenre,

    @NotNull
    @Schema(description = "Platform")
    Platform platform,

    @NotNull
    @Schema(description = "Current status")
    GameStatus status,

    @Schema(description = "Difficulty setting")
    DifficultyTier difficulty,

    @Schema(description = "How you play it")
    PlayStyle playStyle,

    @Min(0) @Max(10000)
    @Schema(description = "Total hours played", example = "120")
    Integer hoursPlayed,

    @Min(0) @Max(100)
    @Schema(description = "Story completion %", example = "100")
    Integer completionPercent,

    @Min(0) @Max(500)
    @Schema(description = "Achievements unlocked", example = "42")
    Integer achievementsUnlocked,

    @Min(0) @Max(500)
    @Schema(description = "Total achievements", example = "42")
    Integer achievementsTotal,

    @Min(1) @Max(5)
    @Schema(description = "Personal rating 1-5", example = "5")
    Integer rating,

    @Min(1) @Max(10)
    @Schema(description = "Story score 1-10", example = "10")
    Integer storyScore,

    @Min(1) @Max(10)
    @Schema(description = "Gameplay score 1-10", example = "10")
    Integer gameplayScore,

    @Min(1) @Max(10)
    @Schema(description = "Visuals score 1-10", example = "9")
    Integer visualsScore,

    @Min(1) @Max(10)
    @Schema(description = "Soundtrack score 1-10", example = "10")
    Integer soundtrackScore,

    @Min(1) @Max(10)
    @Schema(description = "Replay value 1-10", example = "8")
    Integer replayValue,

    @Schema(description = "Platinum trophy earned")
    boolean platinumTrophy,

    @Schema(description = "Game of the Year contender")
    boolean gotyContender,

    @Schema(description = "Comfort game / always return to it")
    boolean comfortGame,

    @Schema(description = "Recommend to friends")
    boolean recommendToFriend,

    @Schema(description = "Purchase date")
    LocalDate purchaseDate,

    @Schema(description = "Started date")
    LocalDate startedDate,

    @Schema(description = "Finished date")
    LocalDate finishedDate,

    @Digits(integer = 6, fraction = 2)
    @Schema(description = "Price paid in EUR", example = "59.99")
    BigDecimal pricePaidEur,

    @Size(max = 100)
    @Schema(description = "Current character / build", example = "Faith / Strength dragon-slayer")
    String currentBuild,

    @Size(max = 200)
    @Schema(description = "Mood / vibe tags", example = "Sublime, melancholy, weekly therapy session")
    String moodTags,

    @Size(max = 100)
    @Schema(description = "Main protagonist", example = "Tarnished, Aloy, V")
    String mainProtagonist,

    @Size(max = 500)
    @Schema(description = "Favorite moment / scene")
    String favoriteMoment,

    @Size(max = 2000)
    @Schema(description = "Personal review")
    String review,

    @Size(max = 2000)
    @Schema(description = "Private notes")
    String personalNotes,

    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
    String addedBy,

    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
    LocalDateTime createdAt,

    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
    LocalDateTime updatedAt
) {
    public static GameDto from(Game g) {
        return new GameDto(
            g.getId(),
            g.getTitle(),
            g.getDeveloper(),
            g.getPublisher(),
            g.getReleaseYear(),
            g.getGenre(),
            g.getSecondaryGenre(),
            g.getPlatform(),
            g.getStatus(),
            g.getDifficulty(),
            g.getPlayStyle(),
            g.getHoursPlayed(),
            g.getCompletionPercent(),
            g.getAchievementsUnlocked(),
            g.getAchievementsTotal(),
            g.getRating(),
            g.getStoryScore(),
            g.getGameplayScore(),
            g.getVisualsScore(),
            g.getSoundtrackScore(),
            g.getReplayValue(),
            g.isPlatinumTrophy(),
            g.isGotyContender(),
            g.isComfortGame(),
            g.isRecommendToFriend(),
            g.getPurchaseDate(),
            g.getStartedDate(),
            g.getFinishedDate(),
            g.getPricePaidEur(),
            g.getCurrentBuild(),
            g.getMoodTags(),
            g.getMainProtagonist(),
            g.getFavoriteMoment(),
            g.getReview(),
            g.getPersonalNotes(),
            g.getAddedBy() != null ? g.getAddedBy().getUsername() : null,
            g.getCreatedAt(),
            g.getUpdatedAt()
        );
    }

    public void applyTo(Game g) {
        g.setTitle(title);
        g.setDeveloper(developer);
        g.setPublisher(publisher);
        g.setReleaseYear(releaseYear);
        g.setGenre(genre);
        g.setSecondaryGenre(secondaryGenre);
        g.setPlatform(platform);
        g.setStatus(status);
        g.setDifficulty(difficulty);
        g.setPlayStyle(playStyle);
        g.setHoursPlayed(hoursPlayed);
        g.setCompletionPercent(completionPercent);
        g.setAchievementsUnlocked(achievementsUnlocked);
        g.setAchievementsTotal(achievementsTotal);
        g.setRating(rating);
        g.setStoryScore(storyScore);
        g.setGameplayScore(gameplayScore);
        g.setVisualsScore(visualsScore);
        g.setSoundtrackScore(soundtrackScore);
        g.setReplayValue(replayValue);
        g.setPlatinumTrophy(platinumTrophy);
        g.setGotyContender(gotyContender);
        g.setComfortGame(comfortGame);
        g.setRecommendToFriend(recommendToFriend);
        g.setPurchaseDate(purchaseDate);
        g.setStartedDate(startedDate);
        g.setFinishedDate(finishedDate);
        g.setPricePaidEur(pricePaidEur);
        g.setCurrentBuild(currentBuild);
        g.setMoodTags(moodTags);
        g.setMainProtagonist(mainProtagonist);
        g.setFavoriteMoment(favoriteMoment);
        g.setReview(review);
        g.setPersonalNotes(personalNotes);
    }
}
