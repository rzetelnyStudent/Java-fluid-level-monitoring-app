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
 * Listener class for a button that enables changing IN1 stream flow value.
 *
 * @author Michał Kapuściński
 * @version 3.0
 */
public class SetIn1ButtonListener implements ActionListener {

    /**
     * Reference to a main controller class
     */
    private final TankController tankController;

    /**
     * Constructor that references main controller class.
     *
     * @param tankController reference to main tankcontroller class.
     */
    public SetIn1ButtonListener(TankController tankController) {
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
            String in1Flow = JOptionPane.showInputDialog(null, "Set IN1 flow value:");
            tankController.setModelValueIn1(Double.parseDouble(in1Flow));
        } catch (InvalidArgumentException | NullPointerException | NumberFormatException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
    }
}
