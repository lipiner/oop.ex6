package oop.ex6.validity;

import oop.ex6.program_members.*;
import java.util.Iterator;
import java.util.LinkedList;

/**
 * The class represents a code line of method call
 */
public class CallingMethod extends CommandLine {
    private String methodName;
    private LinkedList <String> methodInput;
    private static final String METHOD_NOT_FOUND_MSG = "Invalid method call: the method name does not exist",
        INVALID_PARAMETER_NUMBER = "Invalid method call: wrong number of parameters";


    /**
     * constructs a new CallingMethod instance, when getting the name of the called method and the inputs
     * @param methodName the name of the called method
     * @param methodInput the given values of the call
     */
    CallingMethod(String methodName, LinkedList<String> methodInput) {
        this.methodName = methodName;
        this.methodInput= methodInput;
    }

    @Override
    public void check(ScopeChecker scope) throws CompilingException {
        Method method = scope.getMethod(methodName);
        // the method does not exist
        if (method == null)
            throw new CompilingException(METHOD_NOT_FOUND_MSG);

        LinkedList<VariableWrapper> methodVariables = method.getMethodVariables();

        // wrong number of method inputs
        if (methodVariables.size() != methodInput.size())
            throw new CompilingException(INVALID_PARAMETER_NUMBER);

        Iterator<VariableWrapper> variableIterator = methodVariables.listIterator();
        Iterator<String> inputIterator = methodInput.listIterator();

        // checks if the given input suits the method parameter
        while (variableIterator.hasNext()) {
            VariableWrapper methodVariable = variableIterator.next();
            String input = inputIterator.next();

            Assigning assignLine = new Assigning(methodVariable, input);
            VariableWrapper assignVariable = assignLine.getAssigningVariable(scope);
            if (assignVariable != null)
                methodVariable.checkAssigningValue(assignVariable);
            else
                // the assigning value is not a variable
                methodVariable.checkAssigningValue(input);
        }
    }
}
