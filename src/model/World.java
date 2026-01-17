package model;

import controller.FixedCycle;
import controller.Strategy;
import javafx.animation.AnimationTimer;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import model.state.Green;
import model.state.Red;
import util.Coordinate;
import util.Statistics;
import util.TimeElapsed;
import view.ControlPanel;
import java.util.*;

public class World{
    private final Map<String, Image> vehicleImages = new HashMap<>();
    private final Intersection intersection;
    private final Road verticalRoad;
    private final Road horizontalRoad;
    private final Movement timer;
    private final Statistics statistics;
    private final ControlPanel controlPanel;
    private final TimeElapsed timeElapsed = new TimeElapsed();

    private float speed = 1f;

    public World(ControlPanel controlPanel, HashMap<String, ImageView> imagesViews, Statistics statistics){
        this.controlPanel = controlPanel;

        //region HashMap de imagens dos veículos
        vehicleImages.put("Blue", new Image(Objects.requireNonNull(getClass().getResourceAsStream("/img/blue_car.png"))));
        vehicleImages.put("Golden", new Image(Objects.requireNonNull(getClass().getResourceAsStream("/img/golden_car.png"))));
        vehicleImages.put("Purple", new Image(Objects.requireNonNull(getClass().getResourceAsStream("/img/purple_car.png"))));
        vehicleImages.put("Red", new Image(Objects.requireNonNull(getClass().getResourceAsStream("/img/red_car.png"))));
        vehicleImages.put("White", new Image(Objects.requireNonNull(getClass().getResourceAsStream("/img/white_car.png"))));
        //endregion

        //region HashMap das coordenadas do início e do fim dos lanes
        HashMap<String, Coordinate> coordinates = new HashMap<>();
        //Coordenadas iniciais dos lanes horizontais
        coordinates.put("HStart1", new Coordinate(660.0, 243.0));
        coordinates.put("HStart2", new Coordinate(660.0, 278.0));
        coordinates.put("HStart3", new Coordinate(-30.0, 330.0));
        coordinates.put("HStart4", new Coordinate(-30.0, 365.0));

        //Coordenadas finais dos lanes horizontais
        coordinates.put("HEnd1", new Coordinate(-30.0, 243.0));
        coordinates.put("HEnd2", new Coordinate(-30.0, 278.0));
        coordinates.put("HEnd3", new Coordinate(660.0, 330.0));
        coordinates.put("HEnd4", new Coordinate(660.0, 365.0));

        //Coordenadas iniciais dos lanes verticais
        coordinates.put("VStart1", new Coordinate(252.0, -40.0));
        coordinates.put("VStart2", new Coordinate(287.0, -40.0));
        coordinates.put("VStart3", new Coordinate(341.0, 648.0));
        coordinates.put("VStart4", new Coordinate(376.0, 648.0));

        //Coordenadas finais dos lanes verticais
        coordinates.put("VEnd1", new Coordinate(252.0, 648.0));
        coordinates.put("VEnd2", new Coordinate(287.0, 648.0));
        coordinates.put("VEnd3", new Coordinate(341.0, -40.00));
        coordinates.put("VEnd4", new Coordinate(376.0, -40.0));
        //endregion

        //region HashMap das coordenadas das paradas dos veículos nos semáforos
        //Lanes horizontais
        coordinates.put("HStop1", new Coordinate(510.0, 243.0));
        coordinates.put("HStop2", new Coordinate(510.0, 278.0));
        coordinates.put("HStop3", new Coordinate(119.0, 330.0));
        coordinates.put("HStop4", new Coordinate(119.0, 365.0));

        //Lanes verticais
        coordinates.put("VStop1", new Coordinate(252.0, 109.0));
        coordinates.put("VStop2", new Coordinate(287.0, 109.0));
        coordinates.put("VStop3", new Coordinate(341.0, 500.0));
        coordinates.put("VStop4", new Coordinate(376.0, 500.0));
        //endregion

        //ArrayList das vias verticais e horizontais com as várias coordenadas dos HashMaps anteriores
        ArrayList<Lane> horizontalLanes = new ArrayList<>();
        ArrayList<Lane> verticalLanes = new ArrayList<>();
        for (int i = 1; i < 5; i++) {
            Lane laneHorizontal = new Lane(coordinates.get("HStart"+i), coordinates.get("HEnd"+i), coordinates.get("HStop"+i));
            Lane laneVertical = new Lane(coordinates.get("VStart"+i), coordinates.get("VEnd"+i), coordinates.get("VStop"+i));
            horizontalLanes.add(laneHorizontal);
            verticalLanes.add(laneVertical);
        }

        Red red = new Red();
        Green green = new Green();

        //region ArrayList de TrafficLights
        //Define o road vertical com os semáforos
        ArrayList<TrafficLight> tlVerticalList = new ArrayList<>();
        tlVerticalList.add(new TrafficLight(red, imagesViews.get("Top")));
        tlVerticalList.add(new TrafficLight(red, imagesViews.get("Bottom")));

        //Define o road vertical com os semáforos
        ArrayList<TrafficLight> tlHorizontalList = new ArrayList<>();
        tlHorizontalList.add(new TrafficLight(green, imagesViews.get("Left")));
        tlHorizontalList.add(new TrafficLight(green, imagesViews.get("Right")));
        //endregion

        //Criação dos roads
        verticalRoad = new Road(verticalLanes, tlVerticalList);
        horizontalRoad = new Road(horizontalLanes, tlHorizontalList);

        this.intersection = new Intersection(new FixedCycle());
        intersection.addRoad(verticalRoad);
        intersection.addRoad(horizontalRoad);
        statistics.setStrategy("Ciclo Fixo");

        this.statistics = statistics;

        timer = new Movement();
        timer.start();
    }

    //Dentro desta classe Movement vai correr toda a animação dos veículos, dos semáforos e outros eventos
    private class Movement extends AnimationTimer {
        private long lastTime = 0;
        private long lastSecond = 0;

        @Override
        public void start() {
            super.start();
            lastTime = 0;
            lastSecond = 0;
        }

        @Override
        public void handle(long currentTime) {
            long SECONDS_NANOS = (long) (1000000000L / speed);
            long elapsedTime = currentTime - lastTime;

            //Atualiza cada segundo
            if (currentTime - lastSecond > SECONDS_NANOS) {
                long allSecondsElapsed = timeElapsed.getAllSeconds();

                //Cada 5 segundos gera de forma aleatória os veículos nos roads
                if (allSecondsElapsed % 4 == 0) {
                    updateRoad(verticalRoad, true);
                    updateRoad(horizontalRoad, false);
                }
                intersection.update();
                lastSecond = currentTime;
                timeElapsed.increment();
                statistics.setTimeElapsed(timeElapsed.toString());
                controlPanel.updateLabels();
            }

            //Atualiza cada "x" frames
            long FRAMES_PER_SECOND = 60L;
            long INTERVAL = SECONDS_NANOS / FRAMES_PER_SECOND;
            if (elapsedTime >= INTERVAL) {
                lastTime = currentTime;
                verticalRoad.update(controlPanel.getVehiclesPane());
                horizontalRoad.update(controlPanel.getVehiclesPane());
            }
        }
    }

    //Muda a velocidade da animação
    public void changeSpeed(float speed){
        this.speed = speed;
        if (speed == 0){
            timer.stop();
            return;
        }
        timer.start();
    }

    public void changeCycle(Strategy strategy){
        intersection.changeStrategy(strategy);
    }

    //Método para inserir veículos no road
    private void updateRoad(Road road, Boolean isVertical){
        for (int via = 1; via <= road.getNumberOfLanes(); via++) {
            //Cálculo do número de veículos no road
            int stoppedVehicles = road.getVehiclesStopped(via);
            int limitOfVehicles = road.getMaxVehiclesStopped(via);
            int vehiclesRemainder = limitOfVehicles - stoppedVehicles;
            //----------------------------------------------------//

            //Se ouver espaço para mais um veículo no road, gera de forma aleatória um novo veículo
            if (vehiclesRemainder > 0) {
                //Devolve um número entre 0 e 1
                int numRandom = (int) (Math.random() * 2);
                if (numRandom > 0) {
                    Vehicle vehicle = generateVehicle(via, isVertical);
                    road.addVehicle(via, vehicle);

                    //Atualiza o contador da direção onde aparecem os veículos nas estatísticas
                    String direction = getDirection(isVertical, via);
                    int result = statistics.getDirectionValue(direction);
                    statistics.updateDirection(direction, result+numRandom);
                }
            }
        }
    }

    //Obtém em qual direção apareceu o veículo
    private String getDirection(boolean isVertical, int nVia){
        if (isVertical){
            return (nVia == 1 || nVia == 2) ? "Top" : "Bottom";
        }

        return (nVia == 1 || nVia == 2)  ? "Right" : "Left";
    }

    //Método para gerar os veículos
    private Vehicle generateVehicle(Integer nVia, Boolean isVertical){
        //Cores que vão ter os veículos
        String[] vehicleColors = {"Blue", "Golden", "Purple", "Red", "White"};
        int nColors = vehicleColors.length;

        //Escolhe de forma aleatória uma cor para o veículo
        int indexColor = (int)(Math.random() * nColors);
        String color = vehicleColors[indexColor];

        //Incrementa o número de veículos daquela cor nas estatísticas
        int result = statistics.getVehiclesValue(color);
        statistics.updateVehiclesColors(color, ++result);

        //Devolve uma imagem do array de images dos veículos em base à cor do veículo
        Image image = vehicleImages.get(color);
        ImageView imageViewVehicle = new ImageView();
        imageViewVehicle.setImage(image);

        /* Os veículos vão numa única direção dos eixos X e Y:
           X - Quando o veículo vai numa road em horizontal
           Y - Quando o veículo vai numa road em vertical */
        Coordinate start = (isVertical) ? verticalRoad.getLaneStart(nVia)
                                        : horizontalRoad.getLaneStart(nVia);

        Coordinate end = (isVertical) ? verticalRoad.getLaneEnd(nVia)
                                      : horizontalRoad.getLaneEnd(nVia);

        //Define a posição na qual vai aparecer a imageView
        imageViewVehicle.setLayoutX(start.getX());
        imageViewVehicle.setLayoutY(start.getY());

        //Configura o tamanho da imagemView
        imageViewVehicle.setFitWidth(27);
        imageViewVehicle.setFitHeight(49);

        //Rota a imageView em base ao valor do isVertical e o número da via
        double rotation;
        if (isVertical){
            rotation = (nVia == 1 || nVia == 2) ? 0 : 180.0;
        }
        else {
            rotation = (nVia == 1 || nVia == 2) ? 90.0 : 270.0;
        }
        imageViewVehicle.setRotate(rotation);
        controlPanel.addImageView(imageViewVehicle);
        return new Vehicle(imageViewVehicle, end);
    }

    public void reset(){
        intersection.reset();
        statistics.reset();
        timeElapsed.reset();
    }
}