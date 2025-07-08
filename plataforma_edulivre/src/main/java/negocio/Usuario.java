package negocio;

public class Usuario {
    private int id;
    private String nome;
    private String email;
    private String senha;
    private Perfil perfil;

    public enum Perfil { aluno, professor, admin }

    public Usuario (String nome, String email, String senha, Perfil perfil) {
        this.nome = nome;
        this.email = email;
        this.senha = senha;
        this.perfil = perfil;
    }

    public Usuario() {}

    //Getters
    public int getId() { return id; }
    public String getNome() { return nome; }
    public String getEmail() { return email; }
    public String getSenha() { return senha; }
    public Perfil getPerfil() { return perfil; }

    //Setters
    public void setId(int id) { this.id = id; }
    public void setNome(String nome) { this.nome = nome; }
    public void setEmail(String email) { this.email = email; }
    public void setSenha(String senha) { this.senha = senha; }
    public void setPerfil(Perfil perfil) { this.perfil = perfil; }

    // toString
    @Override
    public String toString() {
        return "Usuario [id=" + id + ", nome=" + nome + ", email=" + email + ", perfil=" + perfil + "]";
    }
}
