package oop.ex6.validity;

import oop.ex6.validity.command_validity.CommandLine;

import java.util.LinkedList;

public class GlobalMembers {

    private static GlobalMembers instance = null;
    private LinkedList<Method> methodsList;
    private LinkedList<VariableWrapper> globalVariables;
    private LinkedList<CommandLine> unidentifiedCommands;

    private GlobalMembers(){
        methodsList = new LinkedList<Method>();
        globalVariables = new LinkedList<VariableWrapper>();
        unidentifiedCommands = new LinkedList<CommandLine>();
    }

    static GlobalMembers getInstance(){
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

    void addMethod(String methodName, LinkedList<Variable.Type> methodVariables){
        methodsList.add(new Method(methodName, methodVariables));
    }

    VariableWrapper getGlobalVariable(String variableName) {
        for (VariableWrapper variable: globalVariables)
            if (variable.getVariableName().equals(variableName))
                return variable;
        return null;
    }

    void addVariable(VariableWrapper variable){
        globalVariables.add(variable);
    }

    void addUnidentifiedCommands(CommandLine commandLine){
        unidentifiedCommands.add(commandLine);
    }

//    public void checkUnidentifiedCommands(){
//        for (CommandLine commandLine: unidentifiedCommands)
//            commandLine.check();
//    }


    LinkedList<CommandLine> getUnidentifiedCommands() {
        return unidentifiedCommands;
    }
}
