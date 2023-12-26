package pl.powtorka.Spring.domain.movie;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.data.domain.Pageable;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;
import pl.powtorka.Spring.domain.genre.Genre;
import pl.powtorka.Spring.domain.genre.GenreRepository;
import pl.powtorka.Spring.domain.movie.dto.MovieDto;
import pl.powtorka.Spring.domain.movie.dto.MovieSaveDto;
import pl.powtorka.Spring.storage.FileStorageService;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

class MovieServiceTest {
    @Mock MovieRepository movieRepository;

    @Mock GenreRepository genreRepository;
    @Mock FileStorageService fileStorageService;
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
        long invalidMovieId = 99L;
        when(movieRepository.findById(invalidMovieId)).thenReturn(Optional.empty());
        //when
        Optional<MovieDto> result = movieService.findMovieById(invalidMovieId);
        //then
        assertThat(result).isEmpty();
    }

    @Test
    void shouldFindMoviesByGenre(){
        //given
        Genre genre = mock(Genre.class);
        Movie movie1 = mock(Movie.class);
        when(movie1.getGenre()).thenReturn(genre);
        when(movie1.getTitle()).thenReturn("movie1Title");
        Movie movie2 = mock(Movie.class);
        when(movie2.getGenre()).thenReturn(genre);
        when(movie1.getTitle()).thenReturn("movie2Title");

        List<Movie> movies = List.of(movie1, movie2);

        when(genre.getName()).thenReturn("genreNameTest");
        when(movieRepository.findAllByGenre_NameIgnoreCase(genre.getName())).thenReturn(movies);
        //when
        List<MovieDto> moviesDto = movieService.findMoviesByGenreName(genre.getName());
        //then
        assertThat(moviesDto.get(0).getTitle()).isEqualTo(movie1.getTitle());
        assertThat(moviesDto.get(1).getTitle()).isEqualTo(movie2.getTitle());
        assertThat(movies.size()).isEqualTo(moviesDto.size());
    }

    @Test
    void shouldNotFindAnyMovieByGenre(){
        //given
        //when
        List<MovieDto> movies = movieService.findMoviesByGenreName("Test");
        //then
        assertThat(movies.size()).isEqualTo(0);
    }

    @Test
    void shouldAddMovie(){
        //given
        MovieSaveDto movieSaveDto = mock(MovieSaveDto.class);
        when(movieSaveDto.getTitle()).thenReturn("TitleTest");
        when(movieSaveDto.getPoster()).thenReturn(mock(MultipartFile.class));

        Genre genre = mock(Genre.class);
        when(genre.getName()).thenReturn("action");
        when(genreRepository.findByNameIgnoreCase(any())).thenReturn(Optional.of(genre));
        when(fileStorageService.saveImage(any())).thenReturn("PosterTest");

        //when
        movieService.addMovie(movieSaveDto);
        //then
        ArgumentCaptor<Movie> argumentCaptor = ArgumentCaptor.forClass(Movie.class);
        verify(movieRepository, times(1)).save(argumentCaptor.capture());

        Movie movie = argumentCaptor.getValue();
        assertThat(movie.getTitle()).isEqualTo(movieSaveDto.getTitle());
        assertThat(movie.getGenre().getName()).isEqualTo("action");
        assertThat(movie.getPoster()).isEqualTo("PosterTest");
    }

    @Test
    void shouldFindTopMovies(){
        //given
        Genre genre = mock(Genre.class);
        genre.setName("horror");
        Movie movie1 = mock(Movie.class);
        when(movie1.getGenre()).thenReturn(genre);
        Movie movie2 = mock(Movie.class);
        when(movie2.getGenre()).thenReturn(genre);
        List<Movie> movies = List.of(movie1, movie2);

        Pageable page = Pageable.ofSize(movies.size());
        when(movieRepository.findTopByRating(page)).thenReturn(movies);

        //when
        List<MovieDto> topMovies = movieService.findTopMovies(2);
        //then
        assertThat(topMovies.size()).isEqualTo(2);
        verify(movieRepository, times(1)).findTopByRating(page);
    }
}