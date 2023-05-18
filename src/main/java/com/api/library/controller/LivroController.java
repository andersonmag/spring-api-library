package com.api.library.controller;

import com.api.library.dto.LivroRequestDTO;
import com.api.library.dto.LivrosPaginacaoDTO;
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
    public ResponseEntity<LivrosPaginacaoDTO> obterTodosOsLivros(@PageableDefault(size = 8) Pageable pageable,
                                                                 @RequestParam(name = "q", required = false) String titulo) {
        Page<Livro> livros = livroService.obterTodos(pageable, titulo);

        if (livros.isEmpty())
            return ResponseEntity.notFound().build();
        return ResponseEntity.ok(convertePaginacaoLivros(livros));
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<Livro> obterLivroPorId(@Valid @PathVariable("id") Long id) {
        return ResponseEntity.ok(livroService.obterPorId(id));
    }

    @GetMapping(value = "/link/{link}")
    public ResponseEntity<Livro> ObterLivroPorLink(@PathVariable("link") String link) {
        return ResponseEntity.ok(livroService.obterPorLink(link));
    }

    @GetMapping("/categorias/{link}")
    public ResponseEntity<LivrosPaginacaoDTO> obterLivrosPorCategoria(@PathVariable("link") String link,
                                                                      @PageableDefault(size = 8) Pageable pageable) {
        Categoria categoria = categoriaService.obterCategoria(link);
        Page<Livro> livros = livroService.obterPorCategoria(categoria, pageable);

        if (livros.isEmpty())
            return ResponseEntity.notFound().build();
        return ResponseEntity.ok(convertePaginacaoLivros(livros));
    }

    @GetMapping("/categorias")
    public ResponseEntity<List<Categoria>> obterTodasAsCategorias() {
        List<Categoria> categorias = categoriaService.obterTodas();

        if (categorias.isEmpty())
            return ResponseEntity.notFound().build();
        return ResponseEntity.ok(categorias);
    }

    @CachePut(cacheNames = "livros", key = "#result.id")
    @PostMapping
    public ResponseEntity<Livro> salvarLivro(@Valid @RequestBody LivroRequestDTO livroRequest, UriComponentsBuilder uriBuilder) {
        Livro livroSalvo = livroService.salvar(livroRequest);
        UriComponents enderecoLivroSalvo = uriBuilder.path("/livros/{id}").buildAndExpand(livroSalvo.getId());
        return ResponseEntity.created(enderecoLivroSalvo.toUri()).body(livroSalvo);
    }

    @CachePut(cacheNames = "livros", key = "#id")
    @PutMapping(value = "/{id}")
    public ResponseEntity<Livro> atualizarLivro(@PathVariable("id") Long id, @RequestBody LivroRequestDTO livroRequest) {
        Livro livroAlterado = livroService.atualizar(id, livroRequest);
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
    @CachePut(cacheNames = "livros", condition = "result.statusCodeValue == 204")
    public ResponseEntity<?> deletarLivro(@PathVariable("id") Long id) {
        livroService.excluir(id);
        return ResponseEntity.noContent().build();
    }

    private LivrosPaginacaoDTO convertePaginacaoLivros(Page<Livro> livros) {
        return new LivrosPaginacaoDTO(livros.getPageable().getPageNumber(), livros.getPageable().getPageSize(),
                                        livros.getTotalElements(), livros.getTotalPages(), livros.getContent());
    }

}
