package oop.ex6.program_members;

import java.util.LinkedList;

public class GlobalMembers {

    private static GlobalMembers instance = null;
    private LinkedList<Method> methodsList;
    private LinkedList<VariableWrapper> globalVariables;
//    private LinkedList<CommandLine> unidentifiedCommands;
    private int lineNumber;

    private GlobalMembers(){
        methodsList = new LinkedList<Method>();
        globalVariables = new LinkedList<VariableWrapper>();
//        unidentifiedCommands = new LinkedList<CommandLine>();
        lineNumber = 0;
    }

    public static GlobalMembers getInstance(){
        if (instance == null)
            instance = new GlobalMembers();
        return instance;
    }

    public Method getMethod(String methodName) {
        for (Method method: methodsList)
            if (method.getName().equals(methodName))
                return method;
        return null;
    }


    public void addMethod(String methodName, LinkedList<VariableWrapper> methodVariables, ScopeChecker scope){
        methodsList.add(new Method(methodName, methodVariables, scope, lineNumber));
    }

    VariableWrapper getGlobalVariable(String variableName) {
        for (VariableWrapper variable: globalVariables)
            if (variable.getVariableName().equals(variableName))
                return variable;
        return null;
    }

    public VariableWrapper getGlobalVariable(VariableWrapper variable) {
        for (VariableWrapper scopeVariable: globalVariables)
            if (scopeVariable == variable)
                return scopeVariable;
        return null;
    }

    void addVariable(VariableWrapper variable){
        globalVariables.add(variable);
    }

//    void addUnidentifiedCommands(CommandLine commandLine){
//        unidentifiedCommands.add(commandLine);
//    }
//
////    public void checkUnidentifiedCommands(){
////        for (CommandLine commandLine: unidentifiedCommands)
////            commandLine.check();
////    }
//
//
//    LinkedList<CommandLine> getUnidentifiedCommands() {
//        return unidentifiedCommands;
//    }

    public int getLineNumber() {
        return lineNumber;
    }

    public void updateLineNumber() {
        lineNumber++;
    }
}
