package oop.ex6.program_members;

public class CallingClosedScopeException extends RuntimeException {

    /**
     * Constructor for a CallingClosedScopeException
     */
    CallingClosedScopeException(){
        super();
    }

    /**
     * Constructor for a CallingClosedScopeException
     * @param message a message with details about exception
     */
    CallingClosedScopeException(String message){
        super(message);
    }
}
