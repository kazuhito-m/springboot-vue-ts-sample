package com.github.kazuhito_m.mysample.infrastructure.datasource;

import org.seasar.doma.AnnotateWith;
import org.seasar.doma.Annotation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import static org.seasar.doma.AnnotationTarget.*;

@AnnotateWith(annotations = {
        @Annotation(target = CLASS, type = Repository.class),
        @Annotation(target = CONSTRUCTOR_PARAMETER, type = Qualifier.class, elements = "\"configForConnectToMainDatabase\""),
        @Annotation(target = CONSTRUCTOR, type = Autowired.class)})
public @interface ConfigAutowireable {
}
