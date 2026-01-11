module Smart.Traffic.Flow.Simulation {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.junit.jupiter.api;
    requires java.desktop;
    requires com.fasterxml.jackson.databind;

    opens util to com.fasterxml.jackson.databind;
    exports util;

    opens view;
}