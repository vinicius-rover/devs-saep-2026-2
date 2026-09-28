package com.senai.template.controllers;

import com.senai.template.dtos.CategoriaDto;
import com.senai.template.dtos.UsuarioDto;
import com.senai.template.services.CategoriaService;
import com.senai.template.services.ProdutoService;
import com.senai.template.services.MovimentacaoService;
import com.senai.template.services.UsuarioService;
import com.senai.template.sessoes.SessaoDto;
import com.senai.template.sessoes.SessaoUtil;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class PageController {

	private final UsuarioService usuarioService;
	private final CategoriaService categoriaService;
	private final ProdutoService produtoService;
	private final MovimentacaoService movimentacaoService;

	public PageController(UsuarioService usuarioService,
						 CategoriaService categoriaService,
						 ProdutoService produtoService,
						 MovimentacaoService movimentacaoService) {
		this.usuarioService = usuarioService;
		this.categoriaService = categoriaService;
		this.produtoService = produtoService;
		this.movimentacaoService = movimentacaoService;
	}

	@GetMapping("/")
	public String index() {
		return "redirect:/login";
	}

	@GetMapping("/login")
	public String login() {
		return "login";
	}

	@GetMapping("/home")
	public String home(HttpSession session, Model model) {

		SessaoDto sessaoDto = SessaoUtil.ObterSessao(session);

		if (sessaoDto == null) {
			return "redirect:/login";
		}

		model.addAttribute("usuarioLogado", sessaoDto);
		model.addAttribute("isAdmin", sessaoDto.getUserRole() != null && sessaoDto.getUserRole() == 1);
		model.addAttribute("baixoEstoque", movimentacaoService.listarBaixoEstoque());

		return "home";
	}

	@GetMapping("/produtos")
	public String produtos(HttpSession session, Model model, @RequestParam(required = false) Long editar) {

		SessaoDto sessaoDto = SessaoUtil.ObterSessao(session);

		if (sessaoDto == null) {
			return "redirect:/login";
		}

		model.addAttribute("usuarioLogado", sessaoDto);
		model.addAttribute("produtos", produtoService.listarTodos());
		model.addAttribute("categorias", categoriaService.obterCategorias());
		model.addAttribute("isAdmin", sessaoDto.getUserRole() != null && sessaoDto.getUserRole() == 1);

		com.senai.template.dtos.ProdutoDto produtoForm = new com.senai.template.dtos.ProdutoDto();
		if (editar != null && sessaoDto.getUserRole() != null && sessaoDto.getUserRole() == 1) {
			com.senai.template.dtos.ProdutoDto encontrado = produtoService.buscarPorId(editar);
			if (encontrado != null) produtoForm = encontrado;
		}
		model.addAttribute("produtoForm", produtoForm);

		return "produtos";
	}

	@GetMapping("/usuarios")
	public String usuarios(HttpSession session,
						Model model,
						@RequestParam(required = false) Long editar) {

		SessaoDto sessaoDto = SessaoUtil.ObterSessao(session);

		if (sessaoDto == null) {
			return "redirect:/login";
		}

		model.addAttribute("usuarioLogado", sessaoDto);
		model.addAttribute("isAdmin", sessaoDto.getUserRole() != null && sessaoDto.getUserRole() == 1);
		model.addAttribute("usuarios", usuarioService.listarTodos());

		UsuarioDto usuarioForm = new UsuarioDto();
		if (editar != null && sessaoDto.getUserRole() != null && sessaoDto.getUserRole() == 1) {
			UsuarioDto usuarioEncontrado = usuarioService.buscarPorId(editar);
			if (usuarioEncontrado != null) {
				usuarioForm = usuarioEncontrado;
			}
		}

		model.addAttribute("usuarioForm", usuarioForm);

		return "usuarios";
	}

	@GetMapping("/estoque")
	public String estoque(HttpSession session, Model model) {

		SessaoDto sessaoDto = SessaoUtil.ObterSessao(session);

		if (sessaoDto == null) {
			return "redirect:/login";
		}

		model.addAttribute("usuarioLogado", sessaoDto);
		model.addAttribute("produtos", produtoService.listarTodos());
		model.addAttribute("estoque", movimentacaoService.listarEstoqueOrdenado());
		model.addAttribute("baixoEstoque", movimentacaoService.listarBaixoEstoque());
		model.addAttribute("movimentacoes", movimentacaoService.listarTodos());
		model.addAttribute("isAdmin", sessaoDto.getUserRole() != null && sessaoDto.getUserRole() == 1);

		return "estoque";
	}

	@GetMapping("/movimentacoes")
	public String movimentacoes(HttpSession session, Model model) {

		SessaoDto sessaoDto = SessaoUtil.ObterSessao(session);

		if (sessaoDto == null) {
			return "redirect:/login";
		}

		model.addAttribute("usuarioLogado", sessaoDto);
		model.addAttribute("movimentacoes", movimentacaoService.listarTodos());

		return "movimentacoes";
	}

	@GetMapping("/categorias")
	public String categorias(HttpSession session,
						 Model model,
						 @RequestParam(required = false) Long editar) {

		SessaoDto sessaoDto = SessaoUtil.ObterSessao(session);

		if (sessaoDto == null) {
			return "redirect:/login";
		}

		model.addAttribute("usuarioLogado", sessaoDto);
		model.addAttribute("isAdmin", sessaoDto.getUserRole() != null && sessaoDto.getUserRole() == 1);
		model.addAttribute("categorias", categoriaService.obterCategorias());

		CategoriaDto categoriaForm = new CategoriaDto();
		if (editar != null && sessaoDto.getUserRole() != null && sessaoDto.getUserRole() == 1) {
			CategoriaDto categoriaEncontrada = categoriaService.obterCategoriaPorId(editar);
			if (categoriaEncontrada != null) {
				categoriaForm = categoriaEncontrada;
			}
		}

		model.addAttribute("categoriaForm", categoriaForm);

		return "categorias";
	}

	@GetMapping("/categoria")
	public String categoriaLegada() {
		return "redirect:/categorias";
	}
}