package org.kdr.blaster.repository.search;

import org.kdr.blaster.dto.PostListDTO;
import org.kdr.blaster.dto.PostPageRequestDTO;
import org.springframework.data.domain.Page;

public interface PostSearch {
    Page<PostListDTO> getPosts(PostPageRequestDTO postPageRequestDTO);
}
