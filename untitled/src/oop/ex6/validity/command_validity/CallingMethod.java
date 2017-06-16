package oop.ex6.validity.command_validity;

import oop.ex6.validity.*;

import java.util.Iterator;
import java.util.LinkedList;

public class CallingMethod extends CommandLine {
    private String methodName;
    LinkedList <String> methodInput;

    public CallingMethod(String methodName, LinkedList<String> methodInput) {
        this.methodName = methodName;
        this.methodInput= methodInput;
    }

    @Override
    public void check(ScopeChecker scope) throws CompilingException {
        Method method = GlobalMembers.getInstance().getMethod(methodName);
        if (method == null)
            throw new CompilingException();

        LinkedList<VariableWrapper> methodVariables = method.getMethodVariables();

        if (methodVariables.size() != methodInput.size())
            throw new CompilingException();

        Iterator<VariableWrapper> variableIterator = methodVariables.listIterator();
        Iterator<String> inputIterator = methodInput.listIterator();
        while (variableIterator.hasNext()) {
            Assigning assignLine = new Assigning(variableIterator.next(), inputIterator.next());
            assignLine.check(scope);
        }

    }
}
