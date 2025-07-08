<h1 align="center"> CLASSES DAO: </h1>

###UsuarioDAO:

| Funcionamento - Método Inserir | Funcionamento - Método Obter | 
|---|---|
| Estabelece conexão com o banco | Busca usuário por ID | 
| Prepara statement SQL com parâmetros | Se encontrado, retorna objeto Usuario | 
| Executa a inserção | Se não encontrado, retorna objeto vazio | 
| Recupera o ID gerado | - | 
| Atualiza o objeto usuário com o novo ID | - | 

###MatriculaDAO:
| Método inserir | Qual método realiza |
|---|---|
| Verifica se matrícula já existe | matriculaJaExiste |
| Verifica se usuário existe | usuarioExiste |
| Verifica se curso existe| cursoExiste |

❗Como é feito a verificação dos métodos:
Utilizam SELECT COUNT(*) para verificar existência
Retornam boolean indicando existência

###CursoDAO:
| Método listarCursosComDetalhes | Método adicionarComentario |
|---|---|
| Calcula média de avaliações extraindo do JSONB | Cria novo comentário como objeto JSON |
| Conta alunos matriculados | Adiciona ao array existente |
| Agrupa por curso | Trata caso onde avaliação é null (COALESCE) |

❗Tem os mesmos métodos de verificar usuario e curso existente que MatriculaDAO

###ConteudoDAO:

✅ Gerencia conteúdos dos cursos (vídeos, PDFs, etc).

| Método buscarConteudosPorCurso |
|---|
| Retorna lista de conteúdos | 
| Calcula tamanho do arquivo com LENGTH() | 
| Não carrega o arquivo binário por padrão (apenas metadados) | 

❗Para retornar o arquivo em KB foi criado um getTamanhoFormatado onde para de BYTES para KB, assim é chamado no toString formatado.

