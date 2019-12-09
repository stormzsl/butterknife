package com.fasten.butterknife.compiler;

import javax.annotation.processing.Messager;
import javax.lang.model.element.TypeElement;
import javax.lang.model.util.Elements;
import javax.tools.Diagnostic;

/**
 * 作者:created by storm on 2019-12-09
 */

public class ElementHelper {
    private static final ElementHelper ourInstance = new ElementHelper();

    private static Elements mElementsUtil;

    private static Messager mMessager;

    public static ElementHelper getInstance(Elements elements, Messager messager) {
        mElementsUtil = elements;
        mMessager = messager;
        return ourInstance;
    }

    private ElementHelper() {
    }

    public String getPackageName(TypeElement typeElement) {
        return mElementsUtil.getPackageOf(typeElement).getQualifiedName().toString();
    }

    public void printMessage(String msg) {
        mMessager.printMessage(Diagnostic.Kind.WARNING, msg);
    }
}
