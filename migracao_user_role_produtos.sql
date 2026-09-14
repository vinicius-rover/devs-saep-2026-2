USE saeb_db;

-- Execute somente se a coluna user_role ainda nao existir.
ALTER TABLE usuario ADD COLUMN user_role INT NOT NULL DEFAULT 0;

-- Torna o usuario de teste administrador.
UPDATE usuario
SET user_role = 1
WHERE email = 'admin@teste.com';

-- Se o administrador ainda nao existir, use este INSERT no lugar do UPDATE:
-- INSERT INTO usuario (nome, email, senha, user_role)
-- VALUES ('Administrador', 'admin@teste.com', '12345678', 1);
