package negocio;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.json.JSONArray;
import org.json.JSONObject;


public class Curso {
    private int id;
    private String titulo;
    private String descricao;
    private LocalDateTime dataCriacao;
    private String avaliacaoJson;
    private double mediaAvaliacoes;  // Adicionado
    private int totalAlunos;

    public Curso (String titulo, String descricao) {
        this.titulo = titulo;
        this.descricao = descricao;
        this.dataCriacao = LocalDateTime.now();
    }

    // Método para converter o JSON em lista de mapas
    public List<Map<String, Object>> getObterAvaliacoesDetalhadas() {
        List<Map<String, Object>> avaliacoes = new ArrayList<>(); //cria uma lista vazia 
    
    if (this.avaliacaoJson == null || this.avaliacaoJson.trim().isEmpty()) {
        return avaliacoes; //se nao houver avaliações retorna lista vazia
    }
    try {
        JSONObject json = new JSONObject(this.avaliacaoJson); //converte a string JSON em um objeto JSON
        JSONArray comentarios = json.getJSONArray("comentarios"); //obtem o array "comentarios" do JSON
        
        for (int i = 0; i < comentarios.length(); i++) { // percorre 
            JSONObject comentario = comentarios.getJSONObject(i);
            Map<String, Object> avaliacao = new HashMap<>();
            
            avaliacao.put("usuario_id", comentario.getInt("usuario_id"));
            avaliacao.put("nota", comentario.getInt("nota"));
            avaliacao.put("comentario", comentario.getString("comentario"));
            avaliacao.put("data", comentario.getString("data"));
            
            avaliacoes.add(avaliacao);
        }
    }catch (Exception e) { //tratamento de erros
        System.err.println("Erro ao processar avaliações: " + e.getMessage());
    }
    return avaliacoes;
}
    
    //Getters
    public int getId() { return id; }
    public String getTitulo() { return titulo; }
    public String getDescricao() { return descricao; }
    public LocalDateTime getDataCriacao() { return dataCriacao; }
    public double getMediaAvaliacoes() { return mediaAvaliacoes; }
    public int getTotalAlunos() { return totalAlunos; }
    public String getAvaliacaoJson() { return avaliacaoJson; }

    //Setters
    public void setId(int id) { this.id = id; }
    public void setMediaAvaliacoes(double mediaAvaliacoes) { this.mediaAvaliacoes = mediaAvaliacoes; }
    public void setTotalAlunos(int totalAlunos) { this.totalAlunos = totalAlunos; }
    public void setAvaliacaoJson(String avaliacaoJson) { this.avaliacaoJson = avaliacaoJson; }    
}
