package persistencia;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import negocio.Usuario;

public class UsuarioDAO {
    public void inserir(Usuario usuario) throws SQLException{
        Connection connection = new ConexaoPostgreSQL().getConnection(); //conecta com o banco
        String sql = "INSERT INTO usuario (nome,email,senha,perfil) VALUES (?, ?, ?, ?)"; //cria o script do sql usado no banco
        PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

        preparedStatement.setString(1, usuario.getNome()); 
        preparedStatement.setString(2, usuario.getEmail()); 
        preparedStatement.setString(3, usuario.getSenha()); 
        preparedStatement.setString(4, usuario.getPerfil().name());

        int linhasAfetadas = preparedStatement.executeUpdate();
            
            if (linhasAfetadas == 0) {
                throw new SQLException("Falha ao criar usuário, nenhuma linha afetada.");
            }
        
        // Recuperar o ID gerado
        try (ResultSet rs = preparedStatement.getGeneratedKeys()) {
            if (rs.next()) {
                usuario.setId(rs.getInt(1));
            }
        }
    }

    public Usuario obter(int i) throws SQLException {
        String sql = "SELECT * FROM usuario WHERE id = ?;"; //cria o script do sql usado no banco
        Connection connection = new ConexaoPostgreSQL().getConnection(); //conecta com o banco
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setInt(1, i);
        ResultSet rs = preparedStatement.executeQuery();
        Usuario p = new Usuario();
        
        if (rs.next()) {
            p.setId(rs.getInt("id"));
            p.setNome(rs.getString("nome"));
            p.setEmail(rs.getString("email"));
            p.setSenha(rs.getString("senha"));
        }
        return p;
    }
}

