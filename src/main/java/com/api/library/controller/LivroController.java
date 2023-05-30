package com.api.library.controller;

import com.api.library.dto.LivroRequestDTO;
import com.api.library.dto.LivrosPaginacaoDTO;
import com.api.library.model.Categoria;
import com.api.library.model.Livro;
import com.api.library.service.CategoriaService;
import com.api.library.service.LivroService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;
import java.util.List;


@Tag(name = "Livros", description = "Endpoins para recursos de livros")
@AllArgsConstructor
@CrossOrigin
@RestController
@RequestMapping("/livros")
public class LivroController {

    private final LivroService livroService;

    private final CategoriaService categoriaService;

    @Operation(summary = "Buscar todos os livros")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Livros existentes"),
            @ApiResponse(responseCode = "404", description = "Nenhum livro encontrado!")
    })
    @Cacheable(value = "livros", condition = "#filtro == null")
    @GetMapping
    public ResponseEntity<LivrosPaginacaoDTO> obterTodosOsLivros(@Parameter(name = "Paginação",
                                                                            description = "size=customizar quantidade de resultados, page=customizar quantidade de páginas")
                                                                 @PageableDefault(size = 8) Pageable pageable,
                                                                 @Parameter(name = "filtro", description = "Titulo para filtrar")
                                                                 @RequestParam(required = false) String filtro) {
        Page<Livro> livros = livroService.obterTodos(pageable, filtro);

        if (livros.isEmpty())
            return ResponseEntity.notFound().build();
        return ResponseEntity.ok(convertePaginacaoLivros(livros));
    }

    @Operation(summary = "Buscar livro por Id")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Livro encontrado"),
            @ApiResponse(responseCode = "404", description = "Nenhum livro encontrado!")
    })
    @GetMapping(value = "/{id}")
    public ResponseEntity<Livro> obterLivroPorId(
             @Parameter(description = "Id do livro a buscar")
             @PathVariable("id") Long id) {
        return ResponseEntity.ok(livroService.obterPorId(id));
    }

    @Operation(summary = "Buscar livro por link",
               description = "Link é o titulo resumido do livro sem pontuações e espaços")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Livro existente"),
            @ApiResponse(responseCode = "404", description = "Nenhum livro encontrado!")
    })
    @GetMapping(value = "/link/{link}")
    public ResponseEntity<Livro> ObterLivroPorLink(
            @Parameter(description = "Link do livro a buscar")
            @PathVariable("link") String link) {
        return ResponseEntity.ok(livroService.obterPorLink(link));
    }

    @Operation(summary ="Buscar todos os livros por link de categoria",
               description = "Link é o titulo resumido do livro sem pontuações e espaços")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Livros existentes"),
            @ApiResponse(responseCode = "404", description = "Nenhum livro encontrado!")
    })
    @GetMapping("/categorias/{link}")
    public ResponseEntity<LivrosPaginacaoDTO> obterLivrosPorCategoria(
              @Parameter(description = "Link da categoria a buscar livros")
              @PathVariable("link") String link,
              @PageableDefault(size = 8) Pageable pageable) {
        Categoria categoria = categoriaService.obterCategoria(link);
        Page<Livro> livros = livroService.obterPorCategoria(categoria, pageable);

        if (livros.isEmpty())
            return ResponseEntity.notFound().build();
        return ResponseEntity.ok(convertePaginacaoLivros(livros));
    }

    @Operation(summary = "Buscar todas as categorias")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Categorias existentes"),
            @ApiResponse(responseCode = "404", description = "Nenhuma categoria encontrada!")
    })
    @GetMapping("/categorias")
    public ResponseEntity<List<Categoria>> obterTodasAsCategorias() {
        List<Categoria> categorias = categoriaService.obterTodas();

        if (categorias.isEmpty())
            return ResponseEntity.notFound().build();
        return ResponseEntity.ok(categorias);
    }

    @Operation(summary = "Salvar um livro")
    @SecurityRequirement(name = "token-authotization")
    @CachePut(cacheNames = "livros", key = "#id")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Livro salvo"),
            @ApiResponse(responseCode = "400", description = "Erro ao informar dados de livro"),
            @ApiResponse(responseCode = "403", description = "Sem permissão")
    })
    @PostMapping
    public ResponseEntity<Livro> salvarLivro(
            @Valid @RequestBody
            @Parameter(name = "Livro a salvar", description = "Livro com dados a salvar", required = true) LivroRequestDTO livroRequest,
             UriComponentsBuilder uriBuilder) {
        Livro livroSalvo = livroService.salvar(livroRequest);
        var enderecoLivroSalvo = uriBuilder.path("/livros/{id}").buildAndExpand(livroSalvo.getId()).toUri();
        return ResponseEntity.created(enderecoLivroSalvo).body(livroSalvo);
    }

    @Operation(summary = "Atualizar um livro por Id")
    @SecurityRequirement(name = "token-authotization")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Livro atualizado"),
            @ApiResponse(responseCode = "404", description = "Nenhum livro encontrado!"),
            @ApiResponse(responseCode = "400", description = "Erro ao informar dados de livro"),
            @ApiResponse(responseCode = "403", description = "Sem permissão")
    })
    @CachePut(cacheNames = "livros", key = "#id")
    @PutMapping(value = "/{id}")
    public ResponseEntity<Livro> atualizarLivro(
            @Parameter(description = "Id do livro") @PathVariable("id") Long id,
            @Parameter(name = "Livro com alterações") @RequestBody LivroRequestDTO livroRequest) {
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

    @Operation(summary = "Deletar um livro por Id")
    @SecurityRequirement(name = "token-authotization")
    @DeleteMapping(value = "/{id}")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Livro removido"),
            @ApiResponse(responseCode = "404", description = "Nenhum livro encontrado!"),
            @ApiResponse(responseCode = "403", description = "Sem permissão")
    })
    @CachePut(cacheNames = "livros", condition = "#result.statusCodeValue == 204")
    public ResponseEntity<?> deletarLivro(
            @Parameter(description = "Id do livro a remover")
            @PathVariable("id") Long id) {
        livroService.excluir(id);
        return ResponseEntity.noContent().build();
    }

    private LivrosPaginacaoDTO convertePaginacaoLivros(Page<Livro> livros) {
        return new LivrosPaginacaoDTO(livros);
    }

}
