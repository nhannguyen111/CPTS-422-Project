package My422Project.BlackBoxTestCases;

public class HalsteadLengthTest {
    public static void main(String[] args) {
        // 1. Basic arithmetic operators
        int a = 5 + 3; // Operators: +, =; Operands: a, 5, 3
        int b = a * 2; // Operators: *, =; Operands: b, a, 2

        // 2. Conditional (ternary) operator
        int c = (a > b) ? a : b; // Operators: >, ?, :, =, (); Operands: c, a, b

        // 3. Logical operators
        boolean d = (a > 5) && (b < 10); // Operators: >, <, &&, (), =; Operands: d, a, b, 5, 10

        // 4. Lambda expression
        Runnable r = () -> System.out.println("Hello!"); // Operators: ->, ., (); Operands: r, System.out, "Hello!"

        // 5. Literals
        String str = "Example"; // Operators: =; Operands: str, "Example"

        // 6. Array indexing
        int[] arr = {1, 2, 3}; // Operators: =, {}; Operands: arr, 1, 2, 3
        int e = arr[0] + arr[1]; // Operators: [], +, =; Operands: e, arr, 0, 1

        // 7. Method call with multiple arguments
        printSum(a, b); // Operators: (); Operands: printSum, a, b
    }

    public static void printSum(int x, int y) {
        System.out.println(x + y); // Operators: ., +, (); Operands: System.out, x, y
    }
}

// EXPECTED HALSTEAD LENGTH RESULT:
// Operators:
// +, =, *, >, ?, :, &&, <, ., ->, (), {}, [], (13 unique operators)
// Usage count: 22 occurrences
//
// Operands:
// a, b, c, d, e, str, arr, printSum, System.out, "Example", "Hello!", 5, 3, 2, 10, 1, 0 (17 unique operands)
// Usage count: 27 occurrences
//
// TOTAL HALSTEAD LENGTH: Operators (22) + Operands (27) = 49
