package oop.ex6.main;

import oop.ex6.SyntaxChecker;
import oop.ex6.validity.CompilingException;
import oop.ex6.validity.GlobalMembers;
import oop.ex6.validity.GlobalScope;

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

    public static void main (String[] arg){
        try {
            File file = new File(arg[FILE_NAME_POSITION]);
            LineNumberReader reader = new LineNumberReader(new FileReader(file));
            int scopeDepth = 0;
            String newLine = reader.readLine();
            GlobalScope scope = new GlobalScope();

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
            }

            // reading the local section

        }
        catch (IOException e) {
            System.out.println(IO_PROBLEM); //EXPLANATION??
        }
        catch (CompilingException e) {
            System.out.println(COMPILATION_PROBLEM); //EXPLANATION??

        }

    }


}
