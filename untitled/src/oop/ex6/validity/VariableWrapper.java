package oop.ex6.validity;

import oop.ex6.SyntaxChecker;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class VariableWrapper {

    private Variable variable;
    private boolean assigned;

    VariableWrapper(Variable variable){
//        super(name, type, isFinal, isGlobal);
        this.variable = variable;
        assigned = false;
    }

    /**
     * Change the assigning status.
     */
    void assign () {
        assigned = true;
    }

    /**
     * Change the assigning status with the given variable.
     * @param assignVariable the value of the variable. The variable that is being assign into
     * @throws CompilingException if the operation is invalid (the value cannot be assigned to the variable)
     */
    void assign (VariableWrapper assignVariable) throws CompilingException {
        if (!assigned && !assignVariable.isGlobal()) //WHAT FOR?? DO WE NEED GLOBAL???
            throw new CompilingException();
//        if (!assigned && assignVariable.isGlobal())
//            throw new ;

        // checking for matching types
        if(variable.getType().equals(Variable.Type.DOUBLE)) {
            if (!assignVariable.getType().equals(Variable.Type.DOUBLE) && !assignVariable.getType().equals(Variable.Type.INT))
                throw new CompilingException();
        } else if (variable.getType().equals(Variable.Type.BOOLEAN)) {
            if (!assignVariable.getType().equals(Variable.Type.DOUBLE) && !assignVariable.getType().equals(Variable.Type.INT)
                    && !assignVariable.getType().equals(Variable.Type.BOOLEAN))
                throw new CompilingException();
        } else {
            if (variable.getType() != assignVariable.getType())
                throw new CompilingException();
        }

        assigned = true;
    }

    /**
     * Change the assigning status with the given value (as string).
     * @param value the value of the variable. It should be variable type as String
     * @throws CompilingException if the operation is invalid (the value cannot be assigned to the variable)
     */
    void assign(String value) throws CompilingException{
        if (assigned && variable.isFinal())
            throw new CompilingException();

        if (isMatchedType(SyntaxChecker.STRING_VALUE, value)) {
            if (!variable.getType().equals(Variable.Type.STRING))
                throw new CompilingException();
        }
        else if (isMatchedType(SyntaxChecker.INT_VALUE, value)) {
            if (variable.getType().equals(Variable.Type.STRING) || variable.getType().equals(Variable.Type.CHAR))
                throw new CompilingException();
        }
        else if (isMatchedType(SyntaxChecker.DOUBLE_VALUE, value)) {
            if (!variable.getType().equals(Variable.Type.DOUBLE) && !variable.getType().equals(Variable.Type.BOOLEAN))
                throw new CompilingException();
        }
        else if (isMatchedType(SyntaxChecker.CHAR_VALUE, value)) {
            if (!variable.getType().equals(Variable.Type.CHAR))
                throw new CompilingException();
        }
        else if (isMatchedType(SyntaxChecker.BOOLEAN_VALUE, value)) {
            if (!variable.getType().equals(Variable.Type.BOOLEAN)) //SHOULD DO ELSE?
                throw new CompilingException();
        }

        assigned = true;
    }

    /**
     *
     * @param stringPattern
     * @param value
     * @return
     */
    private boolean isMatchedType(String stringPattern, String value) {
        Pattern pattern = Pattern.compile(stringPattern);
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
    public Variable.Type getType(){
        return variable.getType();
    }

    /**
     * @return true iff the variable is global
     */
    private boolean isGlobal(){
        return variable.isGlobal();
    }
}
