package com.senai.template.controllers;

import com.senai.template.dtos.CategoriaDto;
import com.senai.template.dtos.UsuarioDto;
import com.senai.template.services.CategoriaService;
import com.senai.template.services.ProdutoService;
import com.senai.template.services.UsuarioService;
import com.senai.template.sessoes.SessaoDto;
import com.senai.template.sessoes.SessaoUtil;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class PageController {

	private final UsuarioService usuarioService;
	private final CategoriaService categoriaService;
	private final ProdutoService produtoService;

	public PageController(UsuarioService usuarioService, CategoriaService categoriaService, ProdutoService produtoService) {
		this.usuarioService = usuarioService;
		this.categoriaService = categoriaService;
		this.produtoService = produtoService;
	}

	@GetMapping("/")
	public String index() {
		return "redirect:/login";
	}

	@GetMapping("/login")
	public String login() {
		return "login";
	}

	@GetMapping("/logout")
	public String logout(HttpSession session) {
		SessaoUtil.RemoverSessao(session);
		return "redirect:/login";
	}

	@PostMapping("/login")
	public String realizarLogin(@RequestParam String email,
								@RequestParam String senha,
								Model model,
								RedirectAttributes redirectAttributes,
								HttpSession session) {

		UsuarioDto usuarioDto = usuarioService.autenticar(email, senha);

		if (usuarioDto == null) {
			model.addAttribute("erro", "E-mail ou senha invalidos.");
			model.addAttribute("email", email);
			return "login";
		}

		SessaoDto sessaoDto = new SessaoDto();
		sessaoDto.setUsuarioId(usuarioDto.getId());
		sessaoDto.setUsuarioNome(usuarioDto.getNome());
		sessaoDto.setUserRole(usuarioDto.getUserRole());

		SessaoUtil.RegistrarSessao(session, sessaoDto);

		System.out.println("Sessão: ");
		System.out.println(sessaoDto.getUsuarioId());
		System.out.println(sessaoDto.getUsuarioNome());

		redirectAttributes.addFlashAttribute(
				"mensagem",
				"Bem-vindo, " + usuarioDto.getNome() + "."
		);

		return "redirect:/home";
	}

	@GetMapping("/home")
	public String home(HttpSession session, Model model) {

		SessaoDto sessaoDto = SessaoUtil.ObterSessao(session);

		if (sessaoDto == null) {
			return "redirect:/login";
		}

		model.addAttribute("usuarioLogado", sessaoDto);

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
		if (editar != null) {
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

		return "estoque";
	}

	@GetMapping("/movimentacoes")
	public String movimentacoes(HttpSession session, Model model) {

		SessaoDto sessaoDto = SessaoUtil.ObterSessao(session);

		if (sessaoDto == null) {
			return "redirect:/login";
		}

		model.addAttribute("usuarioLogado", sessaoDto);

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
		model.addAttribute("categorias", categoriaService.obterCategorias());

		CategoriaDto categoriaForm = new CategoriaDto();
		if (editar != null) {
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