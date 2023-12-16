package pl.powtorka.Spring.domain.movie;

import pl.powtorka.Spring.domain.movie.dto.MovieDto;

class MovieDtoMapper {
    static MovieDto map(Movie movie) {
        return new MovieDto(movie.getId(),
                movie.getTitle(),
                movie.getOriginalTitle(),
                movie.getShortDescription(),
                movie.getDescription(),
                movie.getYoutubeTrailerId(),
                movie.getReleaseYear(),
                movie.getGenre().getName(),
                movie.isPromoted(),
                movie.getPoster()
        );
    }
}
