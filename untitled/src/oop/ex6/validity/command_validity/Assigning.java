package oop.ex6.validity.command_validity;


import oop.ex6.validity.ScopeChecker;

public class Assigning extends CommandLine {

    public Assigning(String variableName, String value){
    }

    @Override
    boolean check(ScopeChecker scope) {
        return false;
    }
}
