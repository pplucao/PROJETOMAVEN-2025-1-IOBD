package persistencia;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import negocio.Curso;

public class CursoDAO {
    public List<Curso> listarCursosComDetalhes() throws SQLException {
        Connection connection = new ConexaoPostgreSQL().getConnection();
        String sql = "SELECT curso.id, curso.titulo, curso.descricao, " +
             "AVG((avaliacao_elemento->>'nota')::numeric) AS media_avaliacao, " +
             "COUNT(DISTINCT matricula.usuario_id) AS total_alunos, " +
             "curso.avaliacao AS avaliacao_json " +
             "FROM curso " +
             "LEFT JOIN matricula ON curso.id = matricula.curso_id " +
             "LEFT JOIN jsonb_array_elements(" +
             "COALESCE(curso.avaliacao, '{\"comentarios\":[]}'::jsonb)->'comentarios'" +
             ") avaliacao_elemento ON true " +
             "GROUP BY curso.id, curso.avaliacao " +
             "ORDER BY curso.id";
        
        List<Curso> cursos = new ArrayList<>();
        
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql);
             ResultSet rs = preparedStatement.executeQuery()) {
            
            while (rs.next()) {
                Curso curso = new Curso(
                    rs.getString("titulo"),
                    rs.getString("descricao")
                );
                curso.setId(rs.getInt("id"));
                curso.setMediaAvaliacoes(rs.getDouble("media_avaliacao"));
                curso.setTotalAlunos(rs.getInt("total_alunos"));
                curso.setAvaliacaoJson(rs.getString("avaliacao_json"));
                
                cursos.add(curso);
            }
        } finally {
            connection.close();
        }
        
        return cursos;
    }

    //ADICIONAR COMENTARIO EM JSON
    public boolean adicionarComentario(int cursoId, int usuarioId, int nota, String comentario) throws SQLException {
        Connection connection = new ConexaoPostgreSQL().getConnection();

        // Validações iniciais
        if (nota < 0 || nota > 10) {
            throw new IllegalArgumentException("A nota deve estar entre 0 e 10");
        }
        
        if (comentario == null || comentario.trim().isEmpty()) {
            throw new IllegalArgumentException("O comentário não pode ser vazio");
        }

        // Verifica se o usuário existe
        if (!usuarioExiste(usuarioId)) {
            throw new SQLException("Usuário não encontrado");
        }

        // Verifica se o curso existe
        if (!cursoExiste(cursoId)) {
            throw new SQLException("Curso não encontrado");
        }

        String sql = "UPDATE curso SET avaliacao = " +
        "jsonb_set(" +
        "COALESCE(avaliacao, '{\"comentarios\":[]}'::jsonb), " +
        "'{comentarios}', " +
        "COALESCE(avaliacao->'comentarios', '[]'::jsonb) || " +
        "jsonb_build_object(" +
        " 'usuario_id', ?, " +
        " 'nota', ?, " +
        " 'comentario', ?, " +
        " 'data', ?" +
        "  )" +
        ") WHERE id = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            // Parâmetros para o novo comentário
            preparedStatement.setInt(1, usuarioId);
            preparedStatement.setInt(2, nota);
            preparedStatement.setString(3, comentario);
            preparedStatement.setString(4, LocalDate.now().toString()); // Data no formato YYYY-MM-DD
            preparedStatement.setInt(5, cursoId);

            int linhasAfetadas = preparedStatement.executeUpdate();
            return linhasAfetadas > 0;
        }
    }

    //VERIFICA SE USUÁRIO EXISTE
    private boolean usuarioExiste(int usuarioId) throws SQLException {
        Connection connection = new ConexaoPostgreSQL().getConnection();
        String sql = "SELECT COUNT(*) FROM usuario WHERE id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, usuarioId);
            try (ResultSet rs = preparedStatement.executeQuery()) {
                return rs.next() && rs.getInt(1) > 0;
            }
        }
    }

    //VERIFICA SE O CURSO EXISTE
    private boolean cursoExiste(int cursoId) throws SQLException {
        Connection connection = new ConexaoPostgreSQL().getConnection();
        String sql = "SELECT COUNT(*) FROM curso WHERE id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, cursoId);
            try (ResultSet rs = preparedStatement.executeQuery()) {
                return rs.next() && rs.getInt(1) > 0;
            }
        }
    }
}