package oop.ex6.main;

import oop.ex6.SyntaxChecker;
import oop.ex6.program_members.CompilingException;
import oop.ex6.program_members.GlobalMembers;
import oop.ex6.program_members.GlobalScope;
import oop.ex6.program_members.Method;

import java.io.*;

public class Sjavac {

    private static final int FILE_NAME_POSITION = 0;
    private static final String
        CORRECT_CODE = "0",
        COMPILATION_PROBLEM = "1",
        IO_PROBLEM = "2",
        UNCLOSED_METHOD_MSG = "Method declaration was not closed";

    public static void main (String[] args){
        //FileReader fileReader = null;
        RandomAccessFile reader = null;
        try {
//            File file = new File(args[FILE_NAME_POSITION]);
            reader = new RandomAccessFile(args[FILE_NAME_POSITION], "r");
            //fileReader = new FileReader(args[FILE_NAME_POSITION]);
            //reader = new LineNumberReader(fileReader);
            SyntaxChecker.createPatterns();
            GlobalScope scope = new GlobalScope();

            readGlobalScope(scope, reader);

            //reader = new LineNumberReader(fileReader);
            reader.seek(0);
            readMethods(scope, reader);

            System.out.println(CORRECT_CODE);

        }
        catch (IOException e) {
            System.out.println(IO_PROBLEM); //EXPLANATION??
        }
        catch (CompilingException e) {
            System.err.println(e.getMessage());
            System.out.println(COMPILATION_PROBLEM); //EXPLANATION??
        } finally {
            //closes the file
            try {
                if (reader != null)
                    reader.close();
            } catch (IOException e){
                System.out.println(IO_PROBLEM);
            }

            GlobalMembers.nullifyInstance();
        }

    }

    private static void readGlobalScope(GlobalScope scope, RandomAccessFile reader)
            throws IOException, CompilingException{
        String newLine = reader.readLine();
        int scopeDepth = 0;
        // reading the global section
        while (newLine != null) {
//            System.out.println(GlobalMembers.getInstance().getLineNumber()); //////////////////////////
            GlobalMembers.getInstance().updateLineNumber();
            if (scopeDepth == 0)
                scope.readLine(newLine);

            SyntaxChecker.LineStatus lineStatus = SyntaxChecker.getLineType(newLine);
            if (lineStatus == SyntaxChecker.LineStatus.OPEN_SCOPE)
                scopeDepth ++;
            else if (lineStatus == SyntaxChecker.LineStatus.CLOSE_SCOPE)
                scopeDepth --;

            newLine = reader.readLine();
        }

        if (scopeDepth > 0)
            throw new CompilingException(UNCLOSED_METHOD_MSG);
    }

    private static void readMethods(GlobalScope scope, RandomAccessFile reader)
            throws IOException, CompilingException{
        int currentLineNumber = 1;
        for (Method method: GlobalMembers.getInstance().getAllMethods()) {
            method.openScope();
            while (currentLineNumber < method.getLineNumber() + 1) {
                reader.readLine();
                currentLineNumber ++;
            }
            //reader.setLineNumber(method.getLineNumber()+1);
//            System.out.println("d");
////            System.out.println(method.getLineNumber()+1);
//            System.out.println(reader.getLineNumber());
//            System.out.println(line);
//            String line;//= reader.readLine();
            while (!scope.isMethodClosed()) {
//                line = reader.readLine();
                currentLineNumber++;
                scope.readLine(reader.readLine());
            }
        }
    }


}
