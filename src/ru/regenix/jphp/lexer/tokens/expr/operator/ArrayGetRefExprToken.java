package ru.regenix.jphp.lexer.tokens.expr.operator;

import ru.regenix.jphp.lexer.tokens.stmt.ExprStmtToken;

import java.util.List;

public class ArrayGetRefExprToken extends ArrayGetExprToken {
    private List<ExprStmtToken> parameters;

    public ArrayGetRefExprToken(ArrayGetExprToken token){
        super(token.getMeta());
        parameters = token.getParameters();
    }

    public List<ExprStmtToken> getParameters() {
        return parameters;
    }

    public void setParameters(List<ExprStmtToken> parameters) {
        this.parameters = parameters;
    }

    @Override
    public int getPriority() {
        return 1;
    }
}