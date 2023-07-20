package com.ssafy.mereview.domain.movie.entity;

import com.ssafy.mereview.domain.member.entity.Interest;
import com.ssafy.mereview.domain.member.entity.UserTier;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
public class Genre {
    @Id
    private String genreId;
    private String genreName;

    @OneToMany(mappedBy = "genre")
    private List<UserTier> userTiers  = new ArrayList<>();

    @OneToMany(mappedBy = "genre")
    private List<Interest> interests = new ArrayList<>();

    private boolean isUsing;

    @Builder
    public Genre(String genreId, String genreName, boolean isUsing) {
        this.genreId = genreId;
        this.genreName = genreName;
    }
}
