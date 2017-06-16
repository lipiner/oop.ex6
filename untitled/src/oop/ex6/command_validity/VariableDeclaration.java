package oop.ex6.command_validity;

import oop.ex6.program_members.CompilingException;
import oop.ex6.program_members.ScopeChecker;
import oop.ex6.program_members.Variable;
import oop.ex6.program_members.VariableWrapper;

public class VariableDeclaration extends CommandLine {

    private String variableType, variableName, variableValue;
    private boolean isFinal;


    public VariableDeclaration(String type, boolean isFinal, String name, String value){
        variableType = type;
        variableValue = value;
        variableName = name;
        this.isFinal = isFinal;
    }


    public void check(ScopeChecker scope) throws CompilingException{
        VariableWrapper newVariable = new VariableWrapper(new Variable(variableName, variableType, isFinal));
        scope.addVariable(newVariable);
//        System.out.println("value in declaration: " + variableValue); ///////////////////////////////

        // assigns variables that were assigned during declarations
        if (variableValue != null) {
            Assigning assignLine = new Assigning(newVariable, variableValue);

            assignLine.check(scope);
        }
    }

    VariableWrapper check() {
        return new VariableWrapper(new Variable(variableName, variableType, isFinal));
    }
}
