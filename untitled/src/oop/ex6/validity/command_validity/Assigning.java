package oop.ex6.validity.command_validity;

import oop.ex6.validity.CompilingException;
import oop.ex6.validity.ScopeChecker;
import oop.ex6.validity.Variable;

public class Assigning extends CommandLine {
    private String variableName, value;

    public Assigning(String variableName, String value){
        this.variableName = variableName;
        this.value = value;
    }

    @Override
    public void check(ScopeChecker scope) throws CompilingException{
        Variable variable = scope.getVariable(variableName);
        if (variable == null)
            scope.addUnidentifiedCommand(this); /// CHECK THIS - WHEN CALLING FROM UNIDENTIFIED
        else{
            variable.assign(value, scope, this);
        }
    }
}
