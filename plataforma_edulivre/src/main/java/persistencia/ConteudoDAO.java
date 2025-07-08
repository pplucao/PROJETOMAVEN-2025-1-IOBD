package persistencia;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import negocio.Conteudo;

public class ConteudoDAO {
    public List<Conteudo> buscarConteudosPorCurso(int cursoId) throws SQLException {
        Connection connection = new ConexaoPostgreSQL().getConnection();
        List<Conteudo> conteudos = new ArrayList<>();
        String sql = "SELECT id, titulo, descricao, tipo, LENGTH(arquivo) as tamanho " +
        "FROM conteudo WHERE curso_id = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, cursoId);

            try (ResultSet rs = preparedStatement.executeQuery()) {
                while (rs.next()) {
                    Conteudo conteudo = new Conteudo();
                    conteudo.setId(rs.getInt("id"));
                    conteudo.setCursoId(cursoId);
                    conteudo.setTitulo(rs.getString("titulo"));
                    conteudo.setDescricao(rs.getString("descricao"));
                    conteudo.setTipo(rs.getString("tipo"));
                    conteudo.setTamanhoArquivo(rs.getLong("tamanho"));
                    
                    conteudos.add(conteudo);
                }
            }
        }
        return conteudos;
    }

}
