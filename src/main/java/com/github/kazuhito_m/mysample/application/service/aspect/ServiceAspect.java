package com.github.kazuhito_m.mysample.application.service.aspect;

/**
 * サービス層のメソッド終了をトラップするアスペクト。
 *
 * serviceパッケージ以下のすべての末尾Serviceクラスのすべてのメソッドをトラップする。
 * privateメソッドを取るには
 *
 * - AspectJのCompile-Time Weavingでないとできない
 * - アノテーションではなく、Aspectファイルでないとできない
 *   - https://www.eclipse.org/aspectj/doc/next/adk15notebook/ataspectj-aspects.html の方針
 *
 * のため、*.java 形式のaspectファイルで実装。
 */
public privileged aspect ServiceAspect {
    private final ServiceAspectProcessor processor = new ServiceAspectProcessor();
    pointcut serviceMethod():
        execution(* com.github.kazuhito_m.mysample.application.service..*Service.*(..));
    after() returning (Object returnValue): serviceMethod() {
        processor.after(thisJoinPoint, returnValue, null);
    }
    after() throwing (Throwable e): serviceMethod() {
        processor.after(thisJoinPoint, null, e);
    }
}