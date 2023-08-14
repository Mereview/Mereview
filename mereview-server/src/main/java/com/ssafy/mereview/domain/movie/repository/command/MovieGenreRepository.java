package com.ssafy.mereview.domain.movie.repository.command;

import com.ssafy.mereview.domain.movie.entity.MovieGenre;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MovieGenreRepository extends JpaRepository<MovieGenre, Long> {

}
