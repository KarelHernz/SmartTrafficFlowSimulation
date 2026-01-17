package controller;

import model.TrafficLight;

public interface State {
    //muda de cor e reinicia o temporizador
    void update(TrafficLight trafficLight);
}