/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pl.polsl.controller;

import java.util.ArrayList;
import javax.swing.JOptionPane;
import pl.polsl.model.InvalidArgumentException;
import pl.polsl.model.TankModel;
import pl.polsl.view.TankView;
import pl.polsl.view.ToggleSwitch;

/**
 * Fluid level monitoring application - controller class. Represents controller
 * in MVC
 *
 * @author Michał Kapuściński
 * @version 3.0
 */
public class TankController {

    /**
     * Enum representing toggle switch position.
     */
    private ToggleSwitch toggleSwPos = ToggleSwitch.TYPED;

    /**
     * Setter method for changing toggle switch enum field.
     *
     * @param toggleSwPos toggle switch position value. Could RANDOM or TYPED.
     */
    public void setToggleSwitch(ToggleSwitch toggleSwPos) {
        this.toggleSwPos = toggleSwPos;
    }

    /**
     * Method that returns the position of the toggle switch. Could be TYPED
     * (that means tank parameters are set manually or RANDOM (that means tank
     * parameteres are random values).
     *
     * @return toggle switch position
     */
    public ToggleSwitch getToggleSwitchPos() {
        return toggleSwPos;
    }

    /**
     * Constant value configuring max flow value of IN and OUT valves.
     */
    public final double MAX_FLOW = 3.0;

    /**
     * Constant value configuring min flow value of IN and OUT valves.
     */
    public final double MIN_FLOW = 1.0;
    /**
     * Model class object representing model in MVC pattern.
     */
    private final TankModel model;
    /**
     * View class object representing view in MVC pattern.
     */
    private final TankView view;

    /**
     * Constructor. Initializes TankController class object
     *
     * @param model model class object
     * @param view view class object
     */
    public TankController(TankModel model, TankView view) {
        this.model = model;
        this.view = view;
    }

    /**
     * Method for setting values in model object. Designed to take the input
     * directly from main() parameters.
     *
     * @param args string arguments from main() parameters. Should be in
     * following order: tank base radius [m], initial fluid level height [m],
     * initial IN1 input stream flow value [l/min], initial IN2 input stream
     * flow value [l/min], initial OUT output stream flow value [l/min].
     */
    public void setModelValues(String[] args) {
        double radius = Double.parseDouble(args[0]);     // convert strings to doubles
        double height = Double.parseDouble(args[1]);
        double in1 = Double.parseDouble(args[2]);
        double in2 = Double.parseDouble(args[3]);
        double out = Double.parseDouble(args[4]);
        try {
            model.calculateBaseArea(radius);     // if radius is <= 0, an exception is thrown
            model.setCurrentIn1LMin(in1);
            model.setCurrentHeight(height);
            model.setCurrentIn2LMin(in2);
            model.setCurrentOutLMin(out);
        } catch (InvalidArgumentException | NullPointerException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
    }

    /**
     * Wrapper method for setting IN1 value in model object.
     *
     * @param currentIn1LMin IN1 stream flow value [l/min]
     * @throws InvalidArgumentException when parameter is out of range.
     */
    public void setModelValueIn1(double currentIn1LMin) throws InvalidArgumentException {
        model.setCurrentIn1LMin(currentIn1LMin);
    }

    /**
     * Wrapper method for setting IN2 value in model object.
     *
     * @param currentIn2LMin IN2 stream flow value [l/min]
     * @throws InvalidArgumentException when parameter is out of range.
     */
    public void setModelValueIn2(double currentIn2LMin) throws InvalidArgumentException {
        model.setCurrentIn2LMin(currentIn2LMin);
    }

    /**
     * Wrapper method for setting OUT value in model object.
     *
     * @param currentOUTLMin OUT stream flow value [l/min]
     * @throws InvalidArgumentException when parameter is out of range.
     */
    public void setModelValueOut(double currentOUTLMin) throws InvalidArgumentException {
        model.setCurrentOutLMin(currentOUTLMin);
    }

    /**
     * Wrapper method for recaluculating base area value in model object.
     *
     * @param radius tank base radius
     * @throws InvalidArgumentException when radius value is negative.
     */
    public void changeBaseArea(double radius) throws InvalidArgumentException {
        model.calculateBaseArea(radius);
    }

    /**
     * Method for generating random value from given range.
     *
     * @param from range min value
     * @param to range max value
     * @return generated random value within the range
     */
    private double getRandomFromTo(double from, double to) {
        return (((to - from) * Math.random()) + from);
    }

    /**
     * Method for loading historic tank data from database.
     */
    public void loadDataFromDb() {

    }

    /**
     * Method for gatting stored historic data out of model class.
     *
     * @return historicData Arraylist from model.
     */
    public ArrayList getDataBase() {
        return model.getHistoricHeight();
    }

    /**
     * Method for starting GUI main window.
     */
    public void startGui() {
        view.setController(this);
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                view.startWindow();
            }
        });
    }

    /**
     * Method for updating the display with new values. Should be called every
     * 1s for real-time results.
     */
    public void updateView() {
        model.updateFluidHeight();     // recalculate fluid height, based on tank data.
        view.updateCurrentHeight(model.getCurrentHeight());     // print newly calculated height on the display
        if (toggleSwPos == ToggleSwitch.RANDOM) {
            try {
                model.setCurrentIn1LMin(getRandomFromTo(MIN_FLOW, MAX_FLOW));
                model.setCurrentIn2LMin(getRandomFromTo(MIN_FLOW, MAX_FLOW));
                model.setCurrentOutLMin(getRandomFromTo(MIN_FLOW, MAX_FLOW));
            } catch (InvalidArgumentException e) {
                JOptionPane.showMessageDialog(null, e.getMessage());
            }
        }
        view.udpateIn1Val(String.valueOf(Math.round(model.getCurrentIn1LMin() * 100.0) / 100.0));       //round to 3 decimal places
        view.udpateIn2Val(String.valueOf(Math.round(model.getCurrentIn2LMin() * 100.0) / 100.0));
        view.udpateOutVal(String.valueOf(Math.round(model.getCurrentOutLMin() * 100.0) / 100.0));
        view.udpateAvgVal(String.valueOf(Math.round(model.calculateAverageHeight() * 100.0) / 100.0));
        view.udpateMinVal(String.valueOf(Math.round(model.calculateMinHeight() * 100.0) / 100.0));
        view.udpateMaxVal(String.valueOf(Math.round(model.calculateMaxHeight() * 100.0) / 100.0));
    }
}
