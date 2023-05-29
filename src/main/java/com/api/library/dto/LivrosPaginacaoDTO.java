package com.api.library.dto;

import com.api.library.model.Livro;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.domain.Page;

import java.util.List;

@Getter @Setter
public class LivrosPaginacaoDTO {
    private int page;
    private int size;
    private long total_size;
    private int total_pages;
    
    private List<Livro> content;

    public LivrosPaginacaoDTO(Page<Livro> livros) {
        this.page = livros.getPageable().getPageNumber();
        this.size = livros.getPageable().getPageSize();
        this.total_size = livros.getTotalElements();
        this.total_pages = livros.getTotalPages();
        this.content = livros.getContent();
    }
}