package org.kdr.blaster.repository.search;

import com.querydsl.jpa.JPQLQuery;
import lombok.extern.log4j.Log4j2;
import org.kdr.blaster.domain.Game;
import org.kdr.blaster.domain.QGame;
import org.kdr.blaster.domain.board.Category;
import org.kdr.blaster.domain.board.Post;
import org.kdr.blaster.domain.board.QPost;
import org.kdr.blaster.dto.PostListDTO;
import org.kdr.blaster.dto.PostPageRequestDTO;
import org.kdr.blaster.mapper.PostMapper;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import java.util.List;
import java.util.stream.Collectors;

@Log4j2
public class PostSearchImpl extends QuerydslRepositorySupport implements PostSearch {

    public PostSearchImpl() {
        super(Post.class);
    }

    @Override
    public Page<PostListDTO> getPosts(PostPageRequestDTO postPageRequestDTO) {

        QPost post = QPost.post;
        QGame game = QGame.game;

        JPQLQuery<Post> query = from(post);
        JPQLQuery<Game> queryGame = from(game);

        Game g = queryGame.where(game.name.eq(postPageRequestDTO.getGame())).fetchOne();

        if (g == null) {
            return new PageImpl<>(List.of(), PageRequest.of(1, 10), 0); // 해당 게임이 없는 경우 빈 페이지 반환
        }

        query.where(post.category.eq(Category.valueOf(postPageRequestDTO.getCategory()))
                .and(post.game.eq(g)));

        Pageable pageable = PageRequest.of(
                postPageRequestDTO.getPage() - 1,
                postPageRequestDTO.getSize(),
                Sort.by(
                        Sort.Order.desc(postPageRequestDTO.getSort()),
                        Sort.Order.desc("id")
                ).descending()
        );

        this.getQuerydsl().applyPagination(pageable, query);

        List<PostListDTO> posts = query.fetch().stream().map(PostMapper::toPostListDTO).toList();
        long totalCount = query.fetchCount();

        return new PageImpl<>(posts, pageable, totalCount);
    }
}
