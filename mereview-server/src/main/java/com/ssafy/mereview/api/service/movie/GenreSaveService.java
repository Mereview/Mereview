package com.ssafy.mereview.api.service.movie;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ssafy.mereview.domain.movie.entity.Genre;
import com.ssafy.mereview.domain.movie.repository.command.GenreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

@Service
@Transactional
@RequiredArgsConstructor
public class GenreSaveService {
    private final GenreRepository genreRepository;

    private static final String API_KEY = "787f1e65a4a8fc1475399f09db832612";
    private static final String GENRE_API_URL = "https://api.themoviedb.org/3/genre/movie/list";

    public void saveDumpGenres() {

        try {
            String apiUrl = GENRE_API_URL + "?api_key=" + API_KEY +
                    "&language=ko";

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
                JsonNode genresNode = rootNode.get("genres");

                for (JsonNode genreNode : genresNode) {
                    int genreId = genreNode.get("id").asInt();
                    String name = genreNode.get("name").asText();

                    Genre genre = Genre.builder()
                            .genreNumber(genreId)
                            .genreName(name)
                            .build();

                    genreRepository.save(genre);
                    System.out.println("genre = " + genre.getId());
                }

            } catch (Exception e) {

            }
        } catch (Exception e) {

        }

        System.out.println("장르 저장이 성공했습니다aaaaaaaaaaa");
        
    }
    
    
    
}
