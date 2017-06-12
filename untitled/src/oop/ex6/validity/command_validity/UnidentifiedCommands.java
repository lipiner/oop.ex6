package oop.ex6.validity.command_validity;

import oop.ex6.validity.CompilingException;
import oop.ex6.validity.ScopeChecker;

public class UnidentifiedCommands extends CommandLine {

    private ScopeChecker scope;
    private CommandLine commandLine;

    UnidentifiedCommands(ScopeChecker scope, CommandLine command){
        this.scope = scope;
        commandLine = command;
    }

    public void check() throws CompilingException {
        commandLine.check(scope);
    }

    @Override
    public void check(ScopeChecker scope)throws UnsupportedOperationException{
        throw new UnsupportedOperationException();
    }
}
