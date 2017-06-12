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

    @Override
    public void check(ScopeChecker scope) throws CompilingException {
        commandLine.check(scope);
    }
}
