package pl.powtorka.Spring.domain.web;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.server.ResponseStatusException;
import pl.powtorka.Spring.domain.movie.MovieService;
import pl.powtorka.Spring.domain.movie.dto.MovieDto;

import java.util.Optional;

@Controller
public class MovieController {
    private final MovieService movieService;

    public MovieController(MovieService movieService) {
        this.movieService = movieService;
    }

    @GetMapping("/film/{id}")
    public String getMovie(@PathVariable long id, Model model){
        movieService.findMovieById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        Optional<MovieDto> optionalMovie = movieService.findMovieById(id);
        optionalMovie.ifPresent(movieDto -> model.addAttribute("movie", movieDto));
        return "movie";
    }
}
