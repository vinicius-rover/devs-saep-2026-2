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

        return converterEntityParaDto(usuarioEntityOptional.get());
    }

    public List<UsuarioDto> listarTodos() {
        List<UsuarioDto> usuarios = new ArrayList<>();

        for (UsuarioEntity usuarioEntity : usuarioRepository.findAll()) {
            usuarios.add(converterEntityParaDto(usuarioEntity));
        }

        return usuarios;
    }

    public UsuarioDto buscarPorId(Long id) {
        Optional<UsuarioEntity> usuarioEntity = usuarioRepository.findById(id);

        if (usuarioEntity.isEmpty()) {
            return null;
        }

        return converterEntityParaDto(usuarioEntity.get());
    }

    public UsuarioDto salvar(UsuarioDto usuarioDto) {
        UsuarioEntity usuarioEntity;

        if (usuarioDto.getId() != null) {
            Optional<UsuarioEntity> usuarioExistente = usuarioRepository.findById(usuarioDto.getId());

            if (usuarioExistente.isEmpty()) {
                return null;
            }

            usuarioEntity = usuarioExistente.get();
            usuarioEntity.setNome(usuarioDto.getNome());
            usuarioEntity.setEmail(usuarioDto.getEmail());
            // A senha nao e alterada pelo cadastro/edicao normal.
        } else {
            usuarioEntity = converterDtoParaEntity(usuarioDto);
        }

        usuarioEntity = usuarioRepository.save(usuarioEntity);
        return converterEntityParaDto(usuarioEntity);
    }

    public void excluir(Long id) {
        usuarioRepository.deleteById(id);
    }

    public boolean trocarSenha(String login, String senhaAtual, String senhaNova) {
        Optional<UsuarioEntity> usuarioEntityOptional = usuarioRepository.findByEmail(login);

        if (usuarioEntityOptional.isEmpty()) {
            return false;
        }

        UsuarioEntity usuarioEntity = usuarioEntityOptional.get();

        if (!usuarioEntity.getSenha().equals(senhaAtual)) {
            return false;
        }

        usuarioEntity.setSenha(senhaNova);
        usuarioRepository.save(usuarioEntity);
        return true;
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
