package com.fasten.butterknife.compiler;

import com.fasten.butterknife.annotations.BindView;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeName;
import com.squareup.javapoet.TypeSpec;

import java.util.Collections;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;

/**
 * 作者:created by storm on 2019-12-09
 */

public class BindViewAnnotationProcessor extends AbstractProcessor {

    private ElementHelper mHelper;

    private final static String SEPARATE_SYMBOL = "$$Binder";


    @Override
    public SourceVersion getSupportedSourceVersion() {
        return SourceVersion.RELEASE_7;
    }

    @Override
    public Set<String> getSupportedAnnotationTypes() {
        return Collections.singleton(BindView.class.getCanonicalName());
    }

    @Override
    public synchronized void init(ProcessingEnvironment processingEnvironment) {
        super.init(processingEnvironment);
        mHelper = ElementHelper.getInstance(processingEnvironment.getElementUtils(), processingEnvironment.getMessager());
    }

    @Override
    public boolean process(Set<? extends TypeElement> set, RoundEnvironment roundEnvironment) {
        for (Element element : roundEnvironment.getElementsAnnotatedWith(BindView.class)) {
            if (element instanceof VariableElement) {
                VariableElement variableElement = (VariableElement) element;
                mHelper.printMessage(">>>>>>> "+variableElement.getSimpleName());
                if (variableElement.getAnnotation(BindView.class) != null) {
                    BindView bindViewAnnotation = variableElement.getAnnotation(BindView.class);
                    int idValue = bindViewAnnotation.value();
                    Element enclosingElement = variableElement.getEnclosingElement();
                   if(enclosingElement instanceof TypeElement){
                       TypeElement typeElement=(TypeElement)enclosingElement;
                       String packageName=mHelper.getPackageName(typeElement);
                       ClassName generateClassName=ClassName.get(packageName,generateClassName(typeElement));

                       ClassName bindInterfaceClassName=ClassName.get("com.fasten.butterknife.api","IBindInterface");
                       TypeSpec.Builder tsb=TypeSpec.classBuilder(generateClassName)
                               .addJavadoc("Represent the class $T of {@link $T}\n",generateClassName,ClassName.get(typeElement))
                               .addModifiers(Modifier.PUBLIC)
                               .addSuperinterface(bindInterfaceClassName);
                       ClassName overrideClassName=ClassName.get("java.lang","Override");

                       MethodSpec.Builder msb=MethodSpec.methodBuilder("bind")
                               .addAnnotation(overrideClassName)
                               .addModifiers(Modifier.PUBLIC)
                               .addParameter(Object.class,"target")
                               .beginControlFlow("if(target instanceof $T)",ClassName.get(typeElement))
                               .addStatement("$T instance=($T)target",ClassName.get(typeElement),ClassName.get(typeElement))
                               .addStatement("instance.$L=instance.findViewById($L)",variableElement.getSimpleName(),idValue)
                               .endControlFlow()
                               .returns(void.class);

                       tsb.addMethod(msb.build());

                       try {
                           JavaFile.builder(packageName,tsb.build())
                                   .skipJavaLangImports(true)
                                   .addFileComment("generate file,DO NOT MODIFY!")
                                   .build().writeTo(processingEnv.getFiler());

                       }catch (Exception e){
                           e.printStackTrace();
                       }


                   }
                }
            }
        }
        return false;
    }

    private String generateClassName(TypeElement typeElement){
        String sourceName=typeElement.getSimpleName().toString();
        StringBuilder builder=new StringBuilder(sourceName);
        return builder.append(SEPARATE_SYMBOL).toString();
    }

}
