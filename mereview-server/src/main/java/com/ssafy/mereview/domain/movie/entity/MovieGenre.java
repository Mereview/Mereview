package com.ssafy.mereview.domain.movie.entity;

import com.ssafy.mereview.common.domain.BaseEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@Getter
@NoArgsConstructor
public class MovieGenre extends BaseEntity {

    @Id
    @GeneratedValue

}
