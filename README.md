<h1 align="center"> CLASSES DAO: </h1>

<h3 align="center"> usuarioDAO: </h3>

| Funcionamento - Método Inserir | Funcionamento - Método Obter | 
|---|---|
| Estabelece conexão com o banco | Busca usuário por ID | 
| Prepara statement SQL com parâmetros | Se encontrado, retorna objeto Usuario | 
| Executa a inserção | Se não encontrado, retorna objeto vazio | 
| Recupera o ID gerado | - | 
| Atualiza o objeto usuário com o novo ID | - | 

<h3 align="center"> matriculaDAO: </h3>
| Método inserir | Qual método realiza |
|---|---|
| Verifica se matrícula já existe | matriculaJaExiste |
| Verifica se usuário existe | usuarioExiste |
| Verifica se curso existe| cursoExiste |

❗Como é feito a verificação dos métodos:
Utilizam SELECT COUNT(*) para verificar existência
Retornam boolean indicando existência

<h5 align="center"> Tentando matricular: Usuário não matricula e usuário matriculado </h5>
![Tentando matricular usuário já matriculado e um não matriculado](./img/TentativaMatricular.png)

<h3 align="center"> cursoDAO: </h3>
| Método listarCursosComDetalhes | Método adicionarComentario |
|---|---|
| Calcula média de avaliações extraindo do JSONB | Cria novo comentário como objeto JSON |
| Conta alunos matriculados | Adiciona ao array existente |
| Agrupa por curso | Trata caso onde avaliação é null (COALESCE) |

❗Tem os mesmos métodos de verificar usuario e curso existente que MatriculaDAO

<h5 align="center"> Retorno dos cursos com avaliação JSON, total de alunos, média de avalições, comentários e notas </h5>
![Retorno dos cursos com avaliação JSON, total de alunos, média de avalições, comentários e notas](./img/listandoCursosDetalhes.png)

<h5 align="center"> Adicionando comentário (retorno pelo terminal do vscode) </h5>
![Adicionando comentário (retorno pelo terminal do vscode)](./img/ComentAddVSCODE.png)

<h5 align="center"> Adicionando comentário (retorno pelo banco) </h5>
![Adicionando comentário (retorno pelo banco)](./img/ComentarioAdicionado.png)

<h3 align="center"> conteudoDAO: </h3>

✅ Gerencia conteúdos dos cursos (vídeos, PDFs, etc).

| Método buscarConteudosPorCurso |
|---|
| Retorna lista de conteúdos | 
| Calcula tamanho do arquivo com LENGTH() | 
| Não carrega o arquivo binário por padrão (apenas metadados) | 

❗Para retornar o arquivo em KB foi criado um getTamanhoFormatado onde para de BYTES para KB, assim é chamado no toString formatado.

<h5 align="center"> Retornando conteudo dos cursos</h5>
![Retornando conteudo dos cursos](./img/RetornoConteudoCurso.png)

