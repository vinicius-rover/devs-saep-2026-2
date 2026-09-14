package com.senai.template.services;

import com.senai.template.dtos.ProdutoDto;
import com.senai.template.entities.CategoriaEntity;
import com.senai.template.entities.ProdutoEntity;
import com.senai.template.repositories.CategoriaRepository;
import com.senai.template.repositories.ProdutoRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ProdutoService {
    private final ProdutoRepository produtoRepository;
    private final CategoriaRepository categoriaRepository;

    public ProdutoService(ProdutoRepository produtoRepository, CategoriaRepository categoriaRepository) {
        this.produtoRepository = produtoRepository;
        this.categoriaRepository = categoriaRepository;
    }

    public List<ProdutoDto> listarTodos() {
        List<ProdutoDto> produtos = new ArrayList<>();
        for (ProdutoEntity entity : produtoRepository.findAll()) {
            produtos.add(converterEntityParaDto(entity));
        }
        return produtos;
    }

    public ProdutoDto buscarPorId(Long id) {
        Optional<ProdutoEntity> produto = produtoRepository.findById(id);
        return produto.map(this::converterEntityParaDto).orElse(null);
    }

    public ProdutoDto salvar(ProdutoDto dto, Long usuarioId) {
        ProdutoEntity entity;
        if (dto.getId() != null) {
            Optional<ProdutoEntity> existente = produtoRepository.findById(dto.getId());
            if (existente.isEmpty()) return null;
            entity = existente.get();
        } else {
            entity = new ProdutoEntity();
        }

        entity.setNome(dto.getNome());
        entity.setPreco(dto.getPreco());
        entity.setEstoque(dto.getEstoque());
        entity.setDescricao(dto.getDescricao());
        entity.setAtivo(dto.getAtivo() == null ? true : dto.getAtivo());
        entity.setUsuarioId(usuarioId);

        if (dto.getCategoriaId() != null) {
            Optional<CategoriaEntity> categoria = categoriaRepository.findById(dto.getCategoriaId());
            if (categoria.isEmpty()) return null;
            entity.setCategoria(categoria.get());
        } else {
            entity.setCategoria(null);
        }

        return converterEntityParaDto(produtoRepository.save(entity));
    }

    public boolean excluir(Long id) {
        if (!produtoRepository.existsById(id)) return false;
        produtoRepository.deleteById(id);
        return true;
    }

    private ProdutoDto converterEntityParaDto(ProdutoEntity entity) {
        ProdutoDto dto = new ProdutoDto();
        dto.setId(entity.getId());
        dto.setNome(entity.getNome());
        dto.setPreco(entity.getPreco());
        dto.setEstoque(entity.getEstoque());
        dto.setDescricao(entity.getDescricao());
        dto.setAtivo(entity.getAtivo());
        dto.setUsuarioId(entity.getUsuarioId());
        if (entity.getCategoria() != null) {
            dto.setCategoriaId(entity.getCategoria().getId());
            dto.setCategoriaNome(entity.getCategoria().getNome());
        }
        return dto;
    }
}
