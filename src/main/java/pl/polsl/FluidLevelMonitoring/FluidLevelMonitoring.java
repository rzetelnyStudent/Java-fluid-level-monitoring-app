/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pl.polsl.FluidLevelMonitoring;

import pl.polsl.controller.TankController;
import pl.polsl.view.TankView;
import pl.polsl.model.TankModel;
import java.util.concurrent.TimeUnit;
import javax.swing.JOptionPane;

/**
 * Fluid level monitoring application - main class.
 *
 * @author Michał Kapuściński
 * @version 3.0
 */
public class FluidLevelMonitoring {

    /**
     * The correct count of command line parameters.
     */
    private static final int ARGS_COUNT = 5;

    /**
     * Time in [ms] between display updates. Changing this value requires changing updateFluidHeight() method in
     * model class
     */
    private static final long UPDATE_INTERVAL = 1000;

    /**
     * Main method, the program starts here.
     * @param args Command line parameters. 5 parameters should be passed, all
     * as decimals or integers, in following order: 
     * tank base radius [m], 
     * initial fluid level height [m], 
     * initial IN1 input stream flow value [l/min], 
     * initial IN2 input stream flow value [l/min], 
     * initial OUT output stream flow value [l/min]. 
     * Usage example: 0.5 0.5 1 2 2. Evolves to:
     * tank base radius = 0.5[m]
     * fluid level height = 0.5[m]
     * IN1 input stream flow value = 1[m]
     * IN2 input stream flow value = 2[l/min]
     * OUT output stream flow value = 2[l/min]
     */
    public static void main(String[] args) {

        TankModel tank = new TankModel();
        TankView view = new TankView();
        TankController controller = new TankController(tank, view);
        
        if (args.length == ARGS_COUNT) {
            controller.setModelValues(args);
        }
        controller.loadDataFromDb();
        controller.startGui();

        while (true) {
            controller.updateView();
            try {
                TimeUnit.MILLISECONDS.sleep(UPDATE_INTERVAL);   // makes pressure updates real-time
            } catch (InterruptedException e) {
                JOptionPane.showMessageDialog(null, e.getMessage());
            }
        }

    }

}
