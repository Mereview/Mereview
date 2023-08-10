package com.ssafy.mereview.api.service.movie.dto.response;

import com.ssafy.mereview.domain.movie.entity.Movie;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import java.util.List;

@Getter
@NoArgsConstructor
public class MovieResponse {


    private Long id;


    private String title;

    private String overview;

    private Double voteAverage;

    private String posterImg;

    private String releaseDate;

    private List<GenreResponse> genres;

    @Builder
    public MovieResponse(Long id, int movieContentId, String title, String overview, Double voteAverage, String posterImg, String releaseDate, List<GenreResponse> genres) {
        this.id = id;
        this.title = title;
        this.overview = overview;
        this.voteAverage = voteAverage;
        this.posterImg = posterImg;
        this.releaseDate = releaseDate;
        this.genres = genres;
    }

    public static MovieResponse of(Movie movie){
        return MovieResponse.builder()
                .id(movie.getId())
                .title(movie.getTitle())
                .overview(movie.getOverview())
                .voteAverage(movie.getVoteAverage())
                .posterImg(movie.getPosterImg())
                .releaseDate(movie.getReleaseDate())
                .build();
    }


    public static MovieResponse of(Movie movie, List<GenreResponse> genreResponses){
        if (genreResponses.size() == 0){
            genreResponses.add(GenreResponse.builder().genreId(0L).genreName("없음").build());
        }
        return MovieResponse.builder()
                .id(movie.getId())
                .title(movie.getTitle())
                .genres(genreResponses)
                .overview(movie.getOverview())
                .voteAverage(movie.getVoteAverage())
                .posterImg(movie.getPosterImg())
                .releaseDate(movie.getReleaseDate())
                .build();
    }

}
