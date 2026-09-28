# Spring Security — Projeto de Estoque

A segurança foi adaptada ao projeto existente sem trocar a estrutura de usuários.

## Perfis

- `user_role = 0` → `ROLE_USER`: pode consultar as páginas e dados.
- `user_role = 1` → `ROLE_ADMIN`: pode consultar e realizar alterações.

## Login

O Spring Security processa `POST /login` usando:

- usuário = e-mail;
- senha = campo `password`;
- BCrypt para comparação das senhas;
- sessão HTTP para manter a autenticação.

Não existe mais `@PostMapping("/login")` manual no `PageController`.

## Senhas

Novos usuários cadastrados pelo `UsuarioService` têm a senha convertida para BCrypt antes do `save`.
Trocas de senha também usam BCrypt.

O componente `MigrarSenhasParaBCrypt` converte automaticamente senhas antigas que ainda estiverem em texto puro quando a aplicação inicia, permitindo aproveitar o banco do exercício.

## Autorização

Usuários autenticados podem consultar:

- `/home`
- `/produtos`
- `/usuarios`
- `/categorias`
- `/estoque`
- `/movimentacoes`

Somente `ROLE_ADMIN` pode acessar endpoints de alteração, exclusão e troca de senha.

A interface também usa `isAdmin` para esconder os controles de alteração para usuários comuns, mas a proteção principal está no Spring Security/backend.

## CSRF

A proteção CSRF continua ativa. Os formulários Thymeleaf enviam o token automaticamente e as chamadas JavaScript `DELETE`/`PUT` enviam o token no header correspondente.

## Usuário de teste

No `usuario_teste.sql`:

- E-mail: `admin@teste.com`
- Senha: `12345678`
- Perfil: administrador (`user_role = 1`)

Se o banco já possuir esse usuário com senha em texto puro, o componente de migração converte a senha para BCrypt na inicialização.
