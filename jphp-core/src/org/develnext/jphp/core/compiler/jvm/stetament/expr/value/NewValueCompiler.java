package org.develnext.jphp.core.compiler.jvm.stetament.expr.value;

import org.develnext.jphp.core.compiler.jvm.stetament.ExpressionStmtCompiler;
import org.develnext.jphp.core.compiler.jvm.stetament.expr.BaseExprCompiler;
import org.develnext.jphp.core.tokenizer.token.expr.value.FulledNameToken;
import org.develnext.jphp.core.tokenizer.token.expr.value.NewExprToken;
import org.develnext.jphp.core.tokenizer.token.expr.value.StaticExprToken;
import org.develnext.jphp.core.tokenizer.token.expr.value.VariableExprToken;
import php.runtime.Memory;
import php.runtime.env.Environment;
import php.runtime.env.TraceInfo;

public class NewValueCompiler extends BaseExprCompiler<NewExprToken> {
    public NewValueCompiler(ExpressionStmtCompiler exprCompiler) {
        super(exprCompiler);
    }

    @Override
    public void write(NewExprToken token, boolean returnValue) {
        method.getEntity().setImmutable(false);
        expr.writeLineNumber(token);

        expr.writePushEnv();
        if (token.isDynamic()){
            expr.writePushVariable((VariableExprToken) token.getName());
            expr.writePopString();
            expr.writePushDupLowerCase();
        } else {
            if (token.getName() instanceof StaticExprToken){
                expr.writePushStatic();
                expr.writePushDupLowerCase();
            } else {
                FulledNameToken name = (FulledNameToken) token.getName();
                expr.writePushString(name.getName());
                expr.writePushString(name.getName().toLowerCase());
            }
        }
        expr.writePushTraceInfo(token.getName());
        expr.writePushParameters(token.getParameters());
        expr.writeSysDynamicCall(
                Environment.class, "__newObject",
                Memory.class,
                String.class, String.class, TraceInfo.class, Memory[].class
        );

        if (!returnValue)
            expr.writePopAll(1);
    }
}
