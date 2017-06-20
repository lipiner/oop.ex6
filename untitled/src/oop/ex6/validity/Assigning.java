package oop.ex6.validity;

import oop.ex6.program_members.CompilingException;
import oop.ex6.program_members.ScopeChecker;
import oop.ex6.program_members.VariableWrapper;

import java.util.regex.Matcher;

/**
 * The class represents a code line of variable assignment
 */
public class Assigning extends CommandLine {
    private String variableName, value;
    private VariableWrapper variable;
    private static final String TRUE_VALUE = "true";
    private static final String FALSE_VALUE = "false";
    private static final String VARIABLE_NOT_FOUND_MSG = "Invalid assignment: the variable is not found";

    /**
     * creates a new Assigning instance, when given the name of the assigned variable and the value
     * @param variableName the name of the assigned variable
     * @param value the assigning value
     */
    public Assigning(String variableName, String value){
        this.variableName = variableName;
        this.value = value;
    }

    /**
     * creates a new Assigning instance, when given the object of the assigned variable and the value
     * @param variable the object of the assigned variable
     * @param value the assigning value
     */
    Assigning(VariableWrapper variable, String value){
        this.variable = variable;
        variableName = null;
        this.value = value;
    }

    @Override
    public void check(ScopeChecker scope) throws CompilingException{
        // getting variable for the assignment by variable name
        if (variableName != null)
            variable = scope.getVariable(variableName);

        // the assigned variable does not exist
        if (variable == null)
            throw new CompilingException(VARIABLE_NOT_FOUND_MSG);

        else {
            Matcher variableNameMatcher = SyntaxChecker.VARIABLE_NAME_PATTERN.matcher(value);

            // checks if the assigned value is a variable
            if (variableNameMatcher.matches()) {
                VariableWrapper assignVariable = scope.getVariable(value);
                if (assignVariable != null)
                    scope.assignVariable(variable, assignVariable);
                // the assigning variable does not exist
                else {
                    // the assigning variable is true/false so can be a boolean value as well
                    if (!isBooleanValue(value))
                        throw new CompilingException(VARIABLE_NOT_FOUND_MSG);
                    else
                        scope.assignVariable(variable, value);
                }
            }
            // the assigning value is not a variable
            else {
                scope.assignVariable(variable, value);
            }
        }
    }

    /**
     * checks if the given text is a boolean value- true or false
     * @param value the text to be checked
     * @return iff the text is matches boolean value
     */
    private boolean isBooleanValue(String value){
        return value.equals(TRUE_VALUE) || value.equals(FALSE_VALUE);
    }
}
