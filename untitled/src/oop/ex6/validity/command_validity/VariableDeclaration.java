package oop.ex6.validity.command_validity;

import oop.ex6.validity.ScopeChecker;
import oop.ex6.validity.Variable;

public class VariableDeclaration extends CommandLine {

    private String variableType;
    private String variableName;
    private String variableValue;


    VariableDeclaration(String type, String name, String value){
        variableValue = value;
    }

    VariableDeclaration(String type, String name){
        this(type, name, null);
    }

    public void check(ScopeChecker scope){
        Variable.Type type = Variable.Type.valueOf(variableType);
        if (type.equals(Variable.Type.CHAR) || type.equals(Variable.Type.STRING)){

        }
    }
}
