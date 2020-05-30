package com.api.library.controller;

import java.util.List;
import java.util.Optional;
import com.api.library.model.Categoria;
import com.api.library.model.Livro;
import com.api.library.repository.CategoriaRepository;
import com.api.library.repository.LivroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin
@RestController
@RequestMapping("/livros")
public class LivroController {

    @Autowired
    private CategoriaRepository categoriaRepository;

    @Autowired
    private LivroRepository livroRepository;

    @GetMapping
    public ResponseEntity<List<Livro>> obterTodosOsLivros() {
        List<Livro> livros = livroRepository.findAll();

        if (livros.isEmpty())
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(livros, HttpStatus.OK);
    }

    @GetMapping(value = "/search/{link}")
    public ResponseEntity<List<Livro>> ObterLivroPorLink(@PathVariable("link") String link) {
        List<Livro> livros = livroRepository.findByLink(link);

        if (livros.isEmpty())
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(livros, HttpStatus.OK);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<Livro> obterLivroPorId(@PathVariable("id") Long id) {
        Optional<Livro> livroOptional = livroRepository.findById(id);

        if (livroOptional.isPresent())
            return new ResponseEntity<>(livroOptional.get(), HttpStatus.OK);
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/categorias/{id}")
    public ResponseEntity<List<Livro>> obterLivrosPorCategoria(@PathVariable("id") Long id) {
        Optional<Categoria> categoriaOptional = categoriaRepository.findById(id);

        if (categoriaOptional.isPresent()) {
            List<Livro> livros = livroRepository.findByCategoria(categoriaOptional.get());

            if (livros.isEmpty())
                return ResponseEntity.notFound().build();
            return new ResponseEntity<List<Livro>>(livros, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/categorias")
    public ResponseEntity<List<Categoria>> obterTodasAsCategorias() {
        List<Categoria> categorias = categoriaRepository.findAll();

        if (categorias.isEmpty())
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(categorias, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Livro> salvarLivro(@RequestBody Livro livro) {
        return new ResponseEntity<>(livroRepository.save(livro), HttpStatus.CREATED);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<Livro> atualizarLivro(@PathVariable("id") Long id, @RequestBody Livro livro) {
        Optional<Livro> livroOptional = livroRepository.findById(id);

        if (livroOptional.isPresent()) {
            livro.setId(livroOptional.get().getId());
            return new ResponseEntity<>(livroRepository.save(livro), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Livro> deletarLivro(@PathVariable("id") Long id) {
        Optional<Livro> livroOptional = livroRepository.findById(id);

        if (livroOptional.isPresent()) {
            livroRepository.delete(livroOptional.get());
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

}