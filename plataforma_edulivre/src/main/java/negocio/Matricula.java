package negocio;

import java.time.LocalDateTime;

public class Matricula {
    private int id;
    private int usuarioId;
    private int cursoId;
    private LocalDateTime dataMatricula;

    public Matricula() {
    }

    public Matricula(int usuarioId, int cursoId) {
        this.usuarioId = usuarioId;
        this.cursoId = cursoId;
    }

    // Getters
    public int getId() { return id; }
    public int getUsuarioId() { return usuarioId; }
    public int getCursoId() { return cursoId; }
    public LocalDateTime getDataMatricula() { return dataMatricula; }


    //Setters
    public void setId(int id) { this.id = id; }
    public void setUsuarioId(int usuarioId) { this.usuarioId = usuarioId; }
    public void setCursoId(int cursoId) { this.cursoId = cursoId; }
    public void setDataMatricula(LocalDateTime dataMatricula) { this.dataMatricula = dataMatricula; }

    //toString
    @Override
    public String toString() {
        return "Matricula{" +
                "id=" + id +
                ", usuarioId=" + usuarioId +
                ", cursoId=" + cursoId +
                ", dataMatricula=" + dataMatricula +
                '}';
    }
}