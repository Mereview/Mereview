package com.ssafy.mereview.api.service.movie;

import com.ssafy.mereview.api.service.movie.dto.response.GenreResponse;
import com.ssafy.mereview.api.service.movie.dto.response.MovieResponse;
import com.ssafy.mereview.domain.movie.entity.Movie;
import com.ssafy.mereview.domain.movie.entity.MovieGenre;
import com.ssafy.mereview.domain.movie.repository.query.MovieGenreQueryRepository;
import com.ssafy.mereview.domain.movie.repository.query.MovieQueryRepository;
import com.ssafy.mereview.domain.movie.repository.command.MovieRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class MovieService {

    private final MovieQueryRepository movieQueryRepository;

    private final MovieGenreQueryRepository movieGenreQueryRepository;

    private final MovieRepository movieRepository;

    public List<MovieResponse> searchMovies(String keyword) {
        log.debug("keyword = {}", keyword);

        List<Movie> movies = movieQueryRepository.searchMovieByKeyword(keyword);

        return createMovieResponses(movies);

    }

    private List<MovieResponse> createMovieResponses(List<Movie> movies) {
        List<MovieResponse> movieResponses = new ArrayList<>();

        for(Movie movie : movies){
            List<MovieGenre> movieGenres = movieGenreQueryRepository.searchMovieGenreByMovieId(movie.getId());
            List<GenreResponse> genreResponses = movieGenres.stream().map(MovieGenre::getGenre).map(GenreResponse::of).collect(Collectors.toList());
            MovieResponse movieResponse = MovieResponse.of(movie, genreResponses); //
            movieResponses.add(movieResponse);
        }
        return movieResponses;
    }

    public MovieResponse searchMovieById(Long movieId) {
        Movie movie = movieRepository.findById(movieId).orElseThrow(IllegalArgumentException::new);
        List<MovieGenre> movieGenres = movieGenreQueryRepository.searchMovieGenreByMovieId(movieId);
        List<GenreResponse> genreResponses = movieGenres.stream().map(MovieGenre::getGenre).map(GenreResponse::of).collect(Collectors.toList());

        return MovieResponse.of(movie, genreResponses);
    }

}
