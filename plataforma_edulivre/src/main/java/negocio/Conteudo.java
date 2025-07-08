package negocio;

public class Conteudo {
    private int id;
    private int cursoId;
    private String titulo;
    private String descricao;
    private String tipo;
    private byte[] arquivo;
    private long tamanhoArquivo;

    public Conteudo() {
    }

    public Conteudo(int cursoId, String titulo, String descricao, String tipo) {
        this.cursoId = cursoId;
        this.titulo = titulo;
        this.descricao = descricao;
        this.tipo = tipo;
    }

    // Getters
    public int getId() { return id; }
    public int getCursoId() { return cursoId; }
    public String getTitulo() { return titulo; }
    public String getDescricao() { return descricao; }
    public String getTipo() { return tipo; }
    public byte[] getArquivo() { return arquivo; }
    public String getTamanhoFormatado() {
        if (tamanhoArquivo < 1024) {
            return tamanhoArquivo + " B";
        } else if (tamanhoArquivo < 1024 * 1024) {
            return String.format("%.2f KB", tamanhoArquivo / 1024.0);
        } else {
            return String.format("%.2f MB", tamanhoArquivo / (1024.0 * 1024));
        }
    }

    // Setters
    public long getTamanhoArquivo() { return tamanhoArquivo; }
    public void setId(int id) { this.id = id; }
    public void setCursoId(int cursoId) { this.cursoId = cursoId; }
    public void setTitulo(String titulo) { this.titulo = titulo; }
    public void setDescricao(String descricao) { this.descricao = descricao; }
    public void setTipo(String tipo) { this.tipo = tipo; }
    public void setArquivo(byte[] arquivo) {
        this.arquivo = arquivo;
        this.tamanhoArquivo = arquivo != null ? arquivo.length : 0;
    }
    public void setTamanhoArquivo(long tamanhoArquivo) { this.tamanhoArquivo = tamanhoArquivo; }

    //ToString
    @Override
    public String toString() {
        return "Conteudo{" +
                "id=" + id +
                ", cursoId=" + cursoId +
                ", titulo='" + titulo + '\'' +
                ", tipo='" + tipo + '\'' +
                ", tamanho=" + getTamanhoFormatado() +
                '}';
    }

}
