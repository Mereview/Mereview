package com.ssafy.mereview.domain.movie.repository;

import com.ssafy.mereview.domain.movie.entity.Genre;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GenreRepository extends JpaRepository<Genre, Long> {
    Genre findByGenreNumber(int genreNumber);
}
