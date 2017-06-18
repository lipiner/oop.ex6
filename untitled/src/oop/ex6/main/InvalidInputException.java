package oop.ex6.main;

/**
 * The exception represents an error in the user input
 */
public class InvalidInputException extends Exception {

    /**
     * a default constructor- does not get any parameters
     */
    InvalidInputException() {
        super();
    }

    /**
     * Constructor for a InvalidInputException
     * @param message a message with details about exception
     */
    InvalidInputException(String message) {
        super(message);
    }

}
