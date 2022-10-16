package com.dingyi.myluaapp.openapi.service


/**
 * Identifies injectable constructors, methods, and fields. May apply to static
 * as well as instance members. An injectable member may have any access
 * modifier (private, package-private, protected, public). Constructors are
 * injected first, followed by fields, and then methods. Fields and methods
 * in superclasses are injected before those in subclasses. Ordering of
 * injection among fields and among methods in the same class is not specified.
 *
 *
 * Injectable constructors are annotated with `@Inject` and accept
 * zero or more dependencies as arguments. `@Inject` can apply to at most
 * one constructor per class.
 *
 *
 * <tt></tt><blockquote style="padding-left: 2em; text-indent: -2em;">@Inject
 * *ConstructorModifiers<sub>opt</sub>*
 * *SimpleTypeName*(*FormalParameterList<sub>opt</sub>*)
 * *Throws<sub>opt</sub>*
 * *ConstructorBody*</blockquote>
 *
 *
 * `@Inject` is optional for public, no-argument constructors when no
 * other constructors are present. This enables injectors to invoke default
 * constructors.
 *
 *
 * <tt></tt><blockquote style="padding-left: 2em; text-indent: -2em;">
 * @Inject<sub>*opt*</sub>
 * *Annotations<sub>opt</sub>*
 * public
 * *SimpleTypeName*()
 * *Throws<sub>opt</sub>*
 * *ConstructorBody*</blockquote>
 *
 *
 * Injectable fields:
 *
 *  * are annotated with `@Inject`.
 *  * are not final.
 *  * may have any otherwise valid name.
 *
 *
 * <tt></tt><blockquote style="padding-left: 2em; text-indent: -2em;">@Inject
 * *FieldModifiers<sub>opt</sub>*
 * *Type*
 * *VariableDeclarators*;</blockquote>
 *
 *
 * Injectable methods:
 *
 *  * are annotated with `@Inject`.
 *  * are not abstract.
 *  * do not declare type parameters of their own.
 *  * may return a result
 *  * may have any otherwise valid name.
 *  * accept zero or more dependencies as arguments.
 *
 *
 * <tt></tt><blockquote style="padding-left: 2em; text-indent: -2em;">@Inject
 * *MethodModifiers<sub>opt</sub>*
 * *ResultType*
 * *Identifier*(*FormalParameterList<sub>opt</sub>*)
 * *Throws<sub>opt</sub>*
 * *MethodBody*</blockquote>
 *
 *
 * The injector ignores the result of an injected method, but
 * non-`void` return types are allowed to support use of the method in
 * other contexts (builder-style method chaining, for example).
 *
 *
 * Examples:
 *
 * <pre>
 * public class Car {
 * // Injectable constructor
 * &#064;Inject public Car(Engine engine) { ... }
 *
 * // Injectable field
 * &#064;Inject private Provider&lt;Seat> seatProvider;
 *
 * // Injectable package-private method
 * &#064;Inject void install(Windshield windshield, Trunk trunk) { ... }
 * }</pre>
 *
 *
 * A method annotated with `@Inject` that overrides another method
 * annotated with `@Inject` will only be injected once per injection
 * request per instance. A method with *no* `@Inject` annotation
 * that overrides a method annotated with `@Inject` will not be
 * injected.
 *
 *
 * Injection of members annotated with `@Inject` is required. While an
 * injectable member may use any accessibility modifier (including
 * <tt>private</tt>), platform or injector limitations (like security
 * restrictions or lack of reflection support) might preclude injection
 * of non-public members.
 *
 * <h3>Qualifiers</h3>
 *
 *
 * A [qualifier][Qualifier] may annotate an injectable field
 * or parameter and, combined with the type, identify the implementation to
 * inject. Qualifiers are optional, and when used with `@Inject` in
 * injector-independent classes, no more than one qualifier should annotate a
 * single field or parameter. The qualifiers are bold in the following example:
 *
 * <pre>
 * public class Car {
 * &#064;Inject private **@Leather** Provider&lt;Seat> seatProvider;
 *
 * &#064;Inject void install(**@Tinted** Windshield windshield,
 * **@Big** Trunk trunk) { ... }
 * }</pre>
 *
 *
 * If one injectable method overrides another, the overriding method's
 * parameters do not automatically inherit qualifiers from the overridden
 * method's parameters.
 *
 * <h3>Injectable Values</h3>
 *
 *
 * For a given type T and optional qualifier, an injector must be able to
 * inject a user-specified class that:
 *
 *
 *  1. is assignment compatible with T and
 *  1. has an injectable constructor.
 *
 *
 *
 * For example, the user might use external configuration to pick an
 * implementation of T. Beyond that, which values are injected depend upon the
 * injector implementation and its configuration.
 *
 * <h3>Circular Dependencies</h3>
 *
 *
 * Detecting and resolving circular dependencies is left as an exercise for
 * the injector implementation. Circular dependencies between two constructors
 * is an obvious problem, but you can also have a circular dependency between
 * injectable fields or methods:
 *
 * <pre>
 * class A {
 * &#064;Inject B b;
 * }
 * class B {
 * &#064;Inject A a;
 * }</pre>
 *
 *
 * When constructing an instance of `A`, a naive injector
 * implementation might go into an infinite loop constructing an instance of
 * `B` to set on `A`, a second instance of `A` to set on
 * `B`, a second instance of `B` to set on the second instance of
 * `A`, and so on.
 *
 *
 * A conservative injector might detect the circular dependency at build
 * time and generate an error, at which point the programmer could break the
 * circular dependency by injecting [Provider&amp;lt;A&gt;][Provider] or `Provider<B>` instead of `A` or `B` respectively. Calling [ ][Provider.get] on the provider directly from the constructor or
 * method it was injected into defeats the provider's ability to break up
 * circular dependencies. In the case of method or field injection, scoping
 * one of the dependencies (using [singleton scope][Singleton], for
 * example) may also enable a valid circular relationship.
 *
 */
@Target(
    AnnotationTarget.FUNCTION,
    AnnotationTarget.PROPERTY_GETTER,
    AnnotationTarget.PROPERTY_SETTER,
    AnnotationTarget.CONSTRUCTOR,
    AnnotationTarget.FIELD
)
@kotlin.annotation.Retention(AnnotationRetention.RUNTIME)
@MustBeDocumented
annotation class Inject
