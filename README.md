# GameLog

A personal video game tracker and backlog manager built as a Spring Boot 4 project with an MVC interface and a REST API.

## Technologies

| Component | Version |
|---|---|
| Java | 25 |
| Spring Boot | 4.0.6 |
| Spring MVC / Spring Security | 7.x |
| Thymeleaf | 3.x |
| Spring Data JPA / Hibernate | 7.x |
| H2 (in-memory) | runtime |
| Auth0 Java JWT | 4.4.0 |
| springdoc-openapi | 3.0.0 |

## Running the Project in IntelliJ IDEA

1. **Open the project:** `File → Open` → select the `gamelog` folder
2. **SDK:** `File → Project Structure → SDK` → set to **Java 25**
3. **Maven:** IntelliJ will automatically download dependencies; if not, run **Reload Maven Project**
4. **Run:** `GamelogApplication.java` → right-click → *Run*
5. **Access:** [http://localhost:8080](http://localhost:8080)

## Default User Accounts (in-memory H2)

| Username | Password | Role |
|---|---|---|
| `admin` | `admin123` | ADMIN — full management |
| `user` | `user123` | USER — read and search only |

## MVC Interface

| URL | Description | Access |
|---|---|---|
| `/` | Redirect to `/games` | — |
| `/auth/login` | Login form ("Press Start") | Public |
| `/auth/register` | Registration form ("Create Save File") | Public |
| `/games` | Library — browse and search | USER + ADMIN |
| `/games/{id}` | Game details with full breakdown | USER + ADMIN |
| `/games/new` | Add new game form | ADMIN |
| `/games/edit/{id}` | Edit game form | ADMIN |
| `/games/delete/{id}` | Delete game (POST) | ADMIN |

## REST API

Base URL: `/api`

### Authentication

| Method | URL | Description |
|---|---|---|
| `POST` | `/api/auth/login` | Login — returns access + refresh token |
| `POST` | `/api/auth/register` | Register a new user account |
| `POST` | `/api/auth/refresh` | Obtain a new access token |
| `POST` | `/api/auth/logout` | Revoke the refresh token |

### Games (requires Bearer token)

| Method | URL | Description | Role |
|---|---|---|---|
| `GET` | `/api/games` | All games | USER + ADMIN |
| `GET` | `/api/games/{id}` | Single game | USER + ADMIN |
| `GET` | `/api/games/search` | Search (`query`, `genre`, `platform`, `status`, `gotyOnly`) | USER + ADMIN |
| `POST` | `/api/games` | Add new game | ADMIN |
| `PUT` | `/api/games/{id}` | Update game | ADMIN |
| `DELETE` | `/api/games/{id}` | Delete game | ADMIN |

### Example: Login and API Usage

```bash
# Login
curl -X POST http://localhost:8080/api/auth/login \
  -H "Content-Type: application/json" \
  -d '{"username":"admin","password":"admin123"}'

# Fetch games using the access token
curl http://localhost:8080/api/games \
  -H "Authorization: Bearer <access_token>"

# Search for GOTY-contender RPGs
curl "http://localhost:8080/api/games/search?genre=RPG&gotyOnly=true" \
  -H "Authorization: Bearer <access_token>"
```

## Swagger UI

Available at: [http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html)

## H2 Console

URL: [http://localhost:8080/h2-console](http://localhost:8080/h2-console)

| Field | Value |
|---|---|
| JDBC URL | `jdbc:h2:mem:gamelogdb` |
| Username | `sa` |
| Password | *(leave blank)* |

## Key Features

- **Cyberpunk neon aesthetic** — deep midnight purple (`#0a0a14`) background, electric cyan (`#00f0ff`) + magenta (`#ff00ea`) + neon purple (`#9d4edd`) accents — the most retro-arcade-meets-cyberpunk visual of all projects
- **Clipped-corner auth box** — login page uses CSS `clip-path` polygon to create true beveled corner cuts (like a Cyberpunk 2077 menu), with neon-cyan glow brackets at each corner
- **Animated "Now Playing" indicator** — games with PLAYING or REPLAYING status get a pulsing neon badge with a blinking green dot — currently active games always stand out
- **NG+ tag for replays** — replaying games get a special "NG+" tag (gaming culture reference: New Game Plus)
- **Platinum trophy diamond badge** — games with the Platinum get a metallic platinum gradient badge in the top-right corner
- **Rotated GOTY ribbon** — Game of the Year contenders get a 45° rotated yellow-orange neon ribbon (when both Platinum AND GOTY apply, GOTY shows on the left, Platinum on the right!)
- **Live completion progress bar** — story completion percentage rendered as a neon gradient bar (magenta → cyan → green) with glow effect
- **Achievement progress bar** — separate yellow-orange achievement tracker showing X/Y unlocked with percentage
- **4-stat hero grid** on detail view — Hours, Story %, Achievements (e.g. "42/42"), Price Paid — each with neon glow text
- **5-dimension quality scoring** — Story, Gameplay, Visuals, Soundtrack, Replay Value (1-10 each) with magenta-to-cyan gradient bars
- **Scanline CRT background effect** — subtle horizontal scanlines across all pages via repeating linear gradient (the retro CRT monitor feel)
- **23 game genres** — including specific ones like Soulslike, Metroidvania, MOBA, Grand Strategy (the proper gamer taxonomy)
- **16 platforms** — separate entries for PC Steam, PC Epic, PC GOG, PC GamePass, **Steam Deck**, VR Quest, VR PSVR, retro handhelds, mobile iOS/Android (reflects actual modern gaming reality)
- **8 game statuses** — PLAYING, BEATEN, COMPLETED_100, ABANDONED, ON_HOLD, BACKLOG, WISHLIST, **REPLAYING** (the BACKLOG status is iconic to Steam culture)
- **6-tier difficulty system** — STORY → EASY → NORMAL → HARD → NIGHTMARE → **SOULSBORNE** (yes, SOULSBORNE is its own tier!)
- **8 play styles** — SOLO, CO_OP_COUCH, CO_OP_ONLINE, PVP_COMPETITIVE, PVP_CASUAL, MMO_GUILD, SPEEDRUN, COMPLETIONIST
- **Current Build field** — track your current character / loadout (e.g. "Faith/Strength dragon-slayer, RL150")
- **Main Protagonist field** — separate field for your character's name (Tarnished, V, Aloy, Geralt)
- **Favorite Moment showcase** — dedicated magenta-bordered emphasis section for that one scene that destroyed you
- **"Press Start" custom CTA** + **Player ID / Access Code** labels — fully themed arcade login
- **Courier New monospace typography throughout** — proper retro-arcade / terminal aesthetic

## 10 Curated Sample Games

| Game | Rating | Status | Hours | Notes |
|---|---|---|---|---|
| **Elden Ring** | 5★ Platinum, GOTY | COMPLETED_100 | 142 | Malenia attempt 87, Soulsborne difficulty |
| **Baldur's Gate 3** | 5★ GOTY | PLAYING | 87 | Astarion romance arc, 65% story |
| **Hades II** | 5★ GOTY, Comfort | PLAYING | 54 | Early Access, "Supergiant cooked" |
| **Hollow Knight: Silksong** | 5★ GOTY | PLAYING | 32 | "Wait was worth it" |
| **Stardew Valley** | 5★ Comfort | COMPLETED_100 | **287** | Most-played comfort game |
| **FF VII Rebirth** | 5★ GOTY | BEATEN | 96 | Aerith chapter sobbed |
| **Cyberpunk 2077** | 4★ | BEATEN | 108 | Post-2.0 redemption arc |
| **The Witcher 3** | 5★ Platinum, GOTY, Comfort | COMPLETED_100 | 218 | Bloody Baron tribute |
| **Tears of the Kingdom** | 4★ | ON_HOLD | 73 | BotW fatigue confession |
| **Persona 5 Royal** | — | BACKLOG | 0 | "110 hours intimidates me" |

## Project Structure

```
src/main/java/hr/algebra/gamelog/
├── GamelogApplication.java
├── config/
│   ├── DataInitializer.java          # 10 sample games on startup
│   ├── OpenApiConfig.java            # Swagger / OpenAPI configuration
│   └── SecurityConfig.java           # Two filter chains (API + MVC)
├── controller/
│   ├── mvc/
│   │   ├── AuthMvcController.java
│   │   ├── GameMvcController.java
│   │   └── HomeController.java
│   └── rest/
│       ├── AuthRestController.java
│       └── GameRestController.java
├── dto/
│   ├── Dto.java                      # Login/Register/Token records
│   └── GameDto.java                  # Java record
├── entity/
│   ├── Game.java
│   ├── RefreshToken.java
│   └── User.java                     # Implements UserDetails
├── enums/
│   ├── DifficultyTier.java           # 6 tiers including SOULSBORNE
│   ├── GameStatus.java               # 8 statuses including REPLAYING
│   ├── Genre.java                    # 23 genres
│   ├── PlayStyle.java                # 8 styles
│   ├── Platform.java                 # 16 platforms
│   └── Role.java
├── repository/
│   ├── GameRepository.java
│   ├── RefreshTokenRepository.java
│   └── UserRepository.java
├── security/
│   ├── JwtAuthFilter.java
│   ├── JwtService.java
│   └── UserDetailsServiceImpl.java
└── service/
    ├── AuthService.java
    ├── GameService.java
    └── RefreshTokenService.java
```
