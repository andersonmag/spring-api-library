package com.api.library.dto;

import com.api.library.model.Livro;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@Getter @Setter
public class LivroPage {
    private int page;
    private int size;
    private long total_size;
    private int total_pages;
    
    private List<Livro> content;
}