package hr.algebra.gamelog.entity;

import hr.algebra.gamelog.enums.DifficultyTier;
import hr.algebra.gamelog.enums.GameStatus;
import hr.algebra.gamelog.enums.Genre;
import hr.algebra.gamelog.enums.PlayStyle;
import hr.algebra.gamelog.enums.Platform;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;

@Entity
@Table(name = "games")
public class Game {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Size(max = 200)
    @Column(nullable = false, length = 200)
    private String title;

    @Size(max = 100)
    private String developer;

    @Size(max = 100)
    private String publisher;

    @Min(1980) @Max(2030)
    private Integer releaseYear;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Genre genre;

    @Enumerated(EnumType.STRING)
    private Genre secondaryGenre;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Platform platform;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private GameStatus status;

    @Enumerated(EnumType.STRING)
    private DifficultyTier difficulty;

    @Enumerated(EnumType.STRING)
    private PlayStyle playStyle;

    @Min(0) @Max(10000)
    private Integer hoursPlayed;

    @Min(0) @Max(100)
    private Integer completionPercent;

    @Min(0) @Max(500)
    private Integer achievementsUnlocked;

    @Min(0) @Max(500)
    private Integer achievementsTotal;

    @Min(1) @Max(5)
    private Integer rating;

    @Min(1) @Max(10)
    private Integer storyScore;

    @Min(1) @Max(10)
    private Integer gameplayScore;

    @Min(1) @Max(10)
    private Integer visualsScore;

    @Min(1) @Max(10)
    private Integer soundtrackScore;

    @Min(1) @Max(10)
    private Integer replayValue;

    @Column(nullable = false)
    private boolean platinumTrophy = false;

    @Column(nullable = false)
    private boolean gotyContender = false;

    @Column(nullable = false)
    private boolean comfortGame = false;

    @Column(nullable = false)
    private boolean recommendToFriend = false;

    private LocalDate purchaseDate;
    private LocalDate startedDate;
    private LocalDate finishedDate;

    @Digits(integer = 6, fraction = 2)
    private BigDecimal pricePaidEur;

    @Size(max = 100)
    private String currentBuild;

    @Size(max = 200)
    private String moodTags;

    @Size(max = 100)
    private String mainProtagonist;

    @Size(max = 500)
    @Column(length = 500)
    private String favoriteMoment;

    @Size(max = 2000)
    @Column(length = 2000)
    private String review;

    @Size(max = 2000)
    @Column(length = 2000)
    private String personalNotes;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "added_by_id")
    private User addedBy;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now(ZoneId.of("UTC"));
        updatedAt = LocalDateTime.now(ZoneId.of("UTC"));
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now(ZoneId.of("UTC"));
    }

    public Long getId()                                  { return id; }
    public void setId(Long id)                           { this.id = id; }
    public String getTitle()                             { return title; }
    public void setTitle(String title)                   { this.title = title; }
    public String getDeveloper()                         { return developer; }
    public void setDeveloper(String developer)           { this.developer = developer; }
    public String getPublisher()                         { return publisher; }
    public void setPublisher(String publisher)           { this.publisher = publisher; }
    public Integer getReleaseYear()                      { return releaseYear; }
    public void setReleaseYear(Integer releaseYear)      { this.releaseYear = releaseYear; }
    public Genre getGenre()                              { return genre; }
    public void setGenre(Genre genre)                    { this.genre = genre; }
    public Genre getSecondaryGenre()                     { return secondaryGenre; }
    public void setSecondaryGenre(Genre s)               { this.secondaryGenre = s; }
    public Platform getPlatform()                        { return platform; }
    public void setPlatform(Platform platform)           { this.platform = platform; }
    public GameStatus getStatus()                        { return status; }
    public void setStatus(GameStatus status)             { this.status = status; }
    public DifficultyTier getDifficulty()                { return difficulty; }
    public void setDifficulty(DifficultyTier d)          { this.difficulty = d; }
    public PlayStyle getPlayStyle()                      { return playStyle; }
    public void setPlayStyle(PlayStyle playStyle)        { this.playStyle = playStyle; }
    public Integer getHoursPlayed()                      { return hoursPlayed; }
    public void setHoursPlayed(Integer hoursPlayed)      { this.hoursPlayed = hoursPlayed; }
    public Integer getCompletionPercent()                { return completionPercent; }
    public void setCompletionPercent(Integer c)          { this.completionPercent = c; }
    public Integer getAchievementsUnlocked()             { return achievementsUnlocked; }
    public void setAchievementsUnlocked(Integer a)       { this.achievementsUnlocked = a; }
    public Integer getAchievementsTotal()                { return achievementsTotal; }
    public void setAchievementsTotal(Integer a)          { this.achievementsTotal = a; }
    public Integer getRating()                           { return rating; }
    public void setRating(Integer rating)                { this.rating = rating; }
    public Integer getStoryScore()                       { return storyScore; }
    public void setStoryScore(Integer storyScore)        { this.storyScore = storyScore; }
    public Integer getGameplayScore()                    { return gameplayScore; }
    public void setGameplayScore(Integer g)              { this.gameplayScore = g; }
    public Integer getVisualsScore()                     { return visualsScore; }
    public void setVisualsScore(Integer visualsScore)    { this.visualsScore = visualsScore; }
    public Integer getSoundtrackScore()                  { return soundtrackScore; }
    public void setSoundtrackScore(Integer s)            { this.soundtrackScore = s; }
    public Integer getReplayValue()                      { return replayValue; }
    public void setReplayValue(Integer replayValue)      { this.replayValue = replayValue; }
    public boolean isPlatinumTrophy()                    { return platinumTrophy; }
    public void setPlatinumTrophy(boolean p)             { this.platinumTrophy = p; }
    public boolean isGotyContender()                     { return gotyContender; }
    public void setGotyContender(boolean gotyContender)  { this.gotyContender = gotyContender; }
    public boolean isComfortGame()                       { return comfortGame; }
    public void setComfortGame(boolean comfortGame)      { this.comfortGame = comfortGame; }
    public boolean isRecommendToFriend()                 { return recommendToFriend; }
    public void setRecommendToFriend(boolean r)          { this.recommendToFriend = r; }
    public LocalDate getPurchaseDate()                   { return purchaseDate; }
    public void setPurchaseDate(LocalDate d)             { this.purchaseDate = d; }
    public LocalDate getStartedDate()                    { return startedDate; }
    public void setStartedDate(LocalDate d)              { this.startedDate = d; }
    public LocalDate getFinishedDate()                   { return finishedDate; }
    public void setFinishedDate(LocalDate d)             { this.finishedDate = d; }
    public BigDecimal getPricePaidEur()                  { return pricePaidEur; }
    public void setPricePaidEur(BigDecimal p)            { this.pricePaidEur = p; }
    public String getCurrentBuild()                      { return currentBuild; }
    public void setCurrentBuild(String currentBuild)     { this.currentBuild = currentBuild; }
    public String getMoodTags()                          { return moodTags; }
    public void setMoodTags(String moodTags)             { this.moodTags = moodTags; }
    public String getMainProtagonist()                   { return mainProtagonist; }
    public void setMainProtagonist(String m)             { this.mainProtagonist = m; }
    public String getFavoriteMoment()                    { return favoriteMoment; }
    public void setFavoriteMoment(String f)              { this.favoriteMoment = f; }
    public String getReview()                            { return review; }
    public void setReview(String review)                 { this.review = review; }
    public String getPersonalNotes()                     { return personalNotes; }
    public void setPersonalNotes(String p)               { this.personalNotes = p; }
    public User getAddedBy()                             { return addedBy; }
    public void setAddedBy(User addedBy)                 { this.addedBy = addedBy; }
    public LocalDateTime getCreatedAt()                  { return createdAt; }
    public void setCreatedAt(LocalDateTime t)            { this.createdAt = t; }
    public LocalDateTime getUpdatedAt()                  { return updatedAt; }
    public void setUpdatedAt(LocalDateTime t)            { this.updatedAt = t; }
}
