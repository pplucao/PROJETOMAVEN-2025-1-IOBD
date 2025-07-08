package apresentacao;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import negocio.Conteudo;
import negocio.Curso;
import negocio.Matricula;
import negocio.Usuario;
import persistencia.ConteudoDAO;
import persistencia.CursoDAO;
import persistencia.MatriculaDAO;
import persistencia.UsuarioDAO;

public class Main {
    public static void main(String[] args) throws SQLException {
        
        //Teste para inserir um usuário
        /*Usuario usuario1 = new Usuario("Nelson", "Nelson@gmail.com", "nelson2340", Usuario.Perfil.aluno);
        UsuarioDAO usuarioDAO = new UsuarioDAO();

        try {
            usuarioDAO.inserir(usuario1);
            System.out.println("Usuário inserido com sucesso!");
        } catch (SQLException e) {
            System.err.println("Erro ao inserir usuário no banco de dados:");
            e.printStackTrace();
        }*/

        // Cria uma instância do DAO
        //CursoDAO cursoDAO = new CursoDAO();
    

        //A) Listar cursos, com a média de avaliação e número de alunos matriculados.
        try {
            // Cria uma instância do DAO
            CursoDAO cursoDAO = new CursoDAO();
            
            // Obtém a lista de cursos com detalhes
            List<Curso> cursos = cursoDAO.listarCursosComDetalhes();
            
            // Itera sobre cada curso
            for (Curso curso : cursos) {
                System.out.println("\nCurso: " + curso.getTitulo());
                System.out.println("Descrição: " + curso.getDescricao());
                System.out.printf("Média: %.2f\n", curso.getMediaAvaliacoes());
                System.out.println("Total de Alunos: " + curso.getTotalAlunos());
                
                // Acessa as avaliações dentro do loop para cada curso
                List<Map<String, Object>> avaliacoes = curso.getObterAvaliacoesDetalhadas();
                
                System.out.println("Avaliações (" + avaliacoes.size() + "):");
                for (Map<String, Object> avaliacao : avaliacoes) {
                    System.out.printf(" - Usuário %s: Nota %s - %s (em %s)\n",
                        avaliacao.get("usuario_id"),
                        avaliacao.get("nota"),
                        avaliacao.get("comentario"),
                        avaliacao.get("data"));
                }
            }
        } catch (Exception e) {
            System.err.println("Erro ao processar cursos: " + e.getMessage());
            e.printStackTrace();
        }

        System.out.println("===================");

        //B) Buscar conteúdos de um curso, mostrando tipo e tamanho do arquivo.
        try {
            ConteudoDAO conteudoDAO = new ConteudoDAO();
            List<Conteudo> conteudos = conteudoDAO.buscarConteudosPorCurso(1);
    
            System.out.println("CONTEÚDOS DO CURSO:");
            for( Conteudo curso : conteudos) {
                System.out.println("ID: " + curso.getId());
                System.out.println("Título: " + curso.getTitulo());
                System.out.println("Tipo: " + curso.getTipo());
                System.out.println("Tamanho: " + curso.getTamanhoFormatado());
                System.out.println("-------------------");
            }
        } catch (Exception e) {
            System.err.println("Erro ao processar conteúdo do curso: " + e.getMessage());
            e.printStackTrace();
        }
        

        //C)Inserir uma nova matrícula (com validações).
        MatriculaDAO matriculaDAO = new MatriculaDAO();

        //Criar nova matriculas
        Matricula matricula1 = new Matricula(1, 3); //ja existe
        Matricula matricula2 = new Matricula(1, 2); //nao existe ainda

        // Tentar inserir a matrícula1
        if (matriculaDAO.inserirMatricula(matricula1)) { //essa nao pode ser adicionada
            System.out.println("Matrícula realizada com sucesso!");
        } else {
            System.out.println("Falha ao realizar matrícula.");
        }

        // Tentar inserir a matrícula2
        if (matriculaDAO.inserirMatricula(matricula2)) {
            System.out.println("Matrícula realizada com sucesso!");
        } else {
            System.out.println("Falha ao realizar matrícula.");
        }

        //D) Adicionar um novo comentário no JSONB de avaliação de um curso.
        try{
            CursoDAO cursoDAO = new CursoDAO();

            // Adicionar um novo comentário
            boolean sucesso = cursoDAO.adicionarComentario(
                1,       // ID do curso
                2,       // ID do usuário
                8,       // Nota (0-10)
                "Ótimo curso, aprendi muito!" 
            );

            if (sucesso) {
                System.out.println("Comentário adicionado com sucesso!");
            } else {
                System.out.println("Falha ao adicionar comentário.");
            }
        } catch (SQLException e) {
            System.err.println("Erro de banco de dados: " + e.getMessage());
        } catch (IllegalArgumentException e) {
            System.err.println("Dados inválidos: " + e.getMessage());
        }

    }
}   
