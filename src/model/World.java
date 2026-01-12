package model;

import controller.FixedCycle;
import controller.Strategy;
import javafx.animation.AnimationTimer;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import model.state.Green;
import model.state.Red;
import util.Coordinates;
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

    public World(ControlPanel controlPanel, ArrayList<ImageView> imagesViews, Statistics statistics){
        this.controlPanel = controlPanel;

        //Cria um HashMap de imagens dos veículos
        vehicleImages.put("Blue", new Image(Objects.requireNonNull(getClass().getResourceAsStream("/img/blue_car.png"))));
        vehicleImages.put("Golden", new Image(Objects.requireNonNull(getClass().getResourceAsStream("/img/golden_car.png"))));
        vehicleImages.put("Purple", new Image(Objects.requireNonNull(getClass().getResourceAsStream("/img/purple_car.png"))));
        vehicleImages.put("Red", new Image(Objects.requireNonNull(getClass().getResourceAsStream("/img/red_car.png"))));
        vehicleImages.put("White", new Image(Objects.requireNonNull(getClass().getResourceAsStream("/img/white_car.png"))));

        /* Horizontal -
            Via 1: V1: x=510.0; V2: x=567.0; V3: x=624.0 | y=243.0
            Via 2: V1: x=510.0; V2: x=567.0; V3: x=624.0 | y=278.0
            Via 3: V1: x=119.0; V2: x=62.0; V3: x=5 | y=330.0
            Via 4: V1: x=119.0; V2: x=62.0; V3: x=5 | y=365.0

            Vertical -
            Via 1: V1: y=109.0; V2: y=52.0; V3: y= -5.0 | x=252.0
            Via 2: V1: y=109.0; V2: y=52.0; V3: y= -5.0 | x=287.0
            Via 3: V1: y=500.0; V2: y=557.0; V3: y= 614.0 | x=341.0
            Via 4: V1: y=500.0; V2: y=557.0; V3: y= 614.0 | x=376.0
        * */

        ArrayList<Coordinates> coordinatesArrayList = new ArrayList<>();
        //Coordenadas dos lines horizontais
        coordinatesArrayList.add(new Coordinates(660.0, 243.0, -30.0, 243.0));
        coordinatesArrayList.add(new Coordinates(660.0, 278.0, -30.0, 278.0));
        coordinatesArrayList.add(new Coordinates(-30.0, 330.0, 660.0, 330.0));
        coordinatesArrayList.add(new Coordinates(-30.0, 365.0, 660.0, 365.0));

        //Coordenadas dos lines verticais
        coordinatesArrayList.add(new Coordinates(252.0, -40.0, 252.0, 648.0));
        coordinatesArrayList.add(new Coordinates(287.0, -40.0, 287.0, 648.0));
        coordinatesArrayList.add(new Coordinates(341.0, 648.0, 341.0, -40.0));
        coordinatesArrayList.add(new Coordinates(376.0, 648.0, 376.0, -40.0));

        Red red = new Red();
        Green green = new Green();

        //Define o road vertical com os semáforos de cor vermelho
        ArrayList<TrafficLight> tlVerticalList = new ArrayList<>();
        tlVerticalList.add(new TrafficLight(red, null, imagesViews.getFirst(), imagesViews.get(1)));
        tlVerticalList.add(new TrafficLight(red, null, imagesViews.get(2), imagesViews.get(3)));

        //Define o road vertical com os semáforos de cor verde
        ArrayList<TrafficLight> tlHorizontalList = new ArrayList<>();
        tlHorizontalList.add(new TrafficLight(green, imagesViews.get(4), imagesViews.get(5), null));
        tlHorizontalList.add(new TrafficLight(green, imagesViews.get(6), imagesViews.get(7), null));

        //Cria os roads e o número de vias que este vai ter
        int numVias = 4;
        verticalRoad = new Road(numVias, tlVerticalList);
        horizontalRoad = new Road(numVias, tlHorizontalList);
        for(int via = 1; via <= numVias; via++){
            horizontalRoad.addCoords(via, coordinatesArrayList.get(via-1));
            verticalRoad.addCoords(via, coordinatesArrayList.get(numVias+(via-1)));
        }

        this.intersection = new Intersection(new FixedCycle());
        intersection.addRoad(verticalRoad);
        intersection.addRoad(horizontalRoad);

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
            //Atualiza cada segundo
            long SECONDS_NANOS = (long) (1000000000L / speed);
            long elapsedTime = currentTime - lastTime;

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
            long FRAMES_PER_SECOND = 30L;
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
        System.out.println(intersection.getStrategy());
    }

    //Método para inserir do método os veiculos no road
    private void updateRoad(Road road, Boolean isVertical){
        for (int v = 1; v <= road.getNVias(); v++) {
            //Cálculo do número de veiculos no road
            int numVehicles = road.getNumberOfVehicles(v);
            int limitOfVehicles = road.getMaxVehiclesStoped();
            int vehiclesRemainder = limitOfVehicles - numVehicles;
            //----------------------------------------------------//

            //Se ouver espaço para mais um veiculo no road, gera de forma aleatória um novo veiculo
            if (vehiclesRemainder > 0) {
                //Devolve um número entre 0 e 1
                int numRandom = (int) (Math.random() * 2);
                for (int i = 0; i < numRandom; i++) {
                    Vehicle vehicle = generateVehicle(v, isVertical);
                    road.addVehicle(v, vehicle);
                }

                //Atualiza o contador da direção onde aparecem os veiculos nas estadisticas
                String direction = getDirection(isVertical, v);
                int result = statistics.getIntersectionValue(direction);
                statistics.updateIntersection(direction, result+numRandom);
            }
        }
    }

    //Obtem em qual direção apareceu o veículo
    private String getDirection(boolean isVertical, int nVia){
        if (isVertical){
            return (nVia == 1 || nVia == 2) ? "Top" : "Bottom";
        }

        return (nVia == 1 || nVia == 2)  ? "Right" : "Left";
    }

    //Método para gerar os veiculos
    private Vehicle generateVehicle(Integer nVia, Boolean isVertical){
        //Cores que vão ter os veículos
        String[] vehicleColors = {"Blue", "Golden", "Purple", "Red", "White"};
        int nColors = vehicleColors.length;

        //Escolhe de forma aleatória uma cor para o veículo
        int indexColor = (int)(Math.random() * nColors);
        String color = vehicleColors[indexColor];

        //Devolve uma imagem do array de images dos veículos em base à cor do veículo
        Image image = vehicleImages.get(color);
        ImageView imageViewVehicle = new ImageView();
        imageViewVehicle.setImage(image);

        //O LineCoords tem as coordenadas dos lines que aparecem no ImageView do mapa
        Coordinates viaLineCords = (isVertical) ? verticalRoad.getCoordinates(nVia) : horizontalRoad.getCoordinates(nVia);

        //Define a posição na qual vai aparecer a imageView
        imageViewVehicle.setLayoutX(viaLineCords.getLayoutX());
        imageViewVehicle.setLayoutY(viaLineCords.getLayoutY());

        //Configura o tamanho da imagemView
        imageViewVehicle.setFitWidth(27);
        imageViewVehicle.setFitHeight(49);

        //Rota a imageView em base ao valor do isVertical e o número da via
        if (isVertical){
            if (nVia == 3 || nVia == 4){
                imageViewVehicle.setRotate(180.0);
            }
        }
        else {
            if (nVia == 1 || nVia == 2){
                imageViewVehicle.setRotate(90.0);
            } else {
                imageViewVehicle.setRotate(270.0);
            }
        }
        controlPanel.addImageView(imageViewVehicle);
        int result = statistics.getVehiclesValue(color);
        statistics.updateVehiclesColors(color, ++result);

        /* Os veículos vão numa única direção dos eixos X e Y:
           X - Quando o veículo vai numa road em horizontal
           Y - Quando o veículo vai numa road em vertical */
        HashMap<String, Double> destino = new HashMap<>();
        destino.put("X", viaLineCords.getDestinationX());
        destino.put("Y", viaLineCords.getDestinationY());

        return new Vehicle(imageViewVehicle, destino);
    }

    public void reset(){
        intersection.reset();
        statistics.reset();
        timeElapsed.reset();
    }
}