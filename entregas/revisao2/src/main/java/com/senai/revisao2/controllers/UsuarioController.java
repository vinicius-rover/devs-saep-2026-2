package com.senai.revisao2.controllers;

import com.senai.revisao2.dtos.*;
import com.senai.revisao2.services.UsuarioService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class UsuarioController {

    private final UsuarioService service;

    public UsuarioController(UsuarioService service) {
        this.service = service;
    }

    @PostMapping("/login")
    public String realizarLogin(String email, String senha,
                                Model model, RedirectAttributes redirectAttributes){

        System.out.println("email=" + email + " senha=" + senha);

        UsuarioDto usuarioDto = new UsuarioDto();
        usuarioDto.setEmail(email);
        usuarioDto.setSenha(senha);

        boolean retorno = service.realizarLogin(usuarioDto);

        if (retorno) {

            redirectAttributes.addFlashAttribute("usuario", " Bem-vindo João!");

            return "redirect:/home";
        }

        model.addAttribute("erro","E-mail ou senha invalidos.");

        return "login";

    }

}
