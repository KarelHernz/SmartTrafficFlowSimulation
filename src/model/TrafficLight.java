package model;

import controller.State;
import javafx.scene.image.ImageView;
import model.state.Green;
import model.state.Red;
import model.state.Yellow;

import java.util.HashMap;
import java.util.Map;

public class TrafficLight {
    private State state;
    private Double tempo;

    public enum Color {
        Yellow, Red, Green
    }
    private Color cor;

    private Map<Color, ImageView> lights = new HashMap<>();

      public TrafficLight(ImageView imageView1, ImageView imageView2) {
        this.lights.put(Color.Green, imageView1);
        this.lights.put(Color.Red, imageView2);
        this.state = new Green();
        this.state.enter(this);

        updateVisibilidade();
    }

    public State getState() {
        return state;
    }

    public void setState(State novoState) {
        if (this.state != null){
            this.state.exit(this);
        }
        this.state = novoState;
        this.state.enter(this);
        this.state.update(this, 0.0);
    }

    public void addTempo(double deltaTime) {
          this.tempo += deltaTime;
    }
    public void resetTempo() {
        this.tempo = 0.0;
    }
    public void updateTempo(double deltaTime) {
          addTempo(deltaTime);

          if (this.state != null) {
              this.state.update(this, deltaTime);
          }
          updateVisibilidade();
    }
    public Color getCor(){
          return this.cor;
    }
    public void setColor(Color cor) {
          this.cor = cor;
          updateVisibilidade();
    }

    public void updateVisibilidade(){
        for (Color c : Color.values()){
            ImageView light = lights.get(c);
            if (light != null) {
                light.setVisible(c == this.cor);
            }
        }
    }
}

