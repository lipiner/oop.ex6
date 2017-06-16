package oop.ex6.program_members;

public class CallingClosedScopeException extends RuntimeException {

    CallingClosedScopeException(){
        super();
    }

    CallingClosedScopeException(String message){
        super(message);
    }
}
