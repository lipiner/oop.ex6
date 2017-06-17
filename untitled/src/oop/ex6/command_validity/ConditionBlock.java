package oop.ex6.command_validity;

import oop.ex6.program_members.*;
import java.util.LinkedList;

/**
 * The class represents a code line that starts a new condition block- if or while
 */
public class ConditionBlock extends CommandLine {

    // the conditions of the block execution which are variable names
    private LinkedList<String> conditionVariables;
    private static final String
            CONDITION_NOT_FOUND_MSG = "Invalid condition: the variable used as condition does not exist",
            INVALID_CONDITION_TYPE_MSG = "Invalid condition: the variable used as condition is from wrong type",
            CONDITION_VARIABLE_EMPTY_MSG = "Invalid condition: the variable used as condition not assigned";


    /**
     * constructs a new ConditionBlock instance
     * @param variables the condition of the block execution which are variable names
     */
    public ConditionBlock(LinkedList<String> variables){
        conditionVariables = variables;
    }

    @Override
    public void check(ScopeChecker scope) throws CompilingException{
        // checks all the variables
        for (String variableName: conditionVariables){
            checkVariable(variableName, scope);
        }

        // opening the new scope
        ScopeChecker newScope = new LocalScope(scope);
    }

    /**
     * checks if a given variable name is a valid condition
     * @param variableName the name of the input variable
     * @param scope the current scope
     * @throws CompilingException if the variable name is not a valid condition
     */
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
