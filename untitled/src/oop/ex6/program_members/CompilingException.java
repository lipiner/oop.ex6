package oop.ex6.program_members;

public class CompilingException extends Exception {

    /**
     * Constructor for a CompilingException
     */
    public CompilingException(){
        super();
    }

    /**
     * CompilingException
     * @param message a message with details about exception
     */
    public CompilingException(String message){
        super(message);
    }
}
