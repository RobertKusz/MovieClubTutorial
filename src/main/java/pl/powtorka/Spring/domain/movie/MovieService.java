package pl.powtorka.Spring.domain.movie;

import org.springframework.stereotype.Service;
import pl.powtorka.Spring.domain.movie.dto.MovieDto;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

@Service
public class MovieService {
    private final MovieRepository movieRepository;

    public MovieService(MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
    }

    public List<MovieDto> findAllPromotedMovies(){
        return movieRepository.findAllByPromotedIsTrue().stream()
                .map(MovieDtoMapper::map)
                .toList();
    }


    public Optional<MovieDto> findMovieById(long id){
        return movieRepository.findById(id).map(MovieDtoMapper::map);
    }

    public List<MovieDto> findMoviesByGenreName(String genre){
        return movieRepository.findAllByGenre_NameIgnoreCase(genre)
                .stream()
                .map(MovieDtoMapper::map)
                .toList();
    }
}
