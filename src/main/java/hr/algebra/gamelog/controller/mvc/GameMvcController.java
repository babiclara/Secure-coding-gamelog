package hr.algebra.gamelog.controller.mvc;

import hr.algebra.gamelog.dto.GameDto;
import hr.algebra.gamelog.entity.User;
import hr.algebra.gamelog.enums.DifficultyTier;
import hr.algebra.gamelog.enums.GameStatus;
import hr.algebra.gamelog.enums.Genre;
import hr.algebra.gamelog.enums.PlayStyle;
import hr.algebra.gamelog.enums.Platform;
import hr.algebra.gamelog.service.GameService;
import jakarta.validation.Valid;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.NoSuchElementException;

@Controller
@RequestMapping("/games")
public class GameMvcController {

    private final GameService gameService;

    public GameMvcController(GameService gameService) {
        this.gameService = gameService;
    }

    @GetMapping
    public String list(
        @RequestParam(required = false) String query,
        @RequestParam(required = false) Genre genre,
        @RequestParam(required = false) Platform platform,
        @RequestParam(required = false) GameStatus status,
        @RequestParam(defaultValue = "false") boolean gotyOnly,
        Model model
    ) {
        boolean searching = query != null || genre != null || platform != null || status != null || gotyOnly;
        model.addAttribute("games", searching
            ? gameService.search(query, genre, platform, status, gotyOnly)
            : gameService.findAll());
        addEnumsToModel(model);
        model.addAttribute("query", query);
        model.addAttribute("selectedGenre", genre);
        model.addAttribute("selectedPlatform", platform);
        model.addAttribute("selectedStatus", status);
        model.addAttribute("gotyOnly", gotyOnly);
        return "games/list";
    }

    @GetMapping("/{id}")
    public String detail(@PathVariable Long id, Model model) {
        try {
            model.addAttribute("game", gameService.findById(id));
            return "games/detail";
        } catch (NoSuchElementException e) {
            return "redirect:/games";
        }
    }

    @GetMapping("/new")
    @PreAuthorize("hasRole('ADMIN')")
    public String newForm(Model model) {
        model.addAttribute("game", new GameDto(
            0L, "", "", "", null,
            null, null, null, null, null, null,
            0, 0, 0, 0,
            null, null, null, null, null, null,
            false, false, false, false,
            null, null, null, null,
            "", "", "", "", "", "",
            null, null, null
        ));
        addEnumsToModel(model);
        model.addAttribute("editMode", false);
        return "games/form";
    }

    @PostMapping("/new")
    @PreAuthorize("hasRole('ADMIN')")
    public String create(
        @Valid @ModelAttribute("game") GameDto dto,
        BindingResult result,
        @AuthenticationPrincipal User currentUser,
        Model model,
        RedirectAttributes redirectAttributes
    ) {
        if (result.hasErrors()) {
            addEnumsToModel(model);
            model.addAttribute("editMode", false);
            return "games/form";
        }
        gameService.create(dto, currentUser);
        redirectAttributes.addFlashAttribute("successMessage", "Game added to your library.");
        return "redirect:/games";
    }

    @GetMapping("/edit/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public String editForm(@PathVariable Long id, Model model) {
        try {
            model.addAttribute("game", gameService.findById(id));
            addEnumsToModel(model);
            model.addAttribute("editMode", true);
            return "games/form";
        } catch (NoSuchElementException e) {
            return "redirect:/games";
        }
    }

    @PostMapping("/edit/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public String update(
        @PathVariable Long id,
        @Valid @ModelAttribute("game") GameDto dto,
        BindingResult result,
        Model model,
        RedirectAttributes redirectAttributes
    ) {
        if (result.hasErrors()) {
            addEnumsToModel(model);
            model.addAttribute("editMode", true);
            return "games/form";
        }
        gameService.update(id, dto);
        redirectAttributes.addFlashAttribute("successMessage", "Game updated.");
        return "redirect:/games";
    }

    @PostMapping("/delete/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public String delete(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        gameService.delete(id);
        redirectAttributes.addFlashAttribute("successMessage", "Game removed.");
        return "redirect:/games";
    }

    private void addEnumsToModel(Model model) {
        model.addAttribute("genres", Genre.values());
        model.addAttribute("platforms", Platform.values());
        model.addAttribute("statuses", GameStatus.values());
        model.addAttribute("difficulties", DifficultyTier.values());
        model.addAttribute("playStyles", PlayStyle.values());
    }
}
