package model;

import javafx.scene.image.ImageView;
import java.util.HashMap;
import java.util.Objects;

public class Vehicle {
    private final ImageView image;
    private final HashMap<String, Double> destination;
    private boolean emMovement;

    public Vehicle(ImageView image, HashMap<String, Double> destination) {
        this.image = image;
        this.destination = destination;
        this.emMovement = true;
    }

    public ImageView getImage() {
        return image;
    }

    public Double getX() {
        return image.getLayoutX();
    }

    public Double getY() {
        return image.getLayoutY();
    }

    public void setX(Double coordinate) {
        image.setLayoutX(coordinate);
    }

    public void setY(Double coordinate) {
        image.setLayoutY(coordinate);
    }

    public Double getDestinationX() {
        return destination.get("X");
    }

    public Double getDestinationY() {
        return destination.get("Y");
    }

    public boolean isEmMovement() {
        return emMovement;
    }

    public void setMovimento(boolean emMovimento) {
        this.emMovement = emMovimento;
    }

    //Método para atualizar a posição do veiculo
    public void update(){
        if (!isEmMovement()) {
            return;
        }

        //Como os veiculos só se movimentam de forma retilinea só mudamos um dos dois eixos
        //Vai mudar no eixo do x
        if (!(Objects.equals(getX(), getDestinationX()))){
            setX((getX() > getDestinationX()) ? getX() - 1 : getX() + 1);
        } //Vai mudar no eixo do y
        else if (!(Objects.equals(getY(), getDestinationY()))){
            setY((getY() > getDestinationY()) ? getY() - 1 : getY() + 1);
        }
    }
}