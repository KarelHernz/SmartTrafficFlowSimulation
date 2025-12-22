package controller;

import model.Intersection;

public interface Strategy {
    void apply(Intersection intersection);
}