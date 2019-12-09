package com.fasten.butterknife.compiler;

import javax.annotation.processing.Messager;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;
import javax.lang.model.util.Elements;
import javax.lang.model.util.Types;
import javax.tools.Diagnostic;

/**
 * 作者:created by storm on 2019-12-09
 */

public class ElementHelper {
    private static final ElementHelper ourInstance = new ElementHelper();

    private static final String ACTIVITY_FULL_NAME = "android.app.activity";

    private static final String FRAGMENT_FULL_NAME = "android.app.Fragment";

    private static Elements mElementsUtil;

    private static Messager mMessager;

    private static Types mTypeUtil;

    public static ElementHelper getInstance(Elements elements, Messager messager, Types types) {
        mElementsUtil = elements;
        mMessager = messager;
        mTypeUtil = types;
        return ourInstance;
    }

    private ElementHelper() {
    }

    public String getPackageName(TypeElement typeElement) {
        return mElementsUtil.getPackageOf(typeElement).getQualifiedName().toString();
    }

    public void warn(String msg, Object... args) {
        mMessager.printMessage(Diagnostic.Kind.WARNING, String.format(msg, args));
    }

    public void error(String msg, Object... args) {
        mMessager.printMessage(Diagnostic.Kind.ERROR, String.format(msg, args));
    }

    //判断BindView注解标注的字段是不是非private的
    public boolean isPrivateOrStatic(VariableElement variableElement) {
        return variableElement.getModifiers().contains(Modifier.PRIVATE) || variableElement.getModifiers().contains(Modifier.STATIC);
    }

    public boolean isValidVariableElement(VariableElement variableElement) {
        warn(">>>>>>> variableElement:%s", variableElement.getSimpleName());
        Element enclosingElement = variableElement.getEnclosingElement();
        if (enclosingElement instanceof TypeElement) {
            TypeElement parentElement = (TypeElement) enclosingElement;
            if (isPrivateOrStatic(variableElement)) {
                error(">>>>>> field %s must not be private or static in %s.java", variableElement.getSimpleName(), parentElement.getSimpleName());
                return false;
            }

            //判断父节点是否是类类型的，不是的话就会抛出异常
            //也就是说BindView 的使用必须在一个类里
            // Verify containing type.
            if (enclosingElement.getKind() != ElementKind.CLASS) {
                error(">>>>>> %s may only be contained in %s,classes", variableElement.getSimpleName(), parentElement.getSimpleName());
                return false;
            }

            //判断父节点如果是private 类，则抛出异常
            // Verify containing class visibility is not private.
            if (enclosingElement.getModifiers().contains(Modifier.PRIVATE)) {
                error(">>>>>> %s may not be contained in private %s classes", variableElement.getSimpleName(), parentElement.getSimpleName());
                return false;
            }
            return true;

        } else {
            error(">>>>>> field %s must be field in class", variableElement.getSimpleName());
            return false;
        }

    }

    //判断typeElement是不是type的子类,待完善
    public boolean isSubtype(TypeElement typeElement, String type) {
        return false;
    }

}
