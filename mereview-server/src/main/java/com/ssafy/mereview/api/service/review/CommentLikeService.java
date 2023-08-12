package com.ssafy.mereview.api.service.review;

import com.ssafy.mereview.api.service.review.dto.request.CommentLikeServiceRequest;
import com.ssafy.mereview.api.service.review.dto.response.CommentLikeResponse;
import com.ssafy.mereview.domain.review.entity.CommentLike;
import com.ssafy.mereview.domain.review.entity.CommentLikeType;
import com.ssafy.mereview.domain.review.repository.query.CommentLikeQueryRepository;
import com.ssafy.mereview.domain.review.repository.command.CommentLikeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Optional;

import static com.ssafy.mereview.domain.review.entity.CommentLikeType.DISLIKE;
import static com.ssafy.mereview.domain.review.entity.CommentLikeType.LIKE;

@RequiredArgsConstructor
@Transactional
@Service
public class CommentLikeService {

    private final CommentLikeRepository likeRepository;
    private final CommentLikeQueryRepository LikeQueryRepository;

    public CommentLikeResponse updateCommentLike(CommentLikeServiceRequest request) {
        Optional<CommentLike> commentLike = LikeQueryRepository.searchByCommentAndMember(request.getCommentId(), request.getMemberId());

        boolean isDone = checkIsDone(request, commentLike);

        Map<CommentLikeType, Integer> likeCountsMap = LikeQueryRepository.getCountByCommentIdGroupByType(request.getCommentId());
        
        return createCommentLikeResponse(request, isDone, likeCountsMap);
    }

    /**
     *  private methods
     */

    private boolean checkIsDone(CommentLikeServiceRequest request, Optional<CommentLike> commentLike) {
        if (commentLike.isEmpty()) {
            likeRepository.save(request.toEntity());
            return true;
        }
        CommentLike like = commentLike.orElseThrow(NoSuchElementException::new);
        if (like.getType().equals(request.getType())) {
            likeRepository.delete(like);
            return false;
        }
        throw new DuplicateKeyException("한번에 하나만 할 수 있습니다.");
    }

    private CommentLikeResponse createCommentLikeResponse(CommentLikeServiceRequest request, boolean isDone, Map<CommentLikeType, Integer> likeCountsMap) {
        return CommentLikeResponse.builder()
                .commentLikeType(request.getType())
                .isDone(isDone)
                .likeCount(likeCountsMap.getOrDefault(LIKE, 0))
                .dislikeCount(likeCountsMap.getOrDefault(DISLIKE, 0))
                .build();
    }

}
