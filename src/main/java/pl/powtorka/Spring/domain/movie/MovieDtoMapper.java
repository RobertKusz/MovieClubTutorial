package pl.powtorka.Spring.domain.movie;

import pl.powtorka.Spring.domain.movie.dto.MovieDto;
import pl.powtorka.Spring.domain.rating.Rating;

class MovieDtoMapper {
    static MovieDto map(Movie movie) {
        double avgRating = movie.getRatings().stream()
                .map(Rating::getRating)
                .mapToDouble(value -> value)
                .average().orElse(0);
        int ratingCount = movie.getRatings().size();
        return new MovieDto(movie.getId(),
                movie.getTitle(),
                movie.getOriginalTitle(),
                movie.getShortDescription(),
                movie.getDescription(),
                movie.getYoutubeTrailerId(),
                movie.getReleaseYear(),
                movie.getGenre().getName(),
                movie.isPromoted(),
                movie.getPoster(),
                avgRating,
                ratingCount);
    }
}
