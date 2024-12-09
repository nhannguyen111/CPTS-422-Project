package My422Project.BlackBoxTestCases;

import java.lang.reflect.Method;

public class NumOfExternalMethodReferencesTest {
    // Static external method reference
    public static void staticExternalMethod() {
        System.out.println("Static external method called");
    }

    // Dynamic external method resolution using reflection
    public static void dynamicMethod(String message) {
        System.out.println("Dynamic external method called: " + message);
    }

    // Main test cases
    public static void main(String[] args) {
        int count = 0;

        // Test Case 1: Static external method call
        ExternalMethodTest.calculateSum(1,1);
        count++; // Expected: 1 external method reference

        // Test Case 2: Dynamic method call using reflection
        try {
            Method method = ExternalMethodTest.class.getMethod("dynamicMethod", String.class);
            method.invoke(null, "Hello, Reflection!");
            count++; // Expected: 2 external method references
        } catch (Exception e) {
            System.out.println("Reflection error: " + e.getMessage());
        }

        // Test Case 3: External method resolved at runtime (polymorphism)
        ExternalInterface externalObject = new ExternalClass();
        externalObject.externalMethod();
        count++; // Expected: 3 external method references

        // Test Case 4: Calling a method from an external library (simulated here)
        Math.abs(-10); // External library method
        count++; // Expected: 4 external method references

        // Output the manually tracked expected count
        System.out.println("Expected number of external method references: " + count);
    }
}

// Example interface for polymorphism testing
interface ExternalInterface {
    void externalMethod();
}

// Example external class implementing the interface
class ExternalClass implements ExternalInterface {
    @Override
    public void externalMethod() {
        System.out.println("External class method called");
    }
}

/*
Expected Result:
There are 4 external method references:
1. Static external method call (Test Case 1)
2. Dynamic method call via reflection (Test Case 2)
3. Runtime polymorphic method resolution (Test Case 3)
4. External library method call (Test Case 4)
*/
