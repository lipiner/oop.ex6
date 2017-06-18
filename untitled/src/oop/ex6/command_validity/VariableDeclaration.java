package oop.ex6.command_validity;

import oop.ex6.program_members.CompilingException;
import oop.ex6.program_members.ScopeChecker;
import oop.ex6.program_members.Variable;
import oop.ex6.program_members.VariableWrapper;

/**
 * The class represents a code line that declares a variable
 */
public class VariableDeclaration extends CommandLine {

    private String variableType, variableName, variableValue;
    private boolean isFinal;

    /**
     * constructs a new VariableDeclaration instance when given all the details about the declaration
     * @param type the type of the declared variable
     * @param isFinal boolean value the determines if the variable is final
     * @param name the name of the declared variable
     * @param value the value assigned to the variable while declaration
     */
    public VariableDeclaration(String type, boolean isFinal, String name, String value){
        variableType = type;
        variableValue = value;
        variableName = name;
        this.isFinal = isFinal;
    }


    public void check(ScopeChecker scope) throws CompilingException{
        // creates the new variable
        VariableWrapper newVariable = new VariableWrapper(new Variable(variableName, variableType, isFinal));
        scope.addVariable(newVariable);

        // assigns variables that were assigned during declarations
        if (variableValue != null) {
            Assigning assignLine = new Assigning(newVariable, variableValue);
            assignLine.check(scope);
        }
    }

    /**
     * an overload of the regular check method. This method only creates the variable according to the given details
     * and returns it
     * @return the newly created variable
     */
    VariableWrapper check() {
        return new VariableWrapper(new Variable(variableName, variableType, isFinal));
    }
}
