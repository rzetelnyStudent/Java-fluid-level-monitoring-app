/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pl.polsl.model;

/**
 * Custom exception class. Represents an exception when invalid (aka negative)
 * arguments are passed to a function, that can only accept positive arguments
 *
 * @author Michał Kapuściński
 * @version 3.0
 */
public class InvalidArgumentException extends Exception {

    /**
     * Constructor initializes NegativeArgumentException class object.
     *
     * @param message text containing error description
     */
    public InvalidArgumentException(String message) {
        super(message);     // Call constructor of parent Exception
    }
}
