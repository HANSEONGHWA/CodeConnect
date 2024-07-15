package com.example.codeconnect.repository;

import com.example.codeconnect.entity.*;
import com.example.codeconnect.post.DTO.PostResponseList;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
public class PostRepositoryCustomImpl implements PostRepositoryCustom {

    @Autowired
    private JPAQueryFactory queryFactory;

    /**
     * queryDsl 동적 쿼리
     * @param type
     * @param techStack
     * @param positions
     * @param pageable
     * @return PostResponseList DTO 객체, 페이징 정보, 전체 카운트
     */
    @Override
    public Page<PostResponseList> searchPosts(String type, String techStack, List<String> positions, Pageable pageable) {
        QPost post = QPost.post;
        QPostTechStack postTechStack = QPostTechStack.postTechStack;
        QPostPosition postPosition = QPostPosition.postPosition;

        BooleanBuilder builder = new BooleanBuilder();
        if (type != null && !type.isEmpty()) {
            builder.and(post.type.eq(type));
        }
        if (techStack != null && !techStack.isEmpty()) {
            builder.and(post.techStacks.any().techStacks.eq(techStack));
        }
        if (positions != null && !positions.isEmpty()) {
            positions.forEach(position -> builder.and(post.positions.any().positions.eq(position)));
        }

        List<Post> posts = queryFactory.selectFrom(post)
                .leftJoin(post.techStacks, postTechStack)
                .leftJoin(post.positions, postPosition)
                .where(builder)
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        List<PostResponseList> postResponseLists = posts.stream()
                .map(PostResponseList::fromEntity)
                .collect(Collectors.toList());

        long total = queryFactory.selectFrom(post)
                .leftJoin(post.techStacks, postTechStack)
                .leftJoin(post.positions, postPosition)
                .where(builder)
                .stream().count();

        return new PageImpl<>(postResponseLists, pageable, total);
    }
}
