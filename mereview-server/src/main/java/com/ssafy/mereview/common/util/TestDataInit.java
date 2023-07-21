package com.ssafy.mereview.common.util;

import com.ssafy.mereview.domain.movie.entity.Genre;
import com.ssafy.mereview.domain.movie.repository.GenreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.List;

@Component
@RequiredArgsConstructor
public class TestDataInit {
    private final GenreRepository genreRepository;

    @PostConstruct
    public void init() {
        Genre genre1 = Genre.builder()
                .genreName("드라마")
                .genreId("1")
                .isUsing(true)
                .build();
        Genre genre2 = Genre.builder()
                .genreName("멜로")
                .genreId("2")
                .isUsing(true)
                .build();

        genreRepository.save(genre1);
        genreRepository.save(genre2);



    }
}
