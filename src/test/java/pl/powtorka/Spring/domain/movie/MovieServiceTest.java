package pl.powtorka.Spring.domain.movie;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import pl.powtorka.Spring.domain.genre.Genre;
import pl.powtorka.Spring.domain.movie.dto.MovieDto;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

class MovieServiceTest {
    @Mock MovieRepository movieRepository;
    @InjectMocks MovieService movieService;

    @BeforeEach
    void init(){
        MockitoAnnotations.openMocks(this);
    }
    @Test
    void shouldFindAllPromotedMovies(){
        //given
        Genre genre = mock(Genre.class);
        Movie movie1 = mock(Movie.class);
        when(movie1.getGenre()).thenReturn(genre);
        Movie movie2 = mock(Movie.class);
        when(movie2.getGenre()).thenReturn(genre);
        Movie movie3 = mock(Movie.class);
        when(movie3.getGenre()).thenReturn(genre);

        List<Movie> movies = List.of(movie1, movie2, movie3);
        when(movieRepository.findAllByPromotedIsTrue()).thenReturn(movies);

        //when
        List<MovieDto> promotedMovies = movieService.findAllPromotedMovies();
        //then
        assertThat(promotedMovies.size()).isEqualTo(3);


    }


    @Test
    void shouldFindMovieById(){
        //given
        Long movieId = 1L;
        Movie movie = mock(Movie.class);
        Genre genre = mock(Genre.class);
        when(genre.getName()).thenReturn("genreNameTest");

        when(movie.getId()).thenReturn(movieId);

        when(movie.getDescription()).thenReturn("testDesctiption");
        //asg
        when(movie.getGenre()).thenReturn(genre);

        when(movieRepository.findById(1L)).thenReturn(Optional.of(movie));
        //when
        MovieDto movieDto = movieService.findMovieById(1L).get();

        //then
        assertThat(movie.getId()).isEqualTo(movieDto.getId());
        assertThat(movie.getDescription()).isEqualTo(movieDto.getDescription());
        assertThat(movie.getGenre().getName()).isEqualTo(genre.getName());
    }

    @Test
    void shouldNotFindMovieById(){
        //given
        Long invalidMovieId = 99L;
        when(movieRepository.findById(invalidMovieId)).thenReturn(Optional.empty());
        //when
        Optional<MovieDto> result = movieService.findMovieById(invalidMovieId);
        //then
        assertThat(result).isEmpty();
    }
}