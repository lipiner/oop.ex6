package oop.ex6.validity.command_validity;

import oop.ex6.validity.CompilingException;
import oop.ex6.validity.ScopeChecker;
import oop.ex6.validity.Variable;
import oop.ex6.validity.VariableWrapper;

import java.util.LinkedList;

public class DefiningBlock extends CommandLine {

    private LinkedList<String> conditionVariables;

    public DefiningBlock (LinkedList<String> variables){
        conditionVariables = variables;
    }

    @Override
    public void check(ScopeChecker scope) throws CompilingException{
        for (String variableName: conditionVariables){
            checkVariable(variableName, scope);

        }
    }

    private void checkVariable (String variableName, ScopeChecker scope) throws CompilingException {
        VariableWrapper variable = scope.getVariable(variableName);

        // the variable does not exist
        if (variable == null)
            throw new CompilingException();
        // the variable type is wrong
        else if (variable.getType().equals(Variable.Type.CHAR) || variable.getType().equals(Variable.Type.STRING)) // WTF?
            throw new CompilingException();
        // the variable is not assigned
        else if (!variable.isAssigned())
            throw new CompilingException();
    }
}
