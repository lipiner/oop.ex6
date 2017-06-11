package oop.ex6.validity.command_validity;

import oop.ex6.validity.ScopeChecker;
import oop.ex6.validity.Variable;

import java.util.LinkedList;

public class DefiningBlock extends CommandLine {

    private LinkedList<String> conditionVariables;

    public DefiningBlock (LinkedList<String> variables){
        conditionVariables = variables;
    }

    @Override
    public void check(ScopeChecker scope) {
        for (String variableName: conditionVariables){
            Variable variable = scope.getVariable(variableName);
        }
    }
}
