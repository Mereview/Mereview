package com.ssafy.mereview.api.service.movie;

import com.ssafy.mereview.api.service.movie.dto.response.MovieResponse;
import com.ssafy.mereview.domain.movie.entity.Movie;
import com.ssafy.mereview.domain.movie.repository.MovieQueryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class MovieService {

        private final MovieQueryRepository movieQueryRepository;

        public List<MovieResponse> searchMovies(String keyword) {
            log.debug("keyword = {}", keyword);
            return
            createMovieReponses(movieQueryRepository.searchMovieByKeyword(keyword));
        }

    private List<MovieResponse> createMovieReponses(List<Movie> movies) {
            log.debug("DB에서 가져온 movie = {}", movies);
            return movies.stream().map(MovieResponse::of).collect(Collectors.toList());
    }
}
