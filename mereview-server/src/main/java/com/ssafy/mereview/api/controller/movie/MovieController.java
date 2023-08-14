package com.ssafy.mereview.api.controller.movie;

import com.ssafy.mereview.api.service.movie.MovieQueryService;
import com.ssafy.mereview.api.service.movie.MovieService;
import com.ssafy.mereview.api.service.movie.dto.response.MovieDetailResponse;
import com.ssafy.mereview.api.service.movie.dto.response.MovieResponse;
import com.ssafy.mereview.common.response.ApiResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/movies")
@Slf4j
@Api(tags = {"영화 관련 API"})
public class MovieController {

    private final MovieService movieService;
    private final MovieQueryService movieQueryService;

    @GetMapping
    @ApiOperation(value = "영화 키워드로 검색")
    public ApiResponse<List<MovieResponse>> searchMovies(@RequestParam(required = false) String keyword) {
        log.debug("keyword = {}", keyword);
        if(keyword==null){
            return ApiResponse.ok(new ArrayList<>());
        }

        return ApiResponse.ok(movieService.searchMovies(keyword));
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "영화 아이디로 상세조회")
    public ApiResponse<MovieResponse> searchMovieById(@PathVariable Long id) {
        return ApiResponse.ok(movieService.searchMovieById(id));
    }

    @GetMapping("detail/{movieId}")
    @ApiOperation(value = "영화 상세 검색")
    public ApiResponse<MovieDetailResponse> searchMovie(@PathVariable Long movieId){
        log.debug("movieId = {}", movieId);
        MovieDetailResponse response = movieQueryService.searchById(movieId);

        return ApiResponse.ok(response);
    }
}
