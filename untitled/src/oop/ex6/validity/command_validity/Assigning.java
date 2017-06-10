package oop.ex6.validity.command_validity;


import oop.ex6.validity.ScopeChecker;

public class Assigning extends CommandLine {
    private String variableName, value;

    public Assigning(String variableName, String value){
        this.variableName = variableName;
        this.value = value;
    }

    @Override
    public void check(ScopeChecker scope) {
    }
}
