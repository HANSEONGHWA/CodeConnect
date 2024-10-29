package com.example.codeconnect.repository;

import com.example.codeconnect.entity.Post;
import com.example.codeconnect.entity.QPost;
import com.example.codeconnect.entity.QPostPosition;
import com.example.codeconnect.entity.QPostTechStack;
import com.example.codeconnect.post.DTO.PostResponseList;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@AllArgsConstructor
public class PostRepositoryCustomImpl implements PostRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    /**
     * queryDsl 동적 쿼리
     *
     * @param type
     * @param techStack
     * @param position
     * @param pageable
     * @return PostResponseList DTO 객체, 페이징 정보, 전체 카운트
     */
    @Override
    //String type, techStack, position, Pageable 받음.
    public Page<PostResponseList> searchPosts(String type, List<String> techStack, List<String> position, Pageable pageable) {
        //QClass 사용.
        QPost post = QPost.post;

        //BooleanBuilder 동적 쿼리 생성
        BooleanBuilder builder = new BooleanBuilder();

        //각 파라미터가 null이 아니고 빈 값이 아닐 때마다 BooleanBuilder에 조건 추가
        if (type != null && !type.isEmpty()) {
            builder.and(post.type.eq(type));
        }
        if (techStack != null && !techStack.isEmpty()) {
            techStack.forEach(techStacks -> builder.and(post.techStacks.any().techStacks.eq(techStacks)));
        }
        if (position != null && !position.isEmpty()) {
            position.forEach(positions -> builder.and(post.positions.any().positions.eq(positions)));
        }

        //queryFactory 사용하여 Post 엔티티 조회쿼리 실행
        List<Post> posts = queryFactory.selectFrom(post)
                .where(builder)
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        List<PostResponseList> postResponseLists = posts.stream()
                .map(PostResponseList::fromEntity)
                .collect(Collectors.toList());

        //총 데이터 개수
        long total = queryFactory.selectFrom(post)
                .where(builder)
                .fetch().size();

        return new PageImpl<>(postResponseLists, pageable, total);
    }
}
