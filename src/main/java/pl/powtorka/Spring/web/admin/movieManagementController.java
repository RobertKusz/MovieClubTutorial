package pl.powtorka.Spring.web.admin;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import pl.powtorka.Spring.domain.genre.GenreService;
import pl.powtorka.Spring.domain.genre.dto.GenreDto;
import pl.powtorka.Spring.domain.movie.MovieService;
import pl.powtorka.Spring.domain.movie.dto.MovieSaveDto;

import java.util.List;

@Controller
public class movieManagementController {
    private final MovieService movieService;
    private final GenreService genreService;

    public movieManagementController(MovieService movieService, GenreService genreService) {
        this.movieService = movieService;
        this.genreService = genreService;
    }
    @GetMapping("/admin/dodaj-film")
    public String addMovieForm(Model model){
        List<GenreDto> allGenres = genreService.findAllGenres();
        model.addAttribute("genres", allGenres);
        MovieSaveDto movie = new MovieSaveDto();
        model.addAttribute("movie", movie);
        return "admin/movie-form";
    }
    @PostMapping("/admin/dodaj-film")
    public String addMovie(MovieSaveDto movie, RedirectAttributes redirectAttributes){
        movieService.addMovie(movie);
        redirectAttributes.addFlashAttribute(
                AdminController.NOTIFICATION_ATTRIBUTE,
                "Film %s został zapisany".formatted(movie.getTitle()));
        return "redirect:/admin";
    }

}
