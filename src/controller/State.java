package controller;

import model.TrafficLight;

public interface State {
    double TEMPO = 10.0;

    //muda de cor e reinicia o temporizador
    void enter(TrafficLight trafficLight);

    //muda o estado atual por outro
    void update(TrafficLight trafficLight, double deltaTime);
}
