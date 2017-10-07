package com.uic.ahegde5.instrumentation.parser;

import com.uic.ahegde5.instrumentation.utility.JavaFileListing;
import com.uic.ahegde5.instrumentation.utility.FIleUtility;
import org.apache.commons.io.FileUtils;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jdt.core.dom.*;
import org.eclipse.jdt.core.dom.rewrite.ASTRewrite;
import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.Document;
import org.eclipse.text.edits.TextEdit;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

public class InstrumentationParser {

    private String directoryPath;

    public InstrumentationParser() {
    }

    public InstrumentationParser(String directoryPath) {
        this.directoryPath = directoryPath;
    }


    public CompilationUnit parse(String filePath) throws ExecutionException {
        ASTParser parser = ASTParser.newParser(AST.JLS8);
        parser.setKind(ASTParser.K_COMPILATION_UNIT);
        parser.setResolveBindings(true);
        Map options = JavaCore.getOptions();
        parser.setCompilerOptions(options);

        String unitName = filePath.substring(filePath.lastIndexOf("\\") + 1,filePath.length());
        System.out.println("======================================================================");
        System.out.println("File name : " + filePath);
        parser.setUnitName(unitName);
        String[] sources = { directoryPath };
       // Document document = new Document("D:\\IntellijWorkspace\\JavaProblems\\src");
        String[] classpath = {};
        parser.setEnvironment(classpath, sources, new String[] { "UTF-8"}, true);
        try {
            parser.setSource(FIleUtility.readFileToString(filePath).toCharArray());
        } catch (IOException e) {
            System.out.println(e.getStackTrace().toString());
        }
        final CompilationUnit cu = (CompilationUnit) parser.createAST(null);
        return cu;
    }


    public String getDirectoryPath() {
        return directoryPath;
    }


    public void execute(){

        try {
            List<String> filePathList = JavaFileListing.listFilesInDirectory(directoryPath);
            InstrumentationVisitor instrumentationVisitor = new InstrumentationVisitor();
            for(String filePath : filePathList) {

                CompilationUnit compilationUnit = parse(filePath);
                instrumentationVisitor.setCompilationUnit(compilationUnit);
                instrumentationVisitor.setFilePath(filePath);
                instrumentationVisitor.setSourceDocument(new Document(FIleUtility.readFileToString(instrumentationVisitor.getFilePath())));
                ASTRewrite astRewrite = ASTRewrite.create(compilationUnit.getAST());
                instrumentationVisitor.setAstRewrite(astRewrite);
                compilationUnit.accept(instrumentationVisitor);
                try {

                    TextEdit edits = astRewrite.rewriteAST(instrumentationVisitor.getSourceDocument(),null);
                    edits.apply(instrumentationVisitor.getSourceDocument());


                } catch (BadLocationException e) {
                    System.out.println(e);
                }

                FileUtils.write(new File(filePath),instrumentationVisitor.getSourceDocument().get());

            }
        } catch (ExecutionException e) {
            System.out.println(e);
        } catch (IOException e) {
            System.out.println(e);
        }
    }
}
