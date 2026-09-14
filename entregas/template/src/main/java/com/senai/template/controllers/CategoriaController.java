package com.senai.template.controllers;

import com.senai.template.dtos.CategoriaDto;
import com.senai.template.services.CategoriaService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class CategoriaController {

    private final CategoriaService categoriaService;

    public CategoriaController(CategoriaService categoriaService) {
        this.categoriaService = categoriaService;
    }

    @GetMapping("/categorias")
    public List<CategoriaDto> categorias() {
        return categoriaService.obterCategorias();
    }

    @GetMapping("/{id}")
    public CategoriaDto obterCategoria(@PathVariable Long id) {
        return categoriaService.obterCategoriaPorId(id);
    }

    @PostMapping("/categoria")
    public CategoriaDto cadastrarCategoria(@RequestBody CategoriaDto categoriaDto) {
        return categoriaService.cadastrarCategoria(categoriaDto);
    }

    @PutMapping("/{id}")
    public CategoriaDto atualizarCategoria(
            @PathVariable Long id,
            @RequestBody CategoriaDto categoriaDto) {

        return categoriaService.atualizarCategoria(id, categoriaDto);
    }

    @DeleteMapping("/{id}")
    public boolean excluirCategoria(@PathVariable Long id) {
        return categoriaService.excluirCategoria(id);
    }
}
