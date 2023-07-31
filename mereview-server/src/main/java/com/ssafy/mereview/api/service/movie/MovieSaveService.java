package com.ssafy.mereview.api.service.movie;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ssafy.mereview.domain.movie.entity.Movie;
import com.ssafy.mereview.domain.movie.entity.MovieGenre;
import com.ssafy.mereview.domain.movie.repository.GenreRepository;
import com.ssafy.mereview.domain.movie.repository.MovieGenreRepository;
import com.ssafy.mereview.domain.movie.repository.MovieRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class MovieSaveService {
    private final MovieRepository movieRepository;
    private final GenreRepository genreRepository;
    private final MovieGenreRepository movieGenreRepository;
    private final EntityManager em;

    private static final String API_KEY = "787f1e65a4a8fc1475399f09db832612";
    private static final String BASE_API_URL = "https://api.themoviedb.org/3/discover/movie";

    private static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    private static LocalDate currentDay = LocalDate.of(2021, 2, 7);

    private static String currentDayFormatted = currentDay.format(formatter);
    private static String nextDayFormatted = currentDay.plusDays(1).format(formatter);

    private void saveMovies(int page) {
        String apiUrl = BASE_API_URL + "?api_key=" + API_KEY +
                "&language=ko" +
                "&sort_by=primary_release_date.asc" +
                "&primary_release_date.gte=" + currentDayFormatted +
                "&primary_release_date.lte=" + currentDayFormatted +
                "&page=" + page;

        try{
            URL url = new URL(apiUrl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");

            try (BufferedReader bf = new BufferedReader(new InputStreamReader(conn.getInputStream()))) {
                StringBuilder response = new StringBuilder();
                String line;
                while ((line = bf.readLine()) != null) {
                    response.append(line);
                }

                ObjectMapper mapper = new ObjectMapper();
                JsonNode rootNode = mapper.readTree(response.toString());
                JsonNode resultsNode = rootNode.get("results");

                List<Movie> movies = new ArrayList<>();

                for (JsonNode movieNode : resultsNode) {
                    int movieContentId = movieNode.has("id") ? movieNode.get("id").asInt() : 0;
                    String movieTitle = movieNode.has("title") ? movieNode.get("title").asText() : "";
                    String overview = movieNode.has("overview") ? movieNode.get("overview").asText() : "";
                    String posterImg = movieNode.has("poster_path") ? movieNode.get("poster_path").asText() : "";
                    Double voteAverage = movieNode.has("vote_average") ? movieNode.get("vote_average").asDouble() : 100;
                    String releaseDateFromApi = movieNode.has("release_date") ? movieNode.get("release_date").asText() : "";

                    Movie movie = Movie.builder()
                            .movieContentId(movieContentId)
                            .title(movieTitle)
                            .overview(overview)
                            .voteAverage(voteAverage)
                            .posterImg(posterImg)
                            .releaseDate(releaseDateFromApi)
                            .build();

                    movieRepository.save(movie);
//                    em.persist(movie);
//                    em.flush();
//                    em.clear();



//                    JsonNode genresNode  = movieNode.has("genre_ids") ? movieNode.get("genre_ids") : JsonNodeFactory.instance.nullNode();;
                    JsonNode genresNode  = movieNode.get("genre_ids");
                    for(JsonNode genreNode : genresNode){
                        int genreNumber = genreNode.asInt();

                        MovieGenre movieGenre = MovieGenre.builder()
                                .movie(movie)
                                .genre(genreRepository.findByGenreNumber(genreNumber))
                                .build();

                        movieGenreRepository.save(movieGenre);
//                        em.persist(movieGenre);
//                        em.flush();
//                        em.clear();
                    }

                }

            }catch(Exception e){

            }

        }catch(Exception e){

        }


    }

    //전체 페이지 받아오는 부분
    private int getTotalPages() {
        int totalPages = 0;
        currentDayFormatted = currentDay.format(formatter);
        nextDayFormatted = currentDay.plusDays(1).format(formatter);

        String apiUrl = BASE_API_URL + "?api_key=" + API_KEY +
                "&language=ko" +
                "&sort_by=primary_release_date.asc" +
                "&primary_release_date.gte=" + currentDayFormatted +
                "&primary_release_date.lte=" + currentDayFormatted +
                "&page=1";

        //url 연결
        try {
            URL url = new URL(apiUrl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");

            try (BufferedReader bf = new BufferedReader(new InputStreamReader(conn.getInputStream()))) {
                StringBuilder response = new StringBuilder();
                String line;

                while ((line = bf.readLine()) != null) {
                    response.append(line);
                }

                ObjectMapper mapper = new ObjectMapper();
                JsonNode rootNode = mapper.readTree(response.toString());
                totalPages = rootNode.get("total_pages").asInt();

            } catch (Exception e) {

            }
        } catch (Exception e) {
            System.out.println("url 연결 에러");
        }

        return totalPages;
    }

    public void saveDumpMovies() {
        while (currentDay.isBefore(LocalDate.now())) {

            int totalPages = getTotalPages();
            for (int page = 1; page <= totalPages; ++page) {
                saveMovies(page);
            }
            currentDay = currentDay.plusDays(1);
        }
    }
}
