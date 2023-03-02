/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pl.polsl.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;
import pl.polsl.model.InvalidArgumentException;

/**
 * Listener class for a button that enables changing radius value.
 *
 * @author Michał Kapuściński
 * @version 3.0
 */
public class SetRadiusButtonListener implements ActionListener {

    /**
     * Reference to a main controller class
     */
    private final TankController tankController;

    /**
     * Constructor that references main controller class.
     *
     * @param tankController reference to main tankcontroller class.
     */
    public SetRadiusButtonListener(TankController tankController) {
        this.tankController = tankController;
    }

    /**
     * The action which is taken after clicking the button. Asks user for a new
     * value of IN1 stream. If data is not valid, pops error message.
     *
     * @param ae are the registered events
     */
    @Override
    public void actionPerformed(ActionEvent ae) {

        try {
            String radius = JOptionPane.showInputDialog(null, "Set tank base radius [m]:");
            tankController.changeBaseArea(Double.parseDouble(radius));
        } catch (InvalidArgumentException | NullPointerException | NumberFormatException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
    }
}
