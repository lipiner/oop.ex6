package oop.ex6.command_validity;

import oop.ex6.program_members.*;

import java.util.LinkedList;

public class ConditionBlock extends CommandLine {

    private LinkedList<String> conditionVariables;
    private static final String
            CONDITION_NOT_FOUND_MSG = "Invalid condition: the variable used as condition does not exist",
            INVALID_CONDITION_TYPE_MSG = "Invalid condition: the variable used as condition is from wrong type",
            CONDITION_VARIABLE_EMPTY_MSG = "Invalid condition: the variable used as condition not assigned";


    public ConditionBlock(LinkedList<String> variables){
        conditionVariables = variables;
    }

    @Override
    public void check(ScopeChecker scope) throws CompilingException{
        for (String variableName: conditionVariables){
            checkVariable(variableName, scope);
        }

        // opening new scope
        ScopeChecker newScope = new LocalScope(scope);
    }

    private void checkVariable (String variableName, ScopeChecker scope) throws CompilingException {
        VariableWrapper variable = scope.getVariable(variableName);

        // the variable does not exist
        if (variable == null)
            throw new CompilingException(CONDITION_NOT_FOUND_MSG);
        // the variable type is wrong
        else if (!variable.canBeBoolean())
            throw new CompilingException(INVALID_CONDITION_TYPE_MSG);
        // the variable is not assigned
        else if (!variable.isAssigned())
            throw new CompilingException(CONDITION_VARIABLE_EMPTY_MSG);
    }
}
