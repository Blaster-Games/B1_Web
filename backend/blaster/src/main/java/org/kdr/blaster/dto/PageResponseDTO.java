package org.kdr.blaster.dto;

import lombok.Data;
import org.kdr.blaster.dto.post.PostPageRequestDTO;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.stream.IntStream;

@Data
public class PageResponseDTO<E> {

    private List<E> itemList;

    private List<Integer> pageNumList;

    private int current, size, prevPage, nextPage, totalCount, totalPages;

    private boolean prev, next;

    public PageResponseDTO(Page<E> page) {
        this.itemList = page.getContent();
        this.current = page.getNumber() + 1;
        this.size = page.getSize();
        this.totalCount = (int) page.getTotalElements();
        this.totalPages = page.getTotalPages();
        int start = current - (page.getNumber() % size);
        int end = Integer.min(start + 9, totalPages);
        this.pageNumList = IntStream.rangeClosed(start, end).boxed().toList();
        this.prev = start > 1;
        this.next = end < totalPages;
        this.prevPage = prev ? start - 1 : 0;
        this.nextPage = next ? end + 1 : 0;
    }

    public PageResponseDTO(List<E> dtoList, PostPageRequestDTO postPageRequestDTO, long total) {
        this.itemList = dtoList;
        this.current = postPageRequestDTO.getPage();
        this.size = postPageRequestDTO.getSize();
        this.totalCount = (int) total;
        this.totalPages = totalCount % size > 0 ? totalCount / size + 1 : totalCount / size;
        int start = (current - 1) / size * size + 1;
        int end = Integer.min(start + 9, totalPages);
        this.pageNumList = IntStream.rangeClosed(start, end).boxed().toList();
        this.prev = start > 1;
        this.next = end < totalPages;
        this.prevPage = prev ? start - 1 : 0;
        this.nextPage = next ? end + 1 : 0;
    }
}
