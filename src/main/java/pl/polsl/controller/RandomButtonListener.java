/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pl.polsl.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import pl.polsl.view.ToggleSwitch;

/**
 * Listener class for a button that turns on random generated values.
 *
 * @author Michał Kapuściński
 * @version 3.0
 */
public class RandomButtonListener implements ActionListener {

    /**
     * Reference to a main controller class
     */
    private final TankController tankController;

    /**
     * Constructor that references main controller class.
     *
     * @param tankController reference to main tankcontroller class.
     */
    public RandomButtonListener(TankController tankController) {
        this.tankController = tankController;
    }

    /**
     * The action which is taken after clicking the button. Saves random values
     * - ON in approperiate field in main controller.
     *
     * @param ae are the registered events
     */
    @Override
    public void actionPerformed(ActionEvent ae) {
        tankController.setToggleSwitch(ToggleSwitch.RANDOM);
    }
}
