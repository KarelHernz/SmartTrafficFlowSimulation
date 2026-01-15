package model;

import javafx.scene.image.ImageView;
import util.Coordinate;
import java.util.Objects;

public class Vehicle {
    private final ImageView image;
    private final Coordinate destination;
    private boolean inMovement;

    public Vehicle(ImageView image, Coordinate destination) {
        this.image = image;
        this.destination = destination;
        start();
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
        return destination.getX();
    }

    public Double getDestinationY() {
        return destination.getY();
    }

    public boolean inMovement() {
        return inMovement;
    }

    public void stop(){
        inMovement = false;
    }

    public void start(){
        inMovement = true;
    }

    //Método para atualizar a posição do veiculo
    public void update(){
        if (!inMovement()) {
            return;
        }

        //Como os veículos só se movimentam de forma retilínea só mudamos um dos dois eixos
        //Vai mudar no eixo do x
        if (!(Objects.equals(getX(), getDestinationX()))){
            setX((getX() > getDestinationX()) ? getX() - 1 : getX() + 1);
        } //Vai mudar no eixo do y
        else if (!(Objects.equals(getY(), getDestinationY()))){
            setY((getY() > getDestinationY()) ? getY() - 1 : getY() + 1);
        }
    }
}