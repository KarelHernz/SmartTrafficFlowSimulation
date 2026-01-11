package util;

public class Coordinates {
    private final Double layoutX;
    private final Double layoutY;
    private final Double destinationX;
    private final Double destinationY;

    public Coordinates(Double layoutX, Double layoutY, Double destinationX, Double destinationY) {
        this.layoutX = layoutX;
        this.layoutY = layoutY;
        this.destinationX = destinationX;
        this.destinationY = destinationY;
    }

    public Double getLayoutX() {
        return layoutX;
    }

    public Double getLayoutY() {
        return layoutY;
    }

    public Double getDestinationX() {
        return destinationX;
    }

    public Double getDestinationY() {
        return destinationY;
    }
}
