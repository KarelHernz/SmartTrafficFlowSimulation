package model;

import javafx.scene.image.ImageView;
import java.util.Map;


public class Vehicle {
    private String cor;
    private ImageView image;
    private Map<String, Double> posicao;
    private Map<String, Double> destino;
    private boolean emMovimento;

    public Vehicle(String cor, ImageView image, Map<String, Double> posicao, Map<String, Double> destino) {
        this.cor = cor;
        this.image = image;
        this.posicao = posicao;
        this.destino = destino;
        this.emMovimento = true;
    }

    public String getCor() {
        return cor;
    }

    public void setCor(String cor) {
        this.cor = cor;
    }

    public ImageView getImage() {
        return image;
    }

    public void setImage(ImageView image) {
        this.image = image;
    }

    public Double getPosicao(String eixo) {
        return posicao.getOrDefault(eixo, 0.0);
    }

    public void setPosicao(String eixo, Double coordenada) {
        this.posicao.put(eixo, coordenada);
    }

    public Double getDestino(String eixo) {
        return destino.getOrDefault(eixo, 0.0);
    }

    public void setDestino(String eixo, Double coordenada) {
        this.destino.put(eixo, coordenada);
    }

    public boolean isEmMovimento() {
        return emMovimento;
    }

    public void setEmMovimento(boolean emMovimento) {
        this.emMovimento = emMovimento;
    }
}