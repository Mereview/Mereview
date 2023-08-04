package com.ssafy.mereview.api.controller.movie;

import com.ssafy.mereview.api.service.movie.MovieService;
import com.ssafy.mereview.api.service.movie.dto.response.MovieResponse;
import com.ssafy.mereview.common.response.ApiResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/movies")
@Slf4j
public class MovieController {

    private final MovieService movieService;

    @PostMapping("/{keyword}")
    public ApiResponse<List<MovieResponse>> searchMovies(@PathVariable String keyword) {
        log.debug("keyword = {}", keyword);
        return ApiResponse.ok(movieService.searchMovies(keyword));
    }

}
