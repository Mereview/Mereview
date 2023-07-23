package com.ssafy.mereview.domain.movie.service.impl;

import com.ssafy.mereview.domain.movie.repository.MovieRepository;
import com.ssafy.mereview.domain.movie.service.MovieService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MovieServiceImpl implements MovieService {
    private final MovieRepository movieRepository;



}
