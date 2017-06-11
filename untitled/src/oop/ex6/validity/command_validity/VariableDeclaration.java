package oop.ex6.validity.command_validity;

import oop.ex6.validity.CompilingException;
import oop.ex6.validity.ScopeChecker;
import oop.ex6.validity.Variable;

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
        variableValue = null; // SHOULD I SO THIS?
    }


    public void check(ScopeChecker scope) throws CompilingException{
        Variable.Type type = Variable.Type.valueOf(variableType);
        Variable newVariable = new Variable(variableName, type, isMethodParameter, isFinal); // add scope.isGlobal!

        if (!isMethodParameter && variableValue != null)
            newVariable.assign(variableValue, scope, this);

        scope.addVariable(newVariable);
        //if (type.equals(Variable.Type.CHAR) || type.equals(Variable.Type.STRING)){

        //}

    }
}
