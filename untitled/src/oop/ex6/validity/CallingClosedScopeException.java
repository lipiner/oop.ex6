package oop.ex6.validity;

public class CallingClosedScopeException extends RuntimeException {

    CallingClosedScopeException(){
        super();
    }

    CallingClosedScopeException(String message){
        super(message);
    }
}
