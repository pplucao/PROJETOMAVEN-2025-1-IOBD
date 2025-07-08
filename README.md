<h1 align="center"> CLASSES DAO: </h1>

<h3 align="center"> usuarioDAO: </h3>

### Método Inserir
| Funcionamento                      | Detalhes                                  |
|------------------------------------|-------------------------------------------|
| Estabelece conexão com o banco     | Abre conexão com PostgreSQL              |
| Prepara statement SQL              | Usa parâmetros para evitar SQL injection |
| Executa a inserção                 | Retorna número de linhas afetadas        |
| Atualiza o objeto usuário          | Define o ID gerado pelo banco            |

### Método Obter
| Funcionamento                     | Detalhes                                  |
|------------------------------------|-------------------------------------------|
| Busca usuário por ID               | Usa SELECT com WHERE id=?                |
| Retorna objeto Usuario             | Popula todos os campos se encontrado     |
| Retorna objeto vazio               | Se nenhum usuário for encontrado         |



<h3 align="center"> matriculaDAO: </h3>

### Método inserirMatricula
| Validação                         | Método auxiliar          |
|------------------------------------|--------------------------|
| Verifica se matrícula já existe    | matriculaJaExiste()      |
| Verifica se usuário existe         | usuarioExiste()          |
| Verifica se curso existe           | cursoExiste()            |

**Como é feita a verificação**:  
Todos os métodos auxiliares utilizam `SELECT COUNT(*)` e retornam `boolean` indicando existência.

![Tentativa de matrícula](./img/TentativaMatricular.png)  
*Tentando matricular usuário já matriculado e um não matriculado*

<h3 align="center"> cursoDAO: </h3>

### Métodos Principais
| listarCursosComDetalhes            | adicionarComentario               |
|-------------------------------------|------------------------------------|
| Calcula média de avaliações (JSONB) | Cria novo comentário como objeto JSON |
| Conta alunos matriculados           | Adiciona ao array existente       |
| Agrupa resultados por curso         | Trata casos null com COALESCE     |

**Observação**:  
Reutiliza os mesmos métodos de verificação (usuarioExiste, cursoExiste) que MatriculaDAO.

![Listagem de cursos](./img/listandoCursosDetalhes.png)  
*Cursos com avaliações JSON, total de aluno, notas e médias*

![Comentário adicionado](./img/ComentAddVSCODE.png)  
*Saída no terminal do VS Code*

![Comentário no banco](./img/ComentarioAdicionado.png)  
*Visualização direta no banco de dados*

<h3 align="center"> conteudoDAO: </h3>

### Responsabilidades
- Gerencia conteúdos dos cursos (vídeos, PDFs, etc)
- Fornece informações sobre tamanho e tipo de arquivos

### Método buscarConteudosPorCurso
| Funcionalidade                     | Detalhes                                  |
|------------------------------------|-------------------------------------------|
| Retorna lista de conteúdos         | Inclui todos os metadados                |
| Calcula tamanho do arquivo         | Usa LENGTH(arquivo)                      |
| Não carrega binários por padrão    | Apenas metadados são retornados          |

**Conversão de tamanho**:  
O método `getTamanhoFormatado()` converte bytes para KB/MB e é usado no `toString()`.

![Conteúdos do curso](./img/RetornoConteudoCurso.png)  
*Exemplo de retorno de conteúdos*
