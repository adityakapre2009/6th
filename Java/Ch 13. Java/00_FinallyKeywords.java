/*
13.2 Return from Finally: In Java, does the finally block get executed if we insert a return statement
inside the try block of a try-catch-finally?

SOLUTION
Yes, it will get executed. The finally block gets executed when the try block exits. 
Even when we attempt to exit within the try block (via a return statement, a continue statement, 
a break statement or any exception), the finally block will still be executed.
Note that there are some cases in which the finally block will not get executed, such as the following:
If the virtual machine exits during try/ catch block execution.
If the thread which is executing during the try/ catch block gets killed.


13.3 Final, etc.: What is the difference between final, finally, and finalize?
SOLUTIONS

Despite their similar sounding names, final, finally and finalize have very different purposes.
To speak in very general terms, final is used to control whether a variable, method, or class is "changeable:'
The finally keyword is used in a try/ catch block to ensure that a segment of code is always
executed. The finalize() method is called by the garbage collector once it determines that no more
references exist.
Further detail on these keywords and methods is provided below.

final
------
The final statement has a different meaning depending on its context.
When applied to a variable (primitive): The value of the variable cannot change.
• When applied to a variable (reference): The reference variable cannot point to any other object on the
heap.
• When applied to a method: The method cannot be overridden.
• When applied to a class:The class cannot be subclassed.

finally keyword
---------------
There is an optional finally block after the try block or after the catch block. Statements in the
finally block will always be executed, even if an exception is thrown (except if Java Virtual Machine exits
from the try block). The finally block is often used to write the clean-up code. It will be executed after
the try and catch blocks, but before control transfers back to its origin.
Watch how this plays out in the example below.
1 public static String lem() {
2 System.out.println("lem");
3 return "return from lem";
4 }
5
6 public static String foo() {
7 int X 0j
8 int y = 5;
9 try {
10 System.out.println("start try");
11 int b = y / x;
12 System.out.println("end try");
13 return "returned from try";
14 } catch (Exception ex) {
15 System.out.println("catch");
16 return lem() + " I returned from catch";
17 } finally {
18 System.out.println("finally");
19 }
20 }
21
22 public static void bar() {
23 System.out.println("start bar");
24 String v = foo();
25 System.out.println(v);
26 System. out. println("end bar");
27 }
28
29 public static void main(String[] args) {
30 bar();
31 }

The output for this code is the following:
1 start bar
2 start try
3 catch
4 lem
5 finally
6 return from lem \ returned from catch
7 end bar

Look carefully at lines 3 to 5 in the output. The catch block is fully executed (including the function call in
the return statement), then the finally block, and then the function actually returns.

Note: If the JVM exits while the try or catch code is being executed, then the finally block may not execute. 
Likewise, if the thread executing the try or catch code is interrupted or killed, the finally block may not 
execute even though the application as a whole continues.

Important: The finally block is a key tool for preventing resource leaks. 
When closing a file or otherwise recovering resources, place the code in a finally block to ensure 
that resource is always recovered.


finalize()
----------
The automatic garbage collector calls the finalize () method just before actually destroying the object.
A class can therefore override the finalize () method from the Object class in order to define custom
behavior during garbage collection.
1 protected void finalize() throws Throwable {
2 // Close open files, release resources, etc 
3 }

13.4 Generics vs. Templates: Explain the difference between templates in C ++ and generics in Java.

SOLUTION
Many programmers consider templates and generics to be essentially equivalent because both allow you
to do something like List<String>. But, how each language does this, and why, varies significantly.
The implementation of Java generics is rooted in an idea of"type erasure:'This technique eliminates the
parameterized types when source code is translated to the Java Virtual Machine (JVM) byte code.

For example, suppose you have the Java code below:
1 Vector<String> vector = new Vector<String>();
2 vector.add(new String("hello"));
3 String str = vector.get(0);

During compilation, this code is re-written into:
1 Vector vector = new Vector();
2 vector. add (new String("hello"));
3 String str = (String) vector.get(0);

The use of Java generics didn't really change much about our capabilities; it just made things a bit prettier.
For this reason, Java generics are sometimes called" syntactic sugar:'
This is quite different from C++. In C++, templates are essentially a glorified macro set, with the compiler
creating a new copy of the template code for each type. Proof of this is in the fact that an instance of
MyClass<Foo> will not share a static variable with MyClass<Bar>. Two instances of MyClass<Foo>,
however, will share a static variable.
To illustrate this, consider the code below:
1 // MyClass.h
2 template<class T> class MyClass {
3 public:
4 static int val;
5 MyClass(int v) { val v;}
6 };
7
8 // MyClass.cpp
9 template<typename T>
10 int MyClass<T>::bar;
11
12 template class MyClass<Foo>;
13 template class MyClass<Bar>;
14
15 // main.cpp
16 MyClass<Foo> * fool
17 MyClass<Foo> * foo2
18 MyClass<Bar> * barl
19 MyClass<Bar> * bar2
20
new MyClass<Foo>(10);
new MyClass<Foo>(15);
new MyClass<Bar>(20);
new MyClass<Bar>(35);
21 int fl fool->val; // will equal 15
22 int f2 foo2->val; // will equal 15
23 int bl barl->val; // will equal 35
24 int b2 bar2->val; // will equal 35

In Java, static variables are shared across instances of MyClass, regardless of the different type parameters.
Java generics and C ++ templates have a number of other differences. These include:
C ++ templates can use primitive types, like int. Java cannot and must instead use Integer.
In Java, you can restrict the template's type parameters to be of a certain type. For instance, you might use
generics to implement a Card Deck and specify that the type parameter must extend from CardGame.
In C++, the type parameter can be instantiated, whereas Java does not support this.
In Java, the type parameter (i.e., the Foo in MyClass<Foo>) cannot be used for static methods and
variables, since these would be shared between MyClass<Foo> and MyClass<Bar>. In C++, these
classes are different, so the type parameter can be used for static methods and variables.
In Java, all instances of MyClass, regardless of their type parameters, are the same type. The type
parameters are erased at runtime. In C++, instances with different type parameters are different types.
Remember: Although Java generics and C ++ templates look the same in many ways, they are very different.
*/
