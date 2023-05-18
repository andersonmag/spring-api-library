package com.api.library.controller;

import com.api.library.dto.LivroPage;
import com.api.library.model.Categoria;
import com.api.library.model.Livro;
import com.api.library.service.CategoriaService;
import com.api.library.service.LivroService;
import lombok.AllArgsConstructor;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;
import java.util.List;

@AllArgsConstructor
@CrossOrigin
@RestController
@RequestMapping("/livros")
public class LivroController {

    private final LivroService livroService;

    private final CategoriaService categoriaService;

    @Cacheable(value = "livros", condition = "#titulo != null")
    @GetMapping
    public ResponseEntity<LivroPage> obterTodosOsLivros(@PageableDefault(size = 8) Pageable pageable,
            @RequestParam(name = "q", required = false) String titulo) {
        Page<Livro> livros = livroService.obterTodos(pageable, titulo);

        if (livros.isEmpty())
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(getLivroPage(livros), HttpStatus.OK);
    }

    @GetMapping(value = "/by-link/{link}")
    public ResponseEntity<Livro> ObterLivroPorLink(@PathVariable("link") String link) {
        return new ResponseEntity<>(livroService.obterPorLink(link), HttpStatus.OK);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<Livro> obterLivroPorId(@Valid @PathVariable("id") Long id) {
        return new ResponseEntity<>(livroService.obterPorId(id), HttpStatus.OK);
    }

    @GetMapping("/categorias/{link}")
    public ResponseEntity<LivroPage> obterLivrosPorCategoria(@PathVariable("link") String link,
                                                             @PageableDefault(size = 8) Pageable pageable) {
        Categoria categoria = categoriaService.obterCategoria(link);
        Page<Livro> livros = livroService.obterPorCategoria(categoria, pageable);

        if (livros.isEmpty())
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(getLivroPage(livros), HttpStatus.OK);
    }

    @GetMapping("/categorias")
    public ResponseEntity<List<Categoria>> obterTodasAsCategorias() {
        List<Categoria> categorias = categoriaService.obterTodas();

        if (categorias.isEmpty())
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(categorias, HttpStatus.OK);
    }

    @CachePut(cacheNames = "livros", key = "#result.id")
    @PostMapping
    public ResponseEntity<Livro> salvarLivro(@Valid @RequestBody Livro livro, UriComponentsBuilder uriBuilder) {
        Livro livroSalvo = livroService.salvar(livro);
        UriComponents uriComponents = uriBuilder.path("/livros/{id}").buildAndExpand(livroSalvo.getId());
        return ResponseEntity.created(uriComponents.toUri()).body(livroSalvo);
    }

    @CachePut(cacheNames = "livros", key = "#id")
    @PutMapping(value = "/{id}")
    public ResponseEntity<Livro> atualizarLivro(@PathVariable("id") Long id, @RequestBody Livro livro) {
        Livro livroAlterado = livroService.atualizar(id, livro);
        return ResponseEntity.ok(livroAlterado);
    }

//    @PatchMapping(value = "/{id}/desconto")
//    public ResponseEntity<Livro> inserirDesconto(@PathVariable(name = "id") Long id,
//            @RequestParam(name = "novoPreco", required = true) BigDecimal novoPreco) {
//        Livro livro = livroService.obterPorId(id);
//
//        livro.setPrecoAnterior(livro.getPreco());
//        livro.setPreco(novoPreco);
//
//        return new ResponseEntity<>(livroService.salvar(livro), HttpStatus.OK);
//    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<?> deletarLivro(@PathVariable("id") Long id) {
        livroService.excluir(id);
        return ResponseEntity.noContent().build();
    }

    private LivroPage getLivroPage(Page<Livro> livros) {
        return new LivroPage(livros.getPageable().getPageNumber(), livros.getPageable().getPageSize(),
                             livros.getTotalElements(), livros.getTotalPages(), livros.getContent());
    }

}
