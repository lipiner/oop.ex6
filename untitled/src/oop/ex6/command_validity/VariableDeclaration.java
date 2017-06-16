package oop.ex6.command_validity;

import oop.ex6.program_members.CompilingException;
import oop.ex6.program_members.ScopeChecker;
import oop.ex6.program_members.VariableWrapper;

public class VariableDeclaration extends CommandLine {

    private String variableType, variableName, variableValue;
    private boolean isFinal, isMethodParameter;


    public VariableDeclaration(String type, boolean isFinal, String name, String value){
        variableType = type;
        variableValue = value;
        variableName = name;
        this.isFinal = isFinal;
        isMethodParameter = false;
    }

    public VariableDeclaration(String type, boolean isFinal, String name) {
        variableType = type;
        variableName = name;
        this.isFinal = isFinal;
        isMethodParameter = true;
    }


    public void check(ScopeChecker scope) throws CompilingException{
        VariableWrapper newVariable = scope.addVariable(variableName, variableType, isFinal);

        if (isMethodParameter)
            scope.assignVariable(newVariable);
        else if (variableValue != null) {
            Assigning assignLine = new Assigning(newVariable, variableValue);

            assignLine.check(scope);
        }

    }
}