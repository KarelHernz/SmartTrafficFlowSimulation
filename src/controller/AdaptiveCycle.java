package controller;

import model.Intersection;
import model.Road;
import model.Sensor;
import model.TrafficLight;
import java.util.ArrayList;

public class AdaptiveCycle implements Strategy {
    @Override
    public void update(Intersection intersection) {
        ArrayList<Road> roadList = intersection.getRoads();

        Road road1 = roadList.getFirst();
        Road road2 = roadList.get(1);
        TrafficLight trafficLight1 = road1.getTrafficLights().getFirst();
        TrafficLight trafficLight2 = road2.getTrafficLights().getFirst();

        //Obtém o road que tem os semáforos vermelhos
        Road redRoad = (trafficLight1.isRed()) ? road1 :
                       (trafficLight2.isRed() ? road2 : null);

        //Obtém o road que tem os semáforos em amarelo, se fossem amarelos
        Road yellowRoad = (trafficLight1.isYellow()) ? road1 :
                          (trafficLight2.isYellow() ? road2 : null);

        //Obtém o road que tem os semáforos em verde, se fossem verdes
        Road greenRoad = (trafficLight1.isGreen()) ? road1 :
                         (trafficLight2.isGreen() ? road2 : null);

        if (redRoad == null) return;

        if (yellowRoad != null) {
            TrafficLight yellowLight = yellowRoad.getTrafficLights().getFirst();
            if (yellowLight.getTime() >= YELLOW_DURATION) {
                //Muda o estado dos semáforos que estão de cor vermelho para verde
                for(TrafficLight trafficLight: redRoad.getTrafficLights()){
                    trafficLight.setGreen();
                }
                //e os de cor amarelo para vermelho
                for(TrafficLight trafficLight: yellowRoad.getTrafficLights()){
                    trafficLight.setRed();
                }
                return;
            }
        }

        if (greenRoad == null) return;

        Sensor redRoadSensor = redRoad.getSensor();
        //Valida se os roads atingiram o seu limite e muda o estado dos seus semáforos para amarelo
        int LIMIT = 10;
        if (redRoadSensor.countAllStoppedVehicles() >= LIMIT) {
            for (TrafficLight tl : greenRoad.getTrafficLights()) {
                tl.setYellow();
            }
        }
    }
}