package persistencia;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexaoPostgreSQL {
    private String host;
    private String port;
    private String username;
    private String password;
    private String database;

    public ConexaoPostgreSQL (){
        this.host = "localhost";
        this.port = "5432";
        this.username = "postgres";
        this.password = "senha0310";
        this.database = "edulivre";
    }

    public Connection getConnection() throws SQLException {
        String url = "jdbc:postgresql://" + this.host + ":" + this.port + "/" + this.database;
        return DriverManager.getConnection(url, username, password);
    }
}

