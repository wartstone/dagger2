package com.vertical.annotation;

import java.util.List;

import javax.annotation.processing.Messager;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.DeclaredType;
import javax.lang.model.type.MirroredTypeException;
import javax.lang.model.type.TypeKind;
import javax.lang.model.type.TypeMirror;

import static com.vertical.annotation.Configuration.ACTIVITY_TYPE;
import static com.vertical.annotation.Configuration.AutoLayoutActivityName;
import static com.vertical.annotation.Configuration.InjectorFragmentName;
import static javax.lang.model.element.ElementKind.INTERFACE;

/**
 * Created by ls on 8/18/17.
 */

public class Utils {
    public static TypeMirror getPresenterTypeMirror(AutoWire annotation) {
        try {
            annotation.presenter();
        } catch(MirroredTypeException mte ) {
            return mte.getTypeMirror();
        }
        return null;
    }

    public static TypeMirror getContractTypeMirror(AutoWire annotation) {
        try {
            annotation.contract();
        } catch(MirroredTypeException mte ) {
            return mte.getTypeMirror();
        }
        return null;
    }

    public static TypeMirror getMVPContractClass(TypeMirror typeMirror, Messager messager) {
        DeclaredType declaredType = (DeclaredType) typeMirror;
        Element element = declaredType.asElement();
        if (!(element instanceof TypeElement)) {
            MessagerUtil.getInstance(messager).info(">>> Element not instance of TypeElement... <<<");
            return null;
        }

        TypeElement typeElement = (TypeElement) element;
        for (TypeMirror interfaceType : typeElement.getInterfaces()) {
            MessagerUtil.getInstance(messager).info("||||||Type %s, interface is %s ", typeMirror, interfaceType.toString());
            if(interfaceType.toString().contains("Contract") || interfaceType.toString().contains("contract")) {
                DeclaredType interfaceDeclaredType = (DeclaredType) interfaceType;
                Element interfaceElement = interfaceDeclaredType.asElement();
                TypeElement interfaceTypeElement = (TypeElement) interfaceElement;
                TypeElement enclosingElement = (TypeElement) interfaceTypeElement.getEnclosingElement();
                MessagerUtil.getInstance(messager).info("||||||Type %s, enclosingType is %s", typeMirror, enclosingElement.asType());
                return enclosingElement.asType();
            }
        }

        return null;
    }

    public static boolean isSubtypeOfActivity(TypeMirror typeMirror, Messager mMessager) {
        boolean isActivity = isSubtypeOfType(typeMirror, ACTIVITY_TYPE, mMessager);
        boolean isBaseAutoActivity = isSubtypeOfType(typeMirror, AutoLayoutActivityName, mMessager);
        return isActivity || isBaseAutoActivity;
    }

    public static boolean isSubtypeOfFragment(TypeMirror typeMirror, Messager mMessager) {
        boolean isActivity = isSubtypeOfType(typeMirror, ACTIVITY_TYPE, mMessager);
        boolean isBaseAutoFragment = isSubtypeOfType(typeMirror, InjectorFragmentName, mMessager);
        return isActivity || isBaseAutoFragment;
    }

    public static boolean isSubtypeOfType(TypeMirror typeMirror, String otherType, Messager mMessager) {
        if (isTypeEqual(typeMirror, otherType)) {
            MessagerUtil.getInstance(mMessager).info("=== Type equal ===");
            return true;
        }
        if (typeMirror.getKind() != TypeKind.DECLARED) {
            MessagerUtil.getInstance(mMessager).info("Type is not class or interface. typeMirror.getKind() = %s.", typeMirror.getKind());
            return false;
        }
        DeclaredType declaredType = (DeclaredType) typeMirror;
        MessagerUtil.getInstance(mMessager).info("<< typeMirror is %s;  otherType is %s >>", typeMirror, otherType);
        List<? extends TypeMirror> typeArguments = declaredType.getTypeArguments();
        if (typeArguments.size() > 0) {
            StringBuilder typeString = new StringBuilder(declaredType.asElement().toString());
            typeString.append('<');
            for (int i = 0; i < typeArguments.size(); i++) {
                if (i > 0) {
                    typeString.append(',');
                }
                typeString.append('?');
            }
            typeString.append('>');
            MessagerUtil.getInstance(mMessager).info("TypeString is %s", typeString);
            if (typeString.toString().equals(otherType)) {
                return true;
            }
        }

        Element element = declaredType.asElement();
        if (!(element instanceof TypeElement)) {
            MessagerUtil.getInstance(mMessager).info("Element not instance of TypeElement<<<");
            return false;
        }
        TypeElement typeElement = (TypeElement) element;
        TypeMirror superType = typeElement.getSuperclass();
        MessagerUtil.getInstance(mMessager).info("superType is %s", superType.toString());
        if (isSubtypeOfType(superType, otherType, mMessager)) {
            MessagerUtil.getInstance(mMessager).info("=== Type equal at the end: %s === ", superType.toString());
            return true;
        }
        for (TypeMirror interfaceType : typeElement.getInterfaces()) {
            if (isSubtypeOfType(interfaceType, otherType, mMessager)) {
                MessagerUtil.getInstance(mMessager).info("=== Type interface equal : %s ===", interfaceType.toString());
                return true;
            }
        }

        MessagerUtil.getInstance(mMessager).info("!=!=!= Type not equal at the end: %s !=!=!=", typeMirror.toString());
        return false;
    }

    static boolean isSubtypeOfParamType(TypeMirror typeMirror, String otherType, Messager mMessager) {
        if (isTypeEqual(typeMirror, otherType)) {
            MessagerUtil.getInstance(mMessager).info("Type equal... <<<");
            return true;
        }
        if (typeMirror.getKind() != TypeKind.DECLARED) {
            MessagerUtil.getInstance(mMessager).info(">>> type is not declared... <<<");
            return false;
        }
        DeclaredType declaredType = (DeclaredType) typeMirror;
        List<? extends TypeMirror> typeArguments = declaredType.getTypeArguments();
        MessagerUtil.getInstance(mMessager).info(">>> typeMirror is %s;  arg size is %d<<<", typeMirror, typeArguments.size());
        if (typeArguments.size() > 0) {
            StringBuilder typeString = new StringBuilder(declaredType.asElement().toString());
            typeString.append('<');
            for (int i = 0; i < typeArguments.size(); i++) {
                if (i > 0) {
                    typeString.append(',');
                }
                typeString.append('?');
            }
            typeString.append('>');
            if (typeString.toString().equals(otherType)) {
                MessagerUtil.getInstance(mMessager).info(">>> Type compare equal : %s... <<<", typeString.toString());
                return true;
            }
        }
        Element element = declaredType.asElement();
        if (!(element instanceof TypeElement)) {
            MessagerUtil.getInstance(mMessager).info(">>> Element not instance of TypeElement... <<<");
            return false;
        }
        TypeElement typeElement = (TypeElement) element;
        TypeMirror superType = typeElement.getSuperclass();
        if (isSubtypeOfParamType(superType, otherType, mMessager)) {
            MessagerUtil.getInstance(mMessager).info(">>> type equal at the end: %s... <<<", superType.toString());
            return true;
        }
        for (TypeMirror interfaceType : typeElement.getInterfaces()) {
            if (isSubtypeOfParamType(interfaceType, otherType, mMessager)) {
                MessagerUtil.getInstance(mMessager).info(">>> type interface equal : %s... <<<", interfaceType.toString());
                return true;
            }
        }

        MessagerUtil.getInstance(mMessager).info(">>> type not equal at the end: %s... <<<", typeMirror.toString());
        return false;
    }

    private static boolean isTypeEqual(TypeMirror typeMirror, String otherType) {
        return otherType.equals(typeMirror.toString());
    }

    private boolean isInterface(TypeMirror typeMirror) {
        return typeMirror instanceof DeclaredType
                && ((DeclaredType) typeMirror).asElement().getKind() == INTERFACE;
    }
}
