package hr.algebra.gamelog.config;

import hr.algebra.gamelog.entity.Game;
import hr.algebra.gamelog.entity.User;
import hr.algebra.gamelog.enums.DifficultyTier;
import hr.algebra.gamelog.enums.GameStatus;
import hr.algebra.gamelog.enums.Genre;
import hr.algebra.gamelog.enums.PlayStyle;
import hr.algebra.gamelog.enums.Platform;
import hr.algebra.gamelog.enums.Role;
import hr.algebra.gamelog.repository.GameRepository;
import hr.algebra.gamelog.repository.UserRepository;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDate;

@Component
public class DataInitializer implements ApplicationRunner {

    private final UserRepository userRepository;
    private final GameRepository gameRepository;
    private final PasswordEncoder passwordEncoder;

    public DataInitializer(
        UserRepository userRepository,
        GameRepository gameRepository,
        PasswordEncoder passwordEncoder
    ) {
        this.userRepository = userRepository;
        this.gameRepository = gameRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(ApplicationArguments args) {
        if (userRepository.count() > 0) return;

        User admin = new User();
        admin.setUsername("admin");
        admin.setEmail("admin@gamelog.hr");
        admin.setPassword(passwordEncoder.encode(System.getenv().getOrDefault("ADMIN_PASSWORD", "changeme_admin")));
        admin.setRole(Role.ADMIN);
        admin = userRepository.save(admin);

        User user = new User();
        user.setUsername("user");
        user.setEmail("user@gamelog.hr");
        user.setPassword(passwordEncoder.encode(System.getenv().getOrDefault("USER_PASSWORD", "changeme_user")));
        user.setRole(Role.USER);
        userRepository.save(user);

        createGame("Elden Ring", "FromSoftware", "Bandai Namco", 2022,
            Genre.SOULSLIKE, Genre.OPEN_WORLD, Platform.PS5,
            GameStatus.COMPLETED_100, DifficultyTier.SOULSBORNE, PlayStyle.SOLO,
            142, 100, 42, 42, 5, 9, 10, 9, 10, 9,
            true, true, false, true,
            LocalDate.of(2024, 3, 14), LocalDate.of(2024, 3, 15), LocalDate.of(2024, 6, 28),
            new BigDecimal("59.99"), "Faith / Strength dragon-slayer, RL150",
            "Sublime, melancholy, weekly therapy session, dark beauty",
            "Tarnished (custom)",
            "Beating Malenia on attempt 87 after rage-quitting for 3 weeks. Genuinely shed a tear.",
            "FromSoft's magnum opus. The Lands Between is the greatest open world ever designed — every corner rewards exploration. Boss design is unparalleled. Got the Platinum after 142 hours. Already replaying as a wizard.",
            "Platinum unlocked. Best game of the decade for me. Shadow of the Erdtree DLC waiting in queue.", admin);

        createGame("Baldur's Gate 3", "Larian Studios", "Larian Studios", 2023,
            Genre.RPG, Genre.ACTION_RPG, Platform.PC_STEAM,
            GameStatus.PLAYING, DifficultyTier.NORMAL, PlayStyle.SOLO,
            87, 65, 28, 54, 5, 10, 10, 9, 9, 10,
            false, true, false, true,
            LocalDate.of(2024, 8, 3), LocalDate.of(2024, 8, 5), null,
            new BigDecimal("59.99"), "Half-elf paladin, oath of vengeance, charisma 18",
            "Sprawling, romantic, emergent storytelling, party banter heaven",
            "The Dark Urge (Tav custom)",
            "Astarion's full romance arc had me sobbing at 2am on a Tuesday. Larian put EMOTIONS in here.",
            "The high water mark for CRPGs. Every decision matters, every NPC has depth, the dice rolls are tense in the best way. Halsin enjoyers stay winning.",
            "On Act 3, taking it slow. Already planning the evil playthrough. GOTY 2023 hands down.", admin);

        createGame("Hades II", "Supergiant Games", "Supergiant Games", 2024,
            Genre.ROGUELIKE, Genre.ACTION_RPG, Platform.PC_STEAM,
            GameStatus.PLAYING, DifficultyTier.HARD, PlayStyle.SOLO,
            54, 70, null, null, 5, 9, 10, 10, 10, 10,
            false, true, true, true,
            LocalDate.of(2024, 5, 6), LocalDate.of(2024, 5, 6), null,
            new BigDecimal("29.99"), "Melinoë / Sister Blade + Witch's Staff",
            "Mythological, addictive, run-based comfort, peak Supergiant",
            "Melinoë",
            "Every interaction with Aphrodite. The voice direction is insane.",
            "Supergiant somehow improved on Hades. The witch's staff and combat magic system feels FRESH. The art direction makes me want to cry. Still in early access and already a 10/10.",
            "Comfort game. One run before bed every night. Cannot wait for full release.", admin);

        createGame("Hollow Knight: Silksong", "Team Cherry", "Team Cherry", 2025,
            Genre.METROIDVANIA, Genre.PLATFORMER, Platform.SWITCH,
            GameStatus.PLAYING, DifficultyTier.HARD, PlayStyle.SOLO,
            32, 40, null, null, 5, 9, 10, 10, 10, 9,
            false, true, false, true,
            LocalDate.of(2025, 9, 4), LocalDate.of(2025, 9, 4), null,
            new BigDecimal("19.99"), "Hornet, default needle, Silk Soar",
            "Wait was worth it, vast, melancholy bug kingdom, art-as-religion",
            "Hornet",
            "The Citadel music drop. Goosebumps. Christopher Larkin remains undefeated.",
            "7 years late but every minute is worth it. Hornet's moveset feels distinct from the Knight. Pharloom's biomes are stunning. Team Cherry cooked.",
            "Still early game. Taking it slow because I don't want it to end.", admin);

        createGame("Stardew Valley", "ConcernedApe", "ConcernedApe", 2016,
            Genre.SIMULATION, Genre.INDIE, Platform.PC_STEAM,
            GameStatus.COMPLETED_100, DifficultyTier.STORY, PlayStyle.SOLO,
            287, 100, 49, 49, 5, 8, 9, 7, 10, 10,
            false, false, true, true,
            LocalDate.of(2020, 11, 2), LocalDate.of(2020, 11, 5), LocalDate.of(2021, 3, 18),
            new BigDecimal("13.99"), "Year 6, married Sebastian, full Community Center",
            "Cozy, healing, my comfort game, soundtrack therapy",
            "The Farmer (custom)",
            "Reaching the bottom of Skull Cavern for the first time. Pure adrenaline.",
            "ConcernedApe alone made what Stardew is. The fact that one human created this is staggering. My most-played game ever. 287 hours and counting through replays.",
            "Will keep replaying forever. Update 1.6 brought me back. Forever indebted to Eric Barone.", admin);

        createGame("Final Fantasy VII Rebirth", "Square Enix", "Square Enix", 2024,
            Genre.RPG, Genre.ACTION_RPG, Platform.PS5,
            GameStatus.BEATEN, DifficultyTier.NORMAL, PlayStyle.SOLO,
            96, 100, 38, 54, 5, 10, 9, 10, 10, 7,
            false, true, false, true,
            LocalDate.of(2024, 2, 29), LocalDate.of(2024, 3, 1), LocalDate.of(2024, 5, 10),
            new BigDecimal("69.99"), "Cloud + Tifa + Aerith team, Materia maxed",
            "Sprawling, emotional, nostalgia-charged, ambitious",
            "Cloud Strife",
            "Aerith's chapter. They knew exactly what they were doing. Sobbed.",
            "The middle of the remake trilogy nails what FFVII fans needed. Open zones feel alive, combat is electric, music elevates everything. Mini-games are a love letter to PS1 era.",
            "Going for Platinum eventually. Not 100% yet — saving combat sim grind for off-season.", admin);

        createGame("Cyberpunk 2077", "CD Projekt Red", "CD Projekt", 2020,
            Genre.RPG, Genre.OPEN_WORLD, Platform.PC_STEAM,
            GameStatus.BEATEN, DifficultyTier.HARD, PlayStyle.SOLO,
            108, 95, 44, 57, 4, 9, 8, 10, 9, 7,
            false, false, false, true,
            LocalDate.of(2023, 10, 10), LocalDate.of(2023, 10, 12), LocalDate.of(2024, 1, 6),
            new BigDecimal("29.99"), "Netrunner V, full quickhack monster",
            "Redemption arc real, neon-soaked, Phantom Liberty completed",
            "V (Female V)",
            "Phantom Liberty's Songbird arc ending. Idris Elba elevated everything.",
            "Played AFTER 2.0 + Phantom Liberty. CDPR fixed it. Night City breathes. The Phantom Liberty expansion is genuinely one of the best RPG DLCs ever — Reed/Songbird dilemma destroyed me.",
            "Wait until 2.0+ to play. The 2020 launch version is dead. The current version is a masterpiece.", admin);

        createGame("The Witcher 3: Wild Hunt", "CD Projekt Red", "CD Projekt", 2015,
            Genre.RPG, Genre.OPEN_WORLD, Platform.PC_STEAM,
            GameStatus.COMPLETED_100, DifficultyTier.HARD, PlayStyle.SOLO,
            218, 100, 78, 78, 5, 10, 9, 9, 10, 8,
            true, true, true, true,
            LocalDate.of(2022, 6, 14), LocalDate.of(2022, 7, 1), LocalDate.of(2023, 4, 22),
            new BigDecimal("9.99"), "Igni-focused Geralt, NG+ run completed",
            "Forever and always, dark fantasy peak, fairytale brutality",
            "Geralt of Rivia",
            "Bloody Baron's storyline. No fantasy game has come close to this writing.",
            "STILL the gold standard for open-world RPGs. The Bloody Baron quest line alone justifies the entire genre. Blood & Wine DLC is bigger and better than most full games.",
            "Got Platinum on PC after console replay. Will return again. Comfort game tier.", admin);

        createGame("Tears of the Kingdom", "Nintendo EPD", "Nintendo", 2023,
            Genre.OPEN_WORLD, Genre.ACTION_RPG, Platform.SWITCH,
            GameStatus.ON_HOLD, DifficultyTier.NORMAL, PlayStyle.SOLO,
            73, 55, null, null, 4, 8, 9, 8, 10, 7,
            false, false, false, true,
            LocalDate.of(2023, 5, 12), LocalDate.of(2023, 5, 12), null,
            new BigDecimal("69.99"), "Master Sword + duped weapons (pre-patch glitch)",
            "Magical, exhausting, BotW fatigue, ambition overload",
            "Link",
            "Building the first hover bike with Ultrahand. Genuinely felt like a wizard.",
            "Nintendo's engineering is supernatural but I hit BotW fatigue hard. Ultrahand is genius but combat still has shallow enemy variety. Will return when I have brain space.",
            "On hold since November. Need a long break before tackling the Depths and shrines.", admin);

        createGame("Persona 5 Royal", "Atlus", "Sega", 2019,
            Genre.RPG, Genre.VISUAL_NOVEL, Platform.PC_STEAM,
            GameStatus.BACKLOG, null, null,
            0, 0, null, null, null, null, null, null, null, null,
            false, false, false, false,
            LocalDate.of(2024, 10, 31), null, null,
            new BigDecimal("19.99"), null,
            null,
            "Joker (Ren Amamiya)",
            null,
            null,
            "Got it in the Halloween sale. 110+ hours intimidates me. Saving for winter break.", admin);
    }

    private void createGame(
        String title, String developer, String publisher, Integer releaseYear,
        Genre genre, Genre secondaryGenre, Platform platform,
        GameStatus status, DifficultyTier difficulty, PlayStyle playStyle,
        Integer hours, Integer completion, Integer achievUnlocked, Integer achievTotal,
        Integer rating, Integer story, Integer gameplay, Integer visuals,
        Integer soundtrack, Integer replay,
        boolean platinum, boolean goty, boolean comfort, boolean recommend,
        LocalDate purchase, LocalDate started, LocalDate finished,
        BigDecimal price, String build, String mood, String protagonist,
        String favoriteMoment, String review, String notes, User addedBy
    ) {
        Game g = new Game();
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
        g.setHoursPlayed(hours);
        g.setCompletionPercent(completion);
        g.setAchievementsUnlocked(achievUnlocked);
        g.setAchievementsTotal(achievTotal);
        g.setRating(rating);
        g.setStoryScore(story);
        g.setGameplayScore(gameplay);
        g.setVisualsScore(visuals);
        g.setSoundtrackScore(soundtrack);
        g.setReplayValue(replay);
        g.setPlatinumTrophy(platinum);
        g.setGotyContender(goty);
        g.setComfortGame(comfort);
        g.setRecommendToFriend(recommend);
        g.setPurchaseDate(purchase);
        g.setStartedDate(started);
        g.setFinishedDate(finished);
        g.setPricePaidEur(price);
        g.setCurrentBuild(build);
        g.setMoodTags(mood);
        g.setMainProtagonist(protagonist);
        g.setFavoriteMoment(favoriteMoment);
        g.setReview(review);
        g.setPersonalNotes(notes);
        g.setAddedBy(addedBy);
        gameRepository.save(g);
    }
}
