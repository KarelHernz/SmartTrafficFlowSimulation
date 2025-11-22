package model;

import javafx.geometry.Point2D;
import javafx.scene.image.ImageView;
import java.util.HashMap;
import java.util.Map;

public class Vehicle {
    private String cor;
    private ImageView image;
    private Map<String, Double[]> posicao;
    private Map<String, Double[]> destino;
    private boolean emMovimento;

    public Vehicle(String cor, ImageView image, Map<String, Double[]> posicao, Map<String, Double[]> destino, boolean emMovimento) {
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

    public Map<String, Double[]> getPosicao() {
        return posicao;
    }

    public void setPosicao(Map<String, Double[]> posicao, double x, double y) {
        this.posicao.put("coords: ", new Double[]{x, y});
    }

    public Map<String, Double[]> getDestino() {
        return destino;
    }

    public void setDestino(Map<String, Double[]> destino, double x, double y) {
        this.destino.put("coords: ", new Double[]{x, y});
    }

    public boolean isEmMovimento() {
        return emMovimento;
    }

    public void setEmMovimento(boolean emMovimento) {
        this.emMovimento = emMovimento;
    }
}