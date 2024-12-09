package My422Project.BlackBoxTestCases;

public class LocalMethodReferenceTest {

    // A simple helper method.
    private void helperMethod1() {
        System.out.println("Helper Method 1 invoked");
    }

    // Another helper method.
    private void helperMethod2() {
        System.out.println("Helper Method 2 invoked");
    }

    public void testDirectCall() {
        // Direct call to a local method.
        helperMethod1();
    }

    public void testLambdaCall() {
        // Lambda referencing a local method.
        Runnable lambda = () -> helperMethod1();
        lambda.run();
    }

    public void testMethodReference() {
        // Method reference to a local method.
        Runnable methodRef = this::helperMethod2;
        methodRef.run();
    }

    public void testChainedCall() {
        // Chaining local method references.
        Runnable chained = () -> {
            helperMethod1();
            helperMethod2();
        };
        chained.run();
    }

    public void testDynamicInvocation() {
        // Dynamic method selection via a functional interface.
        java.util.function.Consumer<String> consumer = (input) -> {
            if ("case1".equals(input)) {
                helperMethod1();
            } else {
                helperMethod2();
            }
        };
        consumer.accept("case1");
        consumer.accept("case2");
    }

    public void testNestedLambdas() {
        // Nested lambdas calling local methods.
        Runnable outerLambda = () -> {
            Runnable innerLambda = () -> helperMethod1();
            innerLambda.run();
        };
        outerLambda.run();
    }

    public static void main(String[] args) {
        LocalMethodReferenceTest test = new LocalMethodReferenceTest();

        // Execute all test cases.
        test.testDirectCall();
        test.testLambdaCall();
        test.testMethodReference();
        test.testChainedCall();
        test.testDynamicInvocation();
        test.testNestedLambdas();
    }
}

/*
Expected Results:
1. testDirectCall: 1 local method reference (helperMethod1).
2. testLambdaCall: 1 local method reference (helperMethod1 via lambda).
3. testMethodReference: 1 local method reference (helperMethod2 via method reference).
4. testChainedCall: 2 local method references (helperMethod1, helperMethod2).
5. testDynamicInvocation: 2 local method references (helperMethod1, helperMethod2 via dynamic invocation).
6. testNestedLambdas: 1 local method reference (helperMethod1 via nested lambda).

Total Local Method References: 8
*/
