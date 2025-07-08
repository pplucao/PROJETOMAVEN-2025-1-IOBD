DROP DATABASE IF EXISTS edulivre;

CREATE DATABASE edulivre;

\c edulivre;

CREATE TABLE usuario (
	id SERIAL PRIMARY KEY,
	nome TEXT NOT NULL,
	email VARCHAR(100) UNIQUE NOT NULL,
	senha VARCHAR(100) NOT NULL,
	perfil VARCHAR(20) NOT NULL CHECK (perfil IN ('aluno', 'professor', 'admin'))
);

CREATE TABLE curso (
	id SERIAL PRIMARY KEY,
	titulo VARCHAR(100) NOT NULL,
	descricao TEXT,
	data_criacao TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
	avaliacao JSONB
);

CREATE TABLE matricula (
	id SERIAL PRIMARY KEY,
	usuario_id INTEGER NOT NULL REFERENCES usuario(id),
	curso_id INTEGER NOT NULL REFERENCES curso (id),
	data_matricula TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE conteudo (
	id SERIAL PRIMARY KEY,
	curso_id INTEGER NOT NULL REFERENCES curso (id),
	titulo VARCHAR(100) NOT NULL,
	descricao TEXT,
	tipo VARCHAR(20) NOT NULL CHECK (tipo IN ('video', 'pdf', 'imagem', 'audio', 'quiz', 'slide')),
	arquivo BYTEA
);

--Inserindo usuários
INSERT INTO usuario (nome, email, senha, perfil) 
VALUES 
('Pedro Silva', 'pedro.silva@email.com', 'senha123', 'aluno'),
('Barbara Meireles', 'babi.meir@email.com', 'mei123', 'aluno'),
('Igor Pereira', 'igor.pereira@email.com', 'igorA456', 'professor'),
('Marcio Souza', 'marcio.souza@email.com', 'admin789', 'admin');

--Inserindo cursos
INSERT INTO curso (titulo, descricao) VALUES 
('Aprendendo a programar', 'Este curso te ensina do zero a programar em java'),
('100 Receitas FIT', 'Coma o que sentir vontade sem aquele peso na consciência'),
('Trafego Pago', 'Aprendendo a ganhar dinheiro com a internet sem precisar sair da cama');

--Inserindo avaliações
UPDATE curso SET avaliacao = jsonb_set(
	avaliacao,
	'{comentarios}',
	avaliacao->'comentarios' ||
	jsonb_build_object(
		'usuario_id', 2,
		'nota', 8,
		'comentario', 'Gostei bastante das receitas low carb!',
		'data', '2025-06-13'
	)
)
WHERE id = 2;

UPDATE curso SET avaliacao = jsonb_set(
	avaliacao,
	'{comentarios}',
	avaliacao->'comentarios' ||
	jsonb_build_object(
		'usuario_id', 2,
		'nota', 6,
		'comentario', 'Estou tendo dificuldades com os exercícios',
		'data', '2025-06-14'
	)
)
WHERE id = 1;

UPDATE curso SET avaliacao = jsonb_set(
	avaliacao,
	'{comentarios}',
	avaliacao->'comentarios' ||
	jsonb_build_object(
		'usuario_id', 1,
		'nota', 9,
		'comentario', 'Curso bem auto-explicativo, gostei!',
		'data', '2025-06-13'
	)
)
WHERE id = 1;

--Inserindo matriculas
INSERT INTO matricula (usuario_id, curso_id) VALUES
(1,1),
(2,1),
(2,2),
(1,3);

--Inserindo conteudo
INSERT INTO conteudo (curso_id, titulo, descricao, tipo, arquivo) VALUES 
(1, 'Seja bem vindo (a) ao curso', 'Queremos lhe dar as boas vindas e te desejar um ótimo curso', 'imagem', pg_read_binary_file('./arquivos/1.jpg'));
