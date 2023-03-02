/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pl.polsl.model;

import java.util.ArrayList;    // arraylist for storing historic data
import java.util.Collections;
import java.util.NoSuchElementException;

/**
 * Fluid level monitoring application - model class. Represents model in MVC
 * pattern.
 *
 * @author Michał Kapuściński
 * @version 3.0
 */
public class TankModel {

    /**
     * tank base area. Used for calculating fluid height.
     */
    private double baseArea;

    /**
     * current IN1 stream flow value in [litres/minute].
     */
    private double currentIn1LMin;

    /**
     * current IN2 stream flow value in [litres/minute].
     */
    private double currentIn2LMin;

    /**
     * current OUT stream flow value in [litres/minute].
     */
    private double currentOutLMin;

    /**
     * current fluid level height in [m].
     */
    private double currentHeight;

    /**
     * array for storing historic fluid level height data.
     */
    private ArrayList<Double> historicHeight = new ArrayList<>();

    /**
     * Constructor. Initializes TankModel class object
     */
    public TankModel() {
        currentIn1LMin = 0.06;    // equals 0.000001 [m3/s]
        currentIn2LMin = 0.06;    // equals 0.000001 [m3/s]
        currentOutLMin = 0.06;    // equals 0.000001 [m3/s]
        currentHeight = 0.5;      // [m]
        baseArea = 1.0;
    }

    /**
     * Method that updates height based on IN and OUT parameters. This method
     * should be called every 1s to update height in real-time.
     */
    public void updateFluidHeight() {
        // *0.001 converts from litres to m3                                                                                                         
        // *(1.0 / 60.0) converts from minutes to seconds
        currentHeight = currentHeight + (currentIn1LMin + currentIn2LMin - currentOutLMin) / (baseArea) * 0.001 * (1.0 / 60.0);
        historicHeight.add(currentHeight);
    }

    /**
     * Method that sets private baseArea, calculated from radius. Assuming the
     * tank is a cylindrical one.
     *
     * @param radius tank base radius
     * @throws InvalidArgumentException when input parameter radius is negative
     * or = 0
     */
    public void calculateBaseArea(double radius) throws InvalidArgumentException {
        if (radius <= 0) {
            throw new InvalidArgumentException("Radius have to be >0");
        }
        baseArea = 2 * Math.PI * radius * radius;
    }

    /**
     * Getter method for current fluid height.
     *
     * @return fluid height in [m]
     */
    public double getCurrentHeight() {
        return currentHeight;
    }

    /**
     * Getter method for base area.
     *
     * @return base area in [m2]
     */
    public double getBaseArea() {
        return baseArea;
    }

    /**
     * Setter method for current input IN1 stream value.
     *
     * @param In1LMin IN1 stream flow in [l/min]
     * @throws pl.polsl.model.InvalidArgumentException when argument is out of 1
     * - 3 range
     */
    public void setCurrentIn1LMin(double In1LMin) throws InvalidArgumentException {
        checkFlowParameter(In1LMin);
        currentIn1LMin = In1LMin;
    }

    /**
     * Setter method for current input IN2 stream value.
     *
     * @param In2LMin IN2 stream flow in [l/min]
     * @throws pl.polsl.model.InvalidArgumentException when argument is out of 1
     * - 3 range
     */
    public void setCurrentIn2LMin(double In2LMin) throws InvalidArgumentException {
        checkFlowParameter(In2LMin);
        currentIn2LMin = In2LMin;
    }

    /**
     * Setter method for current output OUT stream value
     *
     * @param OutLMin OUT stream flow in [l/min]
     * @throws pl.polsl.model.InvalidArgumentException when argument is out of 1
     * - 3 range
     */
    public void setCurrentOutLMin(double OutLMin) throws InvalidArgumentException {
        checkFlowParameter(OutLMin);
        currentOutLMin = OutLMin;
    }

    /**
     * Setter method for fluid height value
     *
     * @param height fluid height in [m]
     */
    public void setCurrentHeight(double height) {
        currentHeight = height;
    }

    /**
     * Getter method for current input IN1 stream value
     *
     * @return IN1 stream flow in [l/min]
     */
    public double getCurrentIn1LMin() {
        return currentIn1LMin;
    }

    /**
     * Getter method for current input IN2 stream value
     *
     * @return IN2 stream flow in [l/min]
     */
    public double getCurrentIn2LMin() {
        return currentIn2LMin;
    }

    /**
     * Getter method for current output OUT stream value
     *
     * @return OUT stream flow in [l/min]
     */
    public double getCurrentOutLMin() {
        return currentOutLMin;
    }

    /**
     * Getter method for historic height data.
     *
     * @return historicHeight array list.
     */
    public ArrayList getHistoricHeight() {
        return historicHeight;
    }

    /**
     * Setter method for historic height data.
     *
     * @param historicHeight An arrayList with historic data records.
     */
    public void setHistoricHeight(ArrayList<Double> historicHeight) {
        this.historicHeight = new ArrayList<>(historicHeight);
    }

    /**
     * Method that returns minimal fluid level height record from historic data.
     *
     * @return minimal fluid level height record
     */
    public double calculateMinHeight() {
        double minVal = 0.0;
        try {
            minVal = Collections.min(historicHeight);
        } catch (NoSuchElementException e) {    // when historicHeight is empty, an exception is thrown
        }
        return minVal;
    }

    /**
     * Method that returns maximal fluid level height record from historic data.
     *
     * @return maximal fluid level height record
     */
    public double calculateMaxHeight() {
        double maxVal = 0;
        try {
            maxVal = Collections.max(historicHeight);
        } catch (NoSuchElementException e) {    // when historicHeight is empty, an exception is thrown
        }
        return maxVal;
    }

    /**
     * Method that returns average fluid level height calculated from historic
     * data.
     *
     * @return average fluid level height
     */
    public double calculateAverageHeight() {
        return historicHeight.stream()
                .mapToDouble(d -> d)
                .average()
                .orElse(0.0);
    }

    /**
     * Method that checks if input parameter is in valid range (from 1 to 3).
     * Useful for checking fluid stream values, that has to be in range 1 - 3
     *
     * @param param input parameter to has to be checked if is in the range.
     * @throws InvalidArgumentException when input param is out of 1-3 range.
     */
    private void checkFlowParameter(double param) throws InvalidArgumentException {
        if (param < 1 || param > 3) {
            throw new InvalidArgumentException("Parameter has to be from 1 to 3");
        }
    }
}
