package pl.mariuszlyszczarz;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.util.Duration;


import java.time.LocalTime;

/**
 * Controller dashboard.fxml
 * @author Mariusz Lyszczarz
 */
public class Controller {
    @FXML
    private Circle S_0_0, S_0_1, S_0_2, S_0_3, S_1_0, S_1_1, S_1_2, M_0_0, M_0_1, M_0_2, M_0_3, M_1_0, M_1_1, M_1_2, H_0_0, H_0_1, H_0_2, H_0_3, H_1_0, H_1_1;
    @FXML
    private Label clockLabel;

    @FXML
    public void initialize() {
        final Timeline timeline = new Timeline(
                new KeyFrame(
                        Duration.millis(1000),
                        event -> {
                            digitalClock();
                            binaryClock();
                        }
                )
        );
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();
    }

    /**
     * Method that changes the text in the label where the decimal clock is shown
     */
    void digitalClock() {
        LocalTime localTime = LocalTime.now();

        clockLabel.setText((localTime.getHour() < 10 ? "0" + localTime.getHour() : localTime.getHour())
                + ":" + (localTime.getMinute() < 10 ? "0" + localTime.getMinute() : localTime.getMinute())
                + ":" + (localTime.getSecond() < 10 ? "0" + localTime.getSecond() : localTime.getSecond()));
    }

    /**
     * Method that changes color of circle in binary clock
     */
    void binaryClock() {
        LocalTime localTime = LocalTime.now();
        Circle[] secFirstColumn = {S_0_3, S_0_2, S_0_1, S_0_0};
        Circle[] secSecondaryColumn = {S_1_2, S_1_1, S_1_0};
        Circle[] minFirstColumn = {M_0_3, M_0_2, M_0_1, M_0_0};
        Circle[] minSecondaryColumn = {M_1_2, M_1_1, M_1_0};
        Circle[] hourFirstColumn = {H_0_3, H_0_2, H_0_1, H_0_0};
        Circle[] hourSecondaryColumn = {H_1_1, H_1_0};
        int sec = localTime.getSecond();
        int min = localTime.getMinute();
        int hour = localTime.getHour();

        fillColorBinaryCircle(secFirstColumn, secSecondaryColumn, sec);
        fillColorBinaryCircle(minFirstColumn, minSecondaryColumn, min);
        fillColorBinaryCircle(hourFirstColumn, hourSecondaryColumn, hour);


    }

    /**
     * Method that change color of circle in single section
     * @param tableFirstColumn table contains circle represents unit
     * @param tableSecondaryColumn table contains circle represents dozens
     * @param value value of part time from 0 to 59
     */
    private void fillColorBinaryCircle(Circle[] tableFirstColumn, Circle[] tableSecondaryColumn, int value) {
        if (value < 10) {
            fillColorOff(tableSecondaryColumn);
            changeColor(tableFirstColumn, value);
        } else {
            String string = "" + value;
            changeColor(tableFirstColumn, Integer.parseInt("" + string.charAt(1)));
            changeColor(tableSecondaryColumn, Integer.parseInt("" + string.charAt(0)));
        }
    }

    /**
     * Method that fill unused circle on basic color
     * @param table table of circles which should receive the basic color
     */
    private void fillColorOff(Circle[] table) {
        for (int i = 0; i < table.length; i++) {
            table[i].setFill(Color.GRAY);
        }
    }

    /**
     * Method that fill circle depends on binary value
     * @param table table of circle witch receive one of color
     * @param value single digit of segment time
     */
    private void changeColor(Circle[] table, int value) {
        String string = digitalToBinary(value);

        while (table.length != string.length()) string = "0" + string;
        for (int i = 0; i < table.length; i++) {
            if (string.charAt(i) == '1') table[i].setFill(Color.RED);
            else table[i].setFill(Color.GRAY);
        }
    }

    /**
     * Method changing decimal number to binary string
     * @param number numbet that should be converted to binary string
     * @return string of sequence binary
     */
    String digitalToBinary(int number) {
        StringBuilder string = new StringBuilder();

        if (number / 2 > 0) string.append(digitalToBinary(number / 2));
        string.append(number % 2);
        return string.toString();
    }
}
