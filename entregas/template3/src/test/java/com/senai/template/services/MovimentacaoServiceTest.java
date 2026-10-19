package com.senai.template.services;

import com.senai.template.entities.MovimentacaoEntity;
import com.senai.template.entities.ProdutoEntity;
import com.senai.template.entities.UsuarioEntity;
import com.senai.template.repositories.MovimentacaoRepository;
import com.senai.template.repositories.ProdutoRepository;
import com.senai.template.repositories.UsuarioRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/** Testes unitários sem Spring ou banco real; CTs do descritivo da Aula 10. */
class MovimentacaoServiceTest {
    private MovimentacaoRepository movimentacoes;
    private ProdutoRepository produtos;
    private UsuarioRepository usuarios;
    private MovimentacaoService service;
    private ProdutoEntity produto;
    private UsuarioEntity usuario;

    @BeforeEach
    void preparar() {
        movimentacoes = mock(MovimentacaoRepository.class);
        produtos = mock(ProdutoRepository.class);
        usuarios = mock(UsuarioRepository.class);
        service = new MovimentacaoService(movimentacoes, produtos, usuarios);
        produto = new ProdutoEntity();
        produto.setId(1L);
        produto.setNome("Alicate CT");
        produto.setEstoque(10);
        produto.setEstoqueMinimo(10);
        produto.setAtivo(true);
        usuario = new UsuarioEntity();
        usuario.setId(2L);
        usuario.setNome("Administrador CT");
    }

    private void prepararRegistro() {
        when(produtos.findById(1L)).thenReturn(Optional.of(produto));
        when(usuarios.findById(2L)).thenReturn(Optional.of(usuario));
    }

    @Test
    void ct27EntradaSomaSaldoERegistraHistorico() {
        prepararRegistro();
        assertNull(service.registrar(1L, "ENTRADA", 5, 2L));
        assertEquals(15, produto.getEstoque());
        verify(produtos).save(produto);
        ArgumentCaptor<MovimentacaoEntity> registro = ArgumentCaptor.forClass(MovimentacaoEntity.class);
        verify(movimentacoes).save(registro.capture());
        MovimentacaoEntity m = registro.getValue();
        assertAll(() -> assertSame(produto, m.getProduto()),
                () -> assertSame(usuario, m.getUsuario()),
                () -> assertEquals("ENTRADA", m.getTipo()),
                () -> assertEquals(5, m.getQuantidade()),
                () -> assertEquals(10, m.getEstoqueAnterior()),
                () -> assertEquals(15, m.getEstoqueAtual()),
                () -> assertNotNull(m.getData()));
    }

    @Test
    void ct28SaidaSubtraiSaldoERegistraHistorico() {
        produto.setEstoque(30);
        prepararRegistro();
        assertNull(service.registrar(1L, "SAIDA", 5, 2L));
        assertEquals(25, produto.getEstoque());
        verify(produtos).save(produto);
        ArgumentCaptor<MovimentacaoEntity> registro = ArgumentCaptor.forClass(MovimentacaoEntity.class);
        verify(movimentacoes).save(registro.capture());
        MovimentacaoEntity m = registro.getValue();
        assertAll(() -> assertEquals("SAIDA", m.getTipo()),
                () -> assertEquals(5, m.getQuantidade()),
                () -> assertEquals(30, m.getEstoqueAnterior()),
                () -> assertEquals(25, m.getEstoqueAtual()),
                () -> assertSame(usuario, m.getUsuario()));
    }

    @Test
    void ct29SaidaMaiorQueSaldoNaoPersisteAlteracoes() {
        produto.setEstoque(5);
        prepararRegistro();
        assertEquals("A saída não pode ser maior que o estoque atual.",
                service.registrar(1L, "SAIDA", 6, 2L));
        assertEquals(5, produto.getEstoque());
        verify(produtos, never()).save(any());
        verifyNoInteractions(movimentacoes);
    }

    @Test
    void ct31QuantidadeZeroNaoConsultaNemPersisteDados() {
        assertEquals("A quantidade deve ser maior que zero.",
                service.registrar(1L, "ENTRADA", 0, 2L));
        assertEquals(10, produto.getEstoque());
        verifyNoInteractions(produtos, usuarios, movimentacoes);
    }

    @Test
    void ct33ProdutoInativoNaoPodeSerMovimentado() {
        produto.setAtivo(false);
        prepararRegistro();
        assertEquals("Não é possível movimentar um produto inativo.",
                service.registrar(1L, "ENTRADA", 1, 2L));
        assertEquals(10, produto.getEstoque());
        verify(produtos, never()).save(any());
        verifyNoInteractions(movimentacoes);
    }

    @Test
    void ct36SaldoIgualAoMinimoIntegraListaDeAlerta() {
        when(produtos.findAll()).thenReturn(List.of(produto));
        assertEquals(List.of(produto), service.listarBaixoEstoque());
        verifyNoInteractions(movimentacoes, usuarios);
    }

    @Test
    void ct35SaldoAcimaDoMinimoNaoIntegraListaDeAlerta() {
        produto.setEstoque(11);
        when(produtos.findAll()).thenReturn(List.of(produto));
        assertTrue(service.listarBaixoEstoque().isEmpty());
        verifyNoInteractions(movimentacoes, usuarios);
    }
}
