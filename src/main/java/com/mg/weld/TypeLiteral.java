package com.mg.weld;


import javax.enterprise.util.AnnotationLiteral;

public class TypeLiteral extends AnnotationLiteral<TypeAnnotation> implements TypeAnnotation{

    private String type;

    public TypeLiteral(String type) {
        this.type = type;
    }

    @Override
    public String value() {
        return type;
    }

}
