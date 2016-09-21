package com.android.base.frame.presenter.factory;

import com.android.base.frame.presenter.XPresenter;

import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/*
    @Target　        说明了Annotation所修饰的对象范围。　　　
    @Retention       定义了该Annotation被保留的时间长短
    @Documented　　　 用于描述该注解将被包含在javadoc中
    @Inherited       用于描述子类可以继承父类中的该注解
 */

/*
    @Target(ElementType.TYPE)   //接口、类、枚举、注解
    @Target(ElementType.FIELD) //字段、枚举的常量
    @Target(ElementType.METHOD) //方法
    @Target(ElementType.PARAMETER) //方法参数
    @Target(ElementType.CONSTRUCTOR)  //构造函数
    @Target(ElementType.LOCAL_VARIABLE)//局部变量
    @Target(ElementType.ANNOTATION_TYPE)//注解
    @Target(ElementType.PACKAGE) ///包
*/

/*
    @Retention(RetentionPolicy.SOURCE)   //注解仅存在于源码中，在class字节码文件中不包含
    @Retention(RetentionPolicy.CLASS)     // 默认的保留策略，注解会在class字节码文件中存在，但运行时无法获得，
    @Retention(RetentionPolicy.RUNTIME)  // 注解会在class字节码文件中存在，在运行时可以通过反射获取到
*/

@Inherited
@Retention(RetentionPolicy.RUNTIME)
public @interface RequiresPresenter {
    Class<? extends XPresenter> value();
}
