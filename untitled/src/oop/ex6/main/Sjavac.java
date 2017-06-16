package oop.ex6.main;

import oop.ex6.SyntaxChecker;
import oop.ex6.program_members.CompilingException;
import oop.ex6.program_members.GlobalMembers;
import oop.ex6.program_members.GlobalScope;
import oop.ex6.program_members.Method;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.LineNumberReader;

public class Sjavac {

    private static final int FILE_NAME_POSITION = 0;
    private static final String
        CORRECT_CODE = "0",
        COMPILATION_PROBLEM = "1",
        IO_PROBLEM = "2";

    public static void main (String[] args){
        try {
            File file = new File(args[FILE_NAME_POSITION]);
            LineNumberReader reader = new LineNumberReader(new FileReader(file));
            SyntaxChecker.createPatterns();
            GlobalScope scope = new GlobalScope();

            readGlobalScope(scope, reader);
            readMethods(scope, reader);

            System.out.println(CORRECT_CODE);

        }
        catch (IOException e) {
            System.out.println(IO_PROBLEM); //EXPLANATION??
        }
        catch (CompilingException e) {
            System.err.println(e.getMessage());
            System.out.println(COMPILATION_PROBLEM); //EXPLANATION??

        }

    }

    private static void readGlobalScope(GlobalScope scope, LineNumberReader reader)
            throws IOException, CompilingException{
        String newLine = reader.readLine();
        int scopeDepth = 0;
        // reading the global section
        while (newLine != null) {
            GlobalMembers.getInstance().updateLineNumber();

            if (scopeDepth == 0)
                scope.readLine(newLine);

            SyntaxChecker.LineStatus lineStatus = SyntaxChecker.getLineType(newLine);
            if (lineStatus == SyntaxChecker.LineStatus.OPEN_SCOPE)
                scopeDepth ++;
            else if (lineStatus == SyntaxChecker.LineStatus.CLOSE_SCOPE)
                scopeDepth --;

            GlobalMembers.getInstance().updateLineNumber();
            newLine = reader.readLine();
        }
    }

    private static void readMethods(GlobalScope scope, LineNumberReader reader)
            throws IOException, CompilingException{
        for (Method method: GlobalMembers.getInstance().getAllMethods()) {
            method.openScope();
            reader.setLineNumber(method.getLineNumber()+1);
            while (!scope.isMethodClosed())
                scope.readLine(reader.readLine());
        }
    }


}
