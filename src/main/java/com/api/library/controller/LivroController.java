package com.api.library.controller;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import javax.validation.Valid;
import com.api.library.model.Categoria;
import com.api.library.model.Livro;
import com.api.library.model.LivroPage;
import com.api.library.service.CategoriaService;
import com.api.library.service.LivroService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin
@RestController
@RequestMapping("/livros")
public class LivroController {

    @Autowired
    private LivroService livroService;

    @Autowired
    private CategoriaService categoriaService;

    @GetMapping
    public ResponseEntity<LivroPage> obterTodosOsLivros(@PageableDefault(size = 8) Pageable pageable,
                                                        @RequestParam(name = "q", required = false) String titulo) {
        Page<Livro> livros = livroService.obterTodos(pageable, titulo);

        if (livros.isEmpty())
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(new LivroPage(livros.getPageable().getPageNumber(),
                                                  livros.getPageable().getPageSize(),
                                                  livros.getTotalElements(),
                                                  livros.getTotalPages(),
                                                  livros.getContent()), HttpStatus.OK);
    }

    @GetMapping(value = "/search/{link}")
    public ResponseEntity<Livro> ObterLivroPorLink(@PathVariable("link") String link) {
        Optional<Livro> livroOptional = livroService.obterPorLink(link);

        if (livroOptional.isPresent())
            return new ResponseEntity<>(livroOptional.get(), HttpStatus.OK);
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<Livro> obterLivroPorId(@Valid @PathVariable("id") Long id) {
        Optional<Livro> livroOptional = livroService.obterPorId(id);

        if (livroOptional.isPresent())
            return new ResponseEntity<>(livroOptional.get(), HttpStatus.OK);
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/categorias/{link}")
    public ResponseEntity<LivroPage> obterLivrosPorCategoria(@PathVariable("link") String link,
                                                             @PageableDefault(size = 8) Pageable pageable) {
        Page<Livro> livros = livroService.obterPorCategoria(categoriaService.obterCategoria(link), pageable);

        if (livros.isEmpty())
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(new LivroPage(livros.getPageable().getPageNumber(),
                                                  livros.getPageable().getPageSize(),
                                                  livros.getTotalElements(),
                                                  livros.getTotalPages(),
                                                  livros.getContent()), HttpStatus.OK);
    }

    @GetMapping("/categorias")
    public ResponseEntity<List<Categoria>> obterTodasAsCategorias() {
        List<Categoria> categorias = categoriaService.obterTodas();

        if (categorias.isEmpty())
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(categorias, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Livro> salvarLivro(@Valid @RequestBody Livro livro) {
        livro.setDataCriacao(LocalDateTime.now());
        return new ResponseEntity<>(livroService.salvar(livro), HttpStatus.CREATED);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<Livro> atualizarLivro(@PathVariable("id") Long id, @RequestBody Livro livro) {
        Optional<Livro> livroOptional = livroService.obterPorId(id);

        if (livroOptional.isPresent()) {
            livro.setId(livroOptional.get().getId());
            livro.setDataAtualizacao(LocalDateTime.now());
            return new ResponseEntity<>(livroService.salvar(livro), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PatchMapping(value = "/{id}/desconto")
    public ResponseEntity<Livro> inserirDesconto(@PathVariable(name = "id") Long id,
                                                 @RequestParam(name = "novoPreco", required = true) BigDecimal novoPreco) {
        Optional<Livro> livroOptional = livroService.obterPorId(id);

        if(livroOptional.isPresent()) {
            Livro livro = livroOptional.get();
            livro.setPrecoAnterior(livro.getPreco());
            livro.setPreco(novoPreco);

            return new ResponseEntity<>(livroService.salvar(livro), HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Livro> deletarLivro(@PathVariable("id") Long id) {
        Optional<Livro> livroOptional = livroService.obterPorId(id);

        if (livroOptional.isPresent()) {
            livroService.excluir(livroOptional.get());
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
