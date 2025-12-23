package controller;

import model.Intersection;
import model.Road;
import model.Sensor;
import model.TrafficLight;
import model.state.Green;
import model.state.Yellow;

import java.util.ArrayList;
import java.util.List;

public class AdaptiveCycle implements Strategy{
    private static final int LIMITE = 10;

    @Override
    public void apply(Intersection intersection) {
        List<Road> roadList = new ArrayList<>(intersection.getRoads().keySet());
        Road road1 = roadList.get(0);
        Road road2 = roadList.get(1);

        //Procura qual dos dois é o road que está vermelho e verde
        TrafficLight trafficLight1 = intersection.getTrafficLights(road1).getFirst();
        Road redRoad = trafficLight1.getCor() == TrafficLight.Cor.RED ? road1 : road2;
        Road greenRoad = redRoad == road1 ? road2 : road1;

        //Faz a mudança dos estados do semáforo quando atingir o limite de veiculos à espera
        Sensor sensor = intersection.getSensor(redRoad);
        if (sensor.countAllVehicles() >= LIMITE) {
            for (TrafficLight trafficLight : intersection.getTrafficLights(redRoad)) {
                trafficLight.setState(new Green());
            }

            for (TrafficLight trafficLight : intersection.getTrafficLights(greenRoad)) {
                trafficLight.setState(new Yellow());
            }
        }
    }
}