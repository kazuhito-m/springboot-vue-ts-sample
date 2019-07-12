package com.github.kazuhito_m.mysample.infrastructure.multidbsetting;

import org.seasar.doma.AnnotateWith;
import org.seasar.doma.Annotation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import static org.seasar.doma.AnnotationTarget.*;

/**
 * 2つ目のDB(Log用DB)に接続したい際にDoma2のDaoに付与するアノテーション。
 */
@AnnotateWith(annotations = {
        @Annotation(target = CLASS, type = Repository.class),
        @Annotation(target = CONSTRUCTOR_PARAMETER, type = Qualifier.class, elements = "\"configForConnectToLogDatabase\""),
        @Annotation(target = CONSTRUCTOR, type = Autowired.class)})
public @interface ConfigAutowireableForConnectToLogDatabase {
}
