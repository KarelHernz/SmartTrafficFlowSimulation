package controller;

import model.Intersection;
import model.Road;
import model.Sensor;
import model.TrafficLight;
import model.state.Green;
import model.state.Yellow;
import java.util.ArrayList;
import java.util.List;

public class AdaptiveCycle implements Strategy {
    @Override
    public void update(Intersection intersection) {
        List<Road> roadList = new ArrayList<>(intersection.getRoads());

        Road road1 = roadList.get(0);
        Road road2 = roadList.get(1);
        TrafficLight trafficLight1 = road1.getTrafficLights().getFirst();
        TrafficLight trafficLight2 = road2.getTrafficLights().getFirst();

        //Obtém o road que tem os semáforos em verde, se fossem verdes
        Road greenRoad = (trafficLight1.getState() instanceof Green) ? road1 :
                (trafficLight2.getState() instanceof Green ? road2 : null);

        //Obtém o road que tem os semáforos em amarelo, se fossem amarelos
        Road yellowRoad = (trafficLight1.getState() instanceof Yellow) ? road1 :
                (trafficLight2.getState() instanceof Yellow ? road2 : null);

        if (yellowRoad != null) {
            TrafficLight yellowLight = yellowRoad.getTrafficLights().getFirst();
            if (yellowLight.getTime() >= YELLOW_DURATION) {
                //Muda o estado dos semáforos que estão de cor vermelho para verde
                Road redRoad = (yellowRoad == road1) ? road2 : road1;
                for(TrafficLight trafficLight: redRoad.getTrafficLights()){
                    trafficLight.setGreen();
                }
                //e os de cor amarelo para vermelho
                for(TrafficLight trafficLight: yellowRoad.getTrafficLights()){
                    trafficLight.setRed();
                }
                return;
            }
            return;
        }

        if (greenRoad == null) return;

        Road redRoad = (greenRoad == road1) ? road2 : road1;
        Sensor redRoadSensor = redRoad.getSensor();

        //Valida se os roads atingiram o seu limite e muda o estado dos seus semáforos para verde
        int LIMIT = 8;
        if (redRoadSensor.countAllStoppedVehicles() >= LIMIT) {
            for (TrafficLight tl : greenRoad.getTrafficLights()) {
                tl.setYellow();
            }
        }
    }
}