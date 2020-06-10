package com.api.library.model;

import java.util.List;

public class LivroPage {

    private int page;
    private int size;
    private long total_size;
    private int total_pages;
    
    private List<Livro> content;

    public List<Livro> getContent() {
        return content;
    }

    public void setContent(List<Livro> content) {
        this.content = content;
    }

    public int getTotal_pages() {
        return total_pages;
    }

    public void setTotal_pages(int total_pages) {
        this.total_pages = total_pages;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public long getTotal_size() {
        return total_size;
    }

    public void setTotal_size(long total_size) {
        this.total_size = total_size;
    }

    public LivroPage(int page, int size, long total_size, int total_pages, List<Livro> content) {
        this.page = page;
        this.size = size;
        this.total_size = total_size;
        this.total_pages = total_pages;
        this.content = content;
    }
}