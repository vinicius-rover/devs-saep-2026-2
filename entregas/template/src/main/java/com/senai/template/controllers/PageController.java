package com.senai.template.controllers;

import com.senai.template.dtos.UsuarioDto;
import com.senai.template.services.UsuarioService;
import com.senai.template.sessoes.SessaoDto;
import com.senai.template.sessoes.SessaoUtil;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class PageController {

	private final UsuarioService usuarioService;

	public PageController(UsuarioService usuarioService) {
		this.usuarioService = usuarioService;
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
		SessaoUtil.RegistrarSessao(session, sessaoDto);

		System.out.println("Sessão: ");
		System.out.println(sessaoDto.getUsuarioId());
		System.out.println(sessaoDto.getUsuarioNome());

		redirectAttributes.addFlashAttribute("mensagem", "Bem-vindo, " + usuarioDto.getNome() + ".");
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


}
