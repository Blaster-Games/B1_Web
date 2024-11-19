package org.kdr.blaster.repository.search;

import org.kdr.blaster.dto.post.PostListDTO;
import org.kdr.blaster.dto.post.PostPageRequestDTO;
import org.springframework.data.domain.Page;

public interface PostSearch {
    Page<PostListDTO> getPosts(PostPageRequestDTO postPageRequestDTO);
}
