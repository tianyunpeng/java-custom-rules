package org.sonar.samples.java.checks;

import org.sonar.check.Priority;
import org.sonar.check.Rule;
import org.sonar.plugins.java.api.IssuableSubscriptionVisitor;
import org.sonar.plugins.java.api.semantic.Symbol;
import org.sonar.plugins.java.api.semantic.Type;
import org.sonar.plugins.java.api.tree.AnnotationTree;
import org.sonar.plugins.java.api.tree.MethodTree;
import org.sonar.plugins.java.api.tree.Tree;
import org.sonar.plugins.java.api.tree.Tree.Kind;
import java.util.Collections;
import java.util.List;

@Rule(
    key = "MyFirstCustomCheck",
    name = "This rule detects whether the parameter is the usage of \"POJO\"",
    description = "在标有@RequestMapping注解的所有rest服务方法中，出/入参数必须使用pojo类型参数，不允许使用Object、map、jsonobject、String等。",
    priority = Priority.CRITICAL,
    tags = {"bug"})
public class MyFirstCustomCheck extends IssuableSubscriptionVisitor {
    @Override
    public List<Kind> nodesToVisit() {
        return Collections.singletonList(Kind.METHOD);
    }
    @Override
    public void visitNode(Tree tree) {
        MethodTree method = (MethodTree) tree;
        String methodName = method.simpleName().toString();
        System.out.println("方法名==="+methodName);
        List<AnnotationTree> annotations = method.modifiers().annotations();
        for(AnnotationTree annotationTree : annotations){
            // 注解名称
            String annotate = annotationTree.annotationType().toString();
            // 该方法的被RequestMapping注解
            if("RequestMapping".equals(annotate)){
                // 现在，通过检查方法是否具有单个参数来缩小规则的范围，并在这种情况下提出问题。
                if (method.parameters().size() >= 1) {
                    Symbol.MethodSymbol symbol = method.symbol();
                    Type firstParameterType = symbol.parameterTypes().get(0);
                    System.out.println("第一个参数的类型"+firstParameterType);
                    Type returnType = symbol.returnType().type();
                    System.out.println("返回参数类型"+returnType);
                    String[] paramsType = {"Object","Map","JSONObject","String"};
                    for(String param : paramsType){
                        if(param.equals(firstParameterType.toString()) || param.equals(returnType.toString())){
                            System.out.println("方法"+methodName+"不符合“出/入参数必须使用pojo类型参数”规范");
                            if (returnType.is(firstParameterType.fullyQualifiedName())) {
                                // 我们选择在精确的位置报告问题，这将是方法的名称。
                                reportIssue(method.simpleName(), "出/入参数必须使用pojo类型!");
                            }
                            return;
                        }
                    }
                }
            }
        }

    }
}