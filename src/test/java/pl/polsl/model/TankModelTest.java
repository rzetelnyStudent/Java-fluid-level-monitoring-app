/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package pl.polsl.model;

import java.util.ArrayList;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

/**
 * Test class for testing model class.
 *
 * @author Michał Kapuściński
 * @version 3.0
 */
public class TankModelTest {

    /**
     * Constructor.
     */
    public TankModelTest() {
    }

    /**
     * Test of updateFluidHeight method, of class TankModel. Initializes the
     * model and checks if fluid height update is calculated correctly.
     *
     * @param radius tank base radius (should be > 0.0). Here
     * calculateBaseArea() is not tested for invalid values. (It is tested
     * exclusively in testCalculateBaseArea() against negative or =0 radiuses.)
     */
    @ParameterizedTest
    @ValueSource(doubles = {1.0, 2.0, 3.0, 4.0, 5.0})
    public void testUpdateFluidHeight(double radius) {
        
        TankModel instance = new TankModel();
        instance.setCurrentHeight(0.0);
        try {
            instance.calculateBaseArea(1.0);     // needed for later calculations
        } catch (InvalidArgumentException e) {
            fail("The exception has been thrown, base area hasn't been calculated.");
        }
        for (int i = 1; i <= 3; i++) {     // test the equation with stream values from 0 to 3

            try {
                instance.setCurrentIn1LMin(Double.valueOf(i));
                instance.setCurrentIn2LMin(Double.valueOf(i));
                instance.setCurrentOutLMin(Double.valueOf(i));
            } catch (InvalidArgumentException e) {
                fail("The exception has been thrown, stream flow value hasn't been calculated.");
            }
            double expected = instance.getCurrentHeight() + (instance.getCurrentIn1LMin() + instance.getCurrentIn2LMin()
                    - instance.getCurrentOutLMin()) / (instance.getBaseArea()) * 0.001 * (1.0 / 60.0);
            instance.updateFluidHeight();
            double actual = instance.getCurrentHeight();
            assertEquals(expected, actual, 0.0001, "Tested method calculates fluid level incorrectly");
        }

        for (int i = 1; i <= 3; i++) {     // test equation with a bit different values

            try {
                instance.setCurrentIn1LMin(Double.valueOf(i));
                instance.setCurrentIn2LMin(Double.valueOf(4 - i));
                instance.setCurrentOutLMin(Double.valueOf(4 - i));
            } catch (InvalidArgumentException e) {
                fail("The exception has been thrown, stream flow value hasn't been calculated.");
            }
            double expected = instance.getCurrentHeight() + (instance.getCurrentIn1LMin() + instance.getCurrentIn2LMin()
                    - instance.getCurrentOutLMin()) / (instance.getBaseArea()) * 0.001 * (1.0 / 60.0);
            instance.updateFluidHeight();
            double actual = instance.getCurrentHeight();
            assertEquals(expected, actual, 0.0001, "Tested method calculates fluid level incorrectly");
        }
    }

    /**
     * Test of calculateBaseArea method, of class TankModel. Tests calculations
     * and if there's an exception throw for incorrect parameters (radius <= 0).
     *
     * @param start radius value that test loops from (have to be < 0).
     * @param end radius value that test loops to (have to be > 0).
     */
    @ParameterizedTest
    @CsvSource({"-1000, 1000"})
    public void testCalculateBaseArea(int start, int end) {
        
        TankModel instance = new TankModel();
        

        for (int i = start; i <= 0; i++) {
            double radiusArgument = Double.valueOf(i);
            Throwable exception = assertThrows(InvalidArgumentException.class, () -> instance.calculateBaseArea(radiusArgument));
            assertEquals("Radius have to be >0", exception.getMessage(), "Test failed, the exception hasn't been thrown, where it was expected");
        }

        for (int i = 1; i < end; i++) {
            double radius = Double.valueOf(i);
            double expResult = 2 * Math.PI * radius * radius;
            try {
                instance.calculateBaseArea(radius);
            } catch (InvalidArgumentException e) {
                fail("The exception has been thrown, where it shouldn't be.");
            }

            double actual = instance.getBaseArea();
            assertEquals(expResult, actual, 0.0001, "Tested method calculates area incorrectly");
        }
    }

    /**
     * Test of calculateMinHeight method, of class TankModel. Initializes model
     * with fake historic data and checks if the minimal height is calculated
     * correctly.
     *
     * @param maxSize size of the tested arrayList
     */
    @ParameterizedTest
    @ValueSource(ints = {0, 10, 25, 50, 100})
    public void testCalculateMinHeight(int maxSize) {

        if (maxSize == 0) {     // test with arrayList not set (null value)
            TankModel instance = new TankModel();
            double expected = 0.0;
            double actual = instance.calculateMinHeight();
            assertEquals(expected, actual, 0.0001, "Minimal height is calculated incorrectly");
        } else {
            TankModel instance = new TankModel();
            ArrayList<Double> historicHeight = new ArrayList<>();
            for (int i = 0; i < maxSize; i++) {
                historicHeight.add(Double.valueOf(i));
            }
            instance.setHistoricHeight(historicHeight);
            double min = historicHeight.get(0);
            for (double i : historicHeight) {
                min = min < i ? min : i;
            }
            double expected = min;
            double actual = instance.calculateMinHeight();
            assertEquals(expected, actual, 0.0001, "Minimal height is calculated incorrectly");
        }
    }

    /**
     * Test of calculateMaxHeight method, of class TankModel. Initializes model
     * with fake historic data and checks if the maximal height is calculated
     * correctly.
     *
     * @param maxSize size of the tested arrayList.
     */
    @ParameterizedTest
    @ValueSource(ints = {0, 10, 25, 50, 100})
    public void testCalculateMaxHeight(int maxSize) {

        if (maxSize == 0) {     // test with arrayList not set (null value)
            TankModel instance = new TankModel();
            double expected = 0.0;
            double actual = instance.calculateMaxHeight();
            assertEquals(expected, actual, 0.0001, "Maximal height is calculated incorrectly");
        } else {
            TankModel instance = new TankModel();
            ArrayList<Double> historicHeight = new ArrayList<>();
            for (int i = 0; i < maxSize; i++) {
                historicHeight.add(Double.valueOf(i));
            }
            instance.setHistoricHeight(historicHeight);
            double max = historicHeight.get(0);
            for (double i : historicHeight) {
                max = max > i ? max : i;
            }
            double expected = max;
            double result = instance.calculateMaxHeight();
            assertEquals(expected, result, 0.0001, "Maximal height is calculated incorrectly");
        }
    }

    /**
     * Test of calculateAverageHeight method, of class TankModel. Initializes
     * model with fake historic data and checks if the average height is
     * calculated correctly.
     *
     * @param maxSize size of the tested arrayList.
     */
    @ParameterizedTest
    @ValueSource(ints = {0, 10, 25, 50, 100})
    public void testCalculateAverageHeight(int maxSize) {

        if (maxSize == 0) {     // test with arrayList not set (null value)
            TankModel instance = new TankModel();
            double expected = 0.0;
            double actual = instance.calculateAverageHeight();
            assertEquals(expected, actual, 0.0001, "Average height is calculated incorrectly");
        } else {
            TankModel instance = new TankModel();
            ArrayList<Double> historicHeight = new ArrayList<>();
            for (int i = 0; i < maxSize; i++) {
                historicHeight.add(Double.valueOf(i));
            }
            double average = 0.0;
            double sum = 0.0;
            if (!historicHeight.isEmpty()) {
                for (Double height : historicHeight) {
                    sum += height;
                }
                average = sum / historicHeight.size();
            }
            instance.setHistoricHeight(historicHeight);
            double result = instance.calculateAverageHeight();
            assertEquals(average, result, 0.0001, "Average height is calculated incorrectly");
        }
    }
}
