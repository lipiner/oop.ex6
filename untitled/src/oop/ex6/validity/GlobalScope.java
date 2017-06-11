package oop.ex6.validity;

import oop.ex6.validity.command_validity.CommandLine;

import java.util.LinkedList;

public class GlobalScope extends ScopeChecker {

    private static final String ILLEGAL_OPERATION_EXCEPTION_MESSAGE = "Illegal operation: ";

    public GlobalScope(){
        super(false);
        scopeName = null;
        status = Status.SEMI_CLOSED;
    }

//    public void closeFile(){
//        if (!this.getScopes().getLast().isClosed())
//            System.out.println(); ///EXCEPTION!!!!!!!!!!!!!!
//        // CHECK UNIDENTIFIED!!!!!!!!!!
//    }

//    @Override
//    public Variable createScopeVariable(String name, Variable.Type type, boolean assigned, boolean isFinal) {
//        return new Variable(name, type, assigned, isFinal, true);
//    }

    @Override
    public void freeze() throws CompilingException{
        throw new CompilingException(ILLEGAL_OPERATION_EXCEPTION_MESSAGE);
    }

    @Override
    public void close() throws CompilingException {
        if (this.getInnerScope() != null || !this.getInnerScope().isClosed())
            throw new CompilingException();
        LinkedList<CommandLine> commandLines = GlobalMembers.getInstance().getUnidentifiedCommands();
        for (CommandLine commandLine : commandLines)
            commandLine.check(this);
    }

    @Override
    public void openScope(ScopeChecker scope) throws CompilingException{
        if (!scope.isMethod())
            throw new CompilingException();
        super.openScope(scope);
    }

    @Override
    public Variable getVariable(String variableName) {
        return GlobalMembers.getInstance().getGlobalVariable(variableName);
    }

    @Override
    public boolean canBeDeclared(String variableName) {
        Variable variable = getVariable(variableName);
        return variable == null;
    }

    @Override
    public void addVariable(Variable variable) throws CompilingException{
        if(canBeDeclared(variable.getName()))
            GlobalMembers.getInstance().addVariable(variable);
        else
            throw new CompilingException();
    }
}
