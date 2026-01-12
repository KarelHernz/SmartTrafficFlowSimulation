package controller;

import model.Intersection;

public interface Strategy {
    int YELLOW_DURATION = 5;

    void update(Intersection intersection);
}