package oop.ex6.validity;

public class UnexpectedTypeException extends RuntimeException {

    UnexpectedTypeException(){
        super();
    }

    UnexpectedTypeException(String message){
        super(message);
    }
}
