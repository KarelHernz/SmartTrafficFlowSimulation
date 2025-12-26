package model;

import controller.FixedCycle;
import controller.Strategy;
import javafx.animation.AnimationTimer;
import javafx.scene.image.ImageView;
import model.state.Green;
import model.state.Red;

import java.util.ArrayList;

public class World{
    private final Intersection intersection;
    private final Strategy strategy;
    private float speed = 1.0f;

    public  World(ArrayList<ImageView> imagesViews, ImageView imageViewMapa){
        this.strategy = new FixedCycle();
        this.intersection = new Intersection(strategy);

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

        intersection.addRoad(new Road(4) ,tlVerticalList);
        intersection.addRoad(new Road(4) ,tlHorizontalList);

        strategy.apply(intersection);
        timer.start();
    }

    private final AnimationTimer timer = new AnimationTimer() {
        @Override
        public void handle(long xpto) {
            update(xpto);
        }
    };

    public void changeSpeed(float speed){
        if (speed < 0) {
            timer.stop();
            return;
        }

        this.speed = speed;
    }

    public void reset(){
        intersection.reset();
    }

    public void update(double deltaTime){
        intersection.update(deltaTime);
    }
}