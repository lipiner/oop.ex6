package oop.ex6.command_validity;

import oop.ex6.program_members.*;

import java.util.Iterator;
import java.util.LinkedList;

public class CallingMethod extends CommandLine {
    private String methodName;
    private LinkedList <String> methodInput;
    private static final String METHOD_NOT_FOUND_MSG = "Invalid method call: the method name does not exist",
        INVALID_PARAMETER_NUMBER = "Invalid method call: wrong number of parameters";


    public CallingMethod(String methodName, LinkedList<String> methodInput) {
        this.methodName = methodName;
        this.methodInput= methodInput;
    }

    @Override
    public void check(ScopeChecker scope) throws CompilingException {
        Method method = GlobalMembers.getInstance().getMethod(methodName);
        if (method == null)
            throw new CompilingException(METHOD_NOT_FOUND_MSG);

        LinkedList<VariableWrapper> methodVariables = method.getMethodVariables();

        if (methodVariables.size() != methodInput.size())
            throw new CompilingException(INVALID_PARAMETER_NUMBER);

        Iterator<VariableWrapper> variableIterator = methodVariables.listIterator();
        Iterator<String> inputIterator = methodInput.listIterator();
        while (variableIterator.hasNext()) {
            Assigning assignLine = new Assigning(variableIterator.next(), inputIterator.next());
            assignLine.check(scope);
        }

    }
}
