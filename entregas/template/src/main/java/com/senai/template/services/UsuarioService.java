package com.senai.template.services;

import com.senai.template.dtos.UsuarioDto;
import com.senai.template.entities.UsuarioEntity;
import com.senai.template.repositories.UsuarioRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;

    public UsuarioService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    public UsuarioDto autenticar(String email, String senha) {
        Optional<UsuarioEntity> usuarioEntityOptional = usuarioRepository.findByEmailAndSenha(email, senha);

        if (usuarioEntityOptional.isEmpty()) {
            return null;
        }

        UsuarioEntity usuarioEntity = usuarioEntityOptional.get();
        return converterEntityParaDto(usuarioEntity);
    }

    private UsuarioEntity converterDtoParaEntity(UsuarioDto usuarioDto) {
        UsuarioEntity usuarioEntity = new UsuarioEntity();

        usuarioEntity.setId(usuarioDto.getId());
        usuarioEntity.setNome(usuarioDto.getNome());
        usuarioEntity.setEmail(usuarioDto.getEmail());
        usuarioEntity.setSenha(usuarioDto.getSenha());

        return usuarioEntity;
    }

    private UsuarioDto converterEntityParaDto(UsuarioEntity usuarioEntity) {
        UsuarioDto usuarioDto = new UsuarioDto();

        usuarioDto.setId(usuarioEntity.getId());
        usuarioDto.setNome(usuarioEntity.getNome());
        usuarioDto.setEmail(usuarioEntity.getEmail());
        usuarioDto.setSenha(usuarioEntity.getSenha());

        return usuarioDto;
    }
}
