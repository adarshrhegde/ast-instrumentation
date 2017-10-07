package com.uic.ahegde5.instrumentation.parser;


import org.eclipse.core.runtime.CoreException;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.dom.*;
import org.eclipse.jdt.core.dom.rewrite.ASTRewrite;
import org.eclipse.jdt.core.dom.rewrite.ImportRewrite;
import org.eclipse.jdt.core.dom.rewrite.ListRewrite;
import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.Document;
import org.eclipse.text.edits.TextEdit;

import java.util.*;

public class InstrumentationVisitor extends ASTVisitor {

    List<MethodDeclaration> methods = new ArrayList<>();

    List<SimpleName> simpleNames = new ArrayList<>();

    private CompilationUnit compilationUnit;

    private String filePath;

    private Document sourceDocument;

    private ASTRewrite astRewrite;

    private List<String> ignoredSimpleNames = Arrays.asList(new String[]{"print", "length","println"});

    public InstrumentationVisitor() {
    }

    public InstrumentationVisitor(CompilationUnit compilationUnit, String filePath, List<SimpleName> simpleNames, Document sourceDocument, ASTRewrite astRewrite) {
        this.compilationUnit = compilationUnit;
        this.filePath = filePath;
        this.simpleNames = simpleNames;
        this.sourceDocument = sourceDocument;
        this.astRewrite = astRewrite;
    }

    public CompilationUnit getCompilationUnit() {
        return compilationUnit;
    }

    public void setCompilationUnit(CompilationUnit compilationUnit) {
        this.compilationUnit = compilationUnit;
    }

    public String getFilePath() {
        return filePath;
    }

    public List<SimpleName> getSimpleNames() {
        return simpleNames;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public Document getSourceDocument() {
        return sourceDocument;
    }

    public void setSourceDocument(Document sourceDocument) {
        this.sourceDocument = sourceDocument;
    }

    public ASTRewrite getAstRewrite() {
        return astRewrite;
    }

    public void setAstRewrite(ASTRewrite astRewrite) {
        this.astRewrite = astRewrite;
    }

    @Override
    public boolean visit(MethodDeclaration method) {

        methods.add(method);
        /*AST ast = compilationUnit.getAST();
        MethodInvocation methodInvocation = ast.newMethodInvocation();
        QualifiedName qualifiedName = ast.newQualifiedName(ast.newSimpleName("System"),ast.newSimpleName("out"));
        methodInvocation.setExpression(qualifiedName);
        methodInvocation.setName(ast.newSimpleName("println"));
        StringLiteral literal = ast.newStringLiteral();
        literal.setLiteralValue("Hello World");
        methodInvocation.arguments().add(literal);

        Statement statement = ast.newExpressionStatement(methodInvocation);
        ASTRewrite astRewrite = ASTRewrite.create(ast);

        ListRewrite listRewrite = astRewrite.getListRewrite(method.getBody(),Block.STATEMENTS_PROPERTY);
        listRewrite.insertLast(statement,null);

        try {
            org.eclipse.jface.text.Document document = new org.eclipse.jface.text.Document(FIleUtility.readFileToString(filePath));
            TextEdit edits = astRewrite.rewriteAST(document,null);
            edits.apply(document);
            System.out.println(document.get());
            FileUtils.write(new File(filePath),document.get());

        } catch (BadLocationException e) {
            System.out.println(e);
        } catch (IOException e) {
            System.out.println(e);
        }*/

        return super.visit(method);

    }
/*
    ImportRewrite importRewrite = ImportRewrite.create(compilationUnit,true);
        importRewrite.addImport("com.uic.ahegde5.instrumentation.utility.TemplateClass");
    */

    @Override
    public boolean visit(VariableDeclarationStatement variable) {
        visitStatement(variable);
        return super.visit(variable);
    }

    @Override
    public boolean visit(IfStatement ifStatement) {

        visitStatement(ifStatement);
        return super.visit(ifStatement);
    }

    @Override
    public boolean visit(WhileStatement whileStatement) {

        visitStatement(whileStatement);
        return super.visit(whileStatement);
    }

    @Override
    public boolean visit(ForStatement forStatement) {
        visitStatement(forStatement);
        return super.visit(forStatement);
    }

    @Override
    public boolean visit(EnhancedForStatement enhancedForStatement) {
        visitStatement(enhancedForStatement);
        return super.visit(enhancedForStatement);
    }

    @Override
    public boolean visit(ReturnStatement returnStatement) {
        visitStatement(returnStatement);
        return super.visit(returnStatement);
    }

    /*@Override
    public boolean visit(SwitchCase switchCase) {
        visitStatement(switchCase);
        return super.visit(switchCase);
    }*/

    @Override
    public boolean visit(SwitchStatement switchStatement) {
        visitStatement(switchStatement);
        return super.visit(switchStatement);
    }

    @Override
    public boolean visit(TypeDeclarationStatement typeDeclarationStatement) {

        System.out.println("Type declaration statement >>" + typeDeclarationStatement.getDeclaration().toString());
        return super.visit(typeDeclarationStatement);
    }

    @Override
    public boolean visit(ExpressionStatement expressionStatement) {
        //System.out.println("Expression statement >> " + expressionStatement.toString());
        //visitStatement(expressionStatement);
        return super.visit(expressionStatement);
    }

    public void visitStatement(Statement statement) {
        AST ast = compilationUnit.getAST();

        TextElement textElement = ast.newTextElement();
        int lineNo = compilationUnit.getLineNumber(statement.getStartPosition());
        String type = null;
        if (statement instanceof IfStatement) {
            type = "If";
        } else if (statement instanceof WhileStatement) {
            type = "While";
        } else if (statement instanceof ForStatement) {
            type = "For";
        } else if (statement instanceof EnhancedForStatement) {
            type = "Enhanced For";
        } else if (statement instanceof ReturnStatement) {
            type = "Return";
        } else if (statement instanceof SwitchStatement) {
            type = "Switch";
        } /*else if (statement instanceof SwitchCase) {
            type = "Switch Case";
        }*/ else if (statement instanceof VariableDeclarationStatement) {
            type = "Assign";
        } /*else if (statement instanceof ExpressionStatement) {
            type = "Expression";
        }*/
        String s = "TemplateClass.instrum(" + lineNo + ", \"" + type + "\"";

        statement.accept(new ASTVisitor() {
            @Override
            public boolean visit(SimpleName simpleName) {
                if(!ignoredSimpleNames.contains(simpleName.getIdentifier()))
                    simpleNames.add(simpleName);
                return super.visit(simpleName);
            }
        });
        Set<String> simpleNameSet = new HashSet<>();
        for (SimpleName simpleName : simpleNames) {

            int lineNoOfElement = compilationUnit.getLineNumber(simpleName.getStartPosition());

            if (lineNoOfElement == lineNo && !simpleNameSet.contains(simpleName.getIdentifier()) && !methods.contains(simpleName)) {
                simpleNameSet.add(simpleName.getIdentifier());
                s += ",new Pair(\"" + simpleName.getFullyQualifiedName() + "\"," + simpleName.getIdentifier() + ")";
            }
        }
        s += ");";
        textElement.setText(s);

        ListRewrite listRewrite = astRewrite.getListRewrite(statement.getParent(), Block.STATEMENTS_PROPERTY);
        if(type.equals("Assign")){
            listRewrite.insertAfter(textElement, statement, null);
        } else {
            listRewrite.insertBefore(textElement, statement, null);
        }

    }

    public void addImport(String importStatement){
        //ListRewrite listRewrite = astRewrite.getListRewrite(compilationUnit.findDeclaringNode(), Block.STATEMENTS_PROPERTY);
        //listRewrite.insertBefore(textElement, statement, null);
        System.out.println(compilationUnit.getTypeRoot() instanceof ICompilationUnit);
        ImportRewrite importRewrite = ImportRewrite.create(compilationUnit, true);
        importRewrite.addImport(importStatement);
        try {
            TextEdit textEdit = importRewrite.rewriteImports(null);
            textEdit.apply(sourceDocument);
        } catch (CoreException e) {
            e.printStackTrace();
        } catch (BadLocationException e) {
            e.printStackTrace();
        }

    }


    public List<MethodDeclaration> getMethods() {
        return methods;
    }

}
