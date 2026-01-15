package controller;

import model.Intersection;

public interface Strategy {
    int YELLOW_DURATION = 8;

    void update(Intersection intersection);
}