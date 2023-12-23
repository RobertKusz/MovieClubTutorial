package pl.powtorka.Spring.web;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.server.ResponseStatusException;
import pl.powtorka.Spring.domain.movie.MovieService;
import pl.powtorka.Spring.domain.movie.dto.MovieDto;
import pl.powtorka.Spring.domain.rating.RatingService;

import java.util.Optional;

@Controller
public class MovieController {
    private final MovieService movieService;
    private final RatingService ratingService;

    public MovieController(MovieService movieService, RatingService ratingService) {
        this.movieService = movieService;
        this.ratingService = ratingService;
    }

    @GetMapping("/film/{id}")
    public String getMovie(@PathVariable long id, Model model, Authentication authentication){
        MovieDto movie = movieService.findMovieById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        model.addAttribute("movie", movie);
        if (authentication != null){
            String currentUserEmail = authentication.getName();
            Integer rating = ratingService.getUserRatingForMovie(currentUserEmail, id).orElse(0);
            model.addAttribute("userRating", rating);
        }

//        Optional<MovieDto> optionalMovie = movieService.findMovieById(id);
//        optionalMovie.ifPresent(movieDto -> model.addAttribute("movie", movieDto));
        return "movie";
    }
}
