package oop.ex6.program_members;

import oop.ex6.SyntaxChecker;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class VariableWrapper {

    private Variable variable;
    private boolean assigned;
    private static final String
            FINAL_VARIABLE_ASSIGNMENT_MSG = "Invalid assignment: assigning final variable after declaration",
            UNASSIGNED_VARIABLE_MSG = "Invalid assignment: assigning variable with unassigned variable",
            INVALID_ASSIGNMENT_TYPE = "Invalid assignment: assignment type doesn't match the variable type";


    /**
     * Creates a variable wrapper object that wraps a variable for specific scope assigning status.
     * @param variable the variable to wrap
     */
    public VariableWrapper(Variable variable){
        this.variable = variable;
        assigned = false;
    }

    /**
     * Change the assigning status.
     */
    void assign () {
        assigned = true;
        variable.assign();
    }

    /**
     * Change the assigning status with the given variable.
     * @param assignVariable the value of the variable. The variable that is being assign into
     * @throws CompilingException if the operation is invalid (the value cannot be assigned to the variable)
     */
    void assign (VariableWrapper assignVariable) throws CompilingException {
        if (!variable.canBeAssigned())
            throw new CompilingException(FINAL_VARIABLE_ASSIGNMENT_MSG);

        if (!assignVariable.isAssigned())
            throw new CompilingException(UNASSIGNED_VARIABLE_MSG);

        checkAssignmentTypeMatch(assignVariable.getType());

        assigned = true;
        variable.assign();
    }

    /**
     * Change the assigning status with the given value (as string).
     * @param value the value of the variable. It should be variable type as String
     * @throws CompilingException if the operation is invalid (the value cannot be assigned to the variable)
     */
    void assign(String value) throws CompilingException {
        if (!variable.canBeAssigned())
            throw new CompilingException(FINAL_VARIABLE_ASSIGNMENT_MSG);

        Variable.Type assignType = getInputType(value);
        checkAssignmentTypeMatch(assignType);

        assigned = true;
        variable.assign();
    }

    /**
     *
     * @param input
     * @return
     */
    private Variable.Type getInputType(String input) {
        if (isMatchedType(SyntaxChecker.STRING_VALUE_PATTERN, input))
            return Variable.Type.STRING;
        else if (isMatchedType(SyntaxChecker.INT_VALUE_PATTERN, input))
            return Variable.Type.INT;
        else if (isMatchedType(SyntaxChecker.DOUBLE_VALUE_PATTERN, input))
            return Variable.Type.DOUBLE;
        else if (isMatchedType(SyntaxChecker.CHAR_VALUE_PATTERN, input))
            return Variable.Type.CHAR;
        else // boolean
            return Variable.Type.BOOLEAN;
    }

    /**
     *
     * @param assignmentType
     * @throws CompilingException
     */
    private void checkAssignmentTypeMatch (Variable.Type assignmentType) throws CompilingException{
        if(variable.getType() == Variable.Type.DOUBLE) {
            if (assignmentType != Variable.Type.DOUBLE && assignmentType != Variable.Type.INT)
                throw new CompilingException(INVALID_ASSIGNMENT_TYPE);
        } else if (variable.getType() == Variable.Type.BOOLEAN) {
            if (assignmentType == Variable.Type.STRING || assignmentType == Variable.Type.CHAR)
                throw new CompilingException(INVALID_ASSIGNMENT_TYPE);
        } else {
            if (variable.getType() != assignmentType)
                throw new CompilingException(INVALID_ASSIGNMENT_TYPE);
        }
    }

    /**
     *
     * @param pattern
     * @param value
     * @return
     */
    private boolean isMatchedType(Pattern pattern, String value) {
        Matcher patternMatcher = pattern.matcher(value);

        return patternMatcher.matches();
    }

    /**
     * Checks if the variable has already assigned or not
     * @return true iff the variable has already assigned
     */
    public boolean isAssigned() {
        return assigned;
    }

    /**
     * @return the variable object
     */
    public Variable getVariable() {
        return variable;
    }

    /**
     * @return the variable's name
     */
    String getVariableName(){
        return variable.getName();
    }

    /**
     * @return the variable's type
     */
    private Variable.Type getType(){
        return variable.getType();
    }

    /**
     * @return true iff the variable type consists with boolean type (int, double or boolean)
     */
    public boolean canBeBoolean() {
        return variable.canBeBoolean();
    }
}
