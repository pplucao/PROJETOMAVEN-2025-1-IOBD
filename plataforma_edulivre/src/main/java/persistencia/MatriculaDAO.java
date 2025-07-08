package persistencia;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import negocio.Matricula;

public class MatriculaDAO {
    public boolean inserirMatricula(Matricula matricula) throws SQLException {
        Connection connection = new ConexaoPostgreSQL().getConnection();
        if (matriculaJaExiste(matricula.getUsuarioId(), matricula.getCursoId())) {
            System.out.println("Erro: O usuário já está matriculado neste curso.");
            return false;
        }

        if (!usuarioExiste(matricula.getUsuarioId())) {
            System.out.println("Erro: Usuário não encontrado.");
            return false;
        }

        if (!cursoExiste(matricula.getCursoId())) {
            System.out.println("Erro: Curso não encontrado.");
            return false;
        }

        String sql = "INSERT INTO matricula (usuario_id, curso_id) VALUES (?, ?)";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, matricula.getUsuarioId());
            preparedStatement.setInt(2, matricula.getCursoId());

            int linhasAfetadas = preparedStatement.executeUpdate();

            if(linhasAfetadas > 0) {
                try(ResultSet rs = preparedStatement.getGeneratedKeys()) {
                    if (rs.next()) {
                        matricula.setId(rs.getInt(1));
                    }
                }
                return true;
            }
        } catch (SQLException e) {
            System.out.println("Erro ao inserir matrícula: " + e.getMessage());
            throw e;
        }
        return false;
    }

    // Método para verificar se a matrícula já existe
    private boolean matriculaJaExiste(int usuarioId, int cursoId) throws SQLException {
        Connection connection = new ConexaoPostgreSQL().getConnection();
        String sql = "SELECT COUNT(*) FROM matricula WHERE usuario_id = ? AND curso_id = ?";
        
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, usuarioId);
            preparedStatement.setInt(2, cursoId);
            
            try (ResultSet rs = preparedStatement.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }
        }
        return false;
    }

    // Método para verificar se o usuário existe
    private boolean usuarioExiste(int usuarioId) throws SQLException {
        Connection connection = new ConexaoPostgreSQL().getConnection();
        String sql = "SELECT COUNT(*) FROM usuario WHERE id = ?";
        
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, usuarioId);
            
            try (ResultSet rs = preparedStatement.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }
        }
        return false;
    }

    // Método para verificar se o curso existe
    private boolean cursoExiste(int cursoId) throws SQLException {
        Connection connection = new ConexaoPostgreSQL().getConnection();
        String sql = "SELECT COUNT(*) FROM curso WHERE id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, cursoId);


            try (ResultSet rs = preparedStatement.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }
        }
        return false;
    }
}
