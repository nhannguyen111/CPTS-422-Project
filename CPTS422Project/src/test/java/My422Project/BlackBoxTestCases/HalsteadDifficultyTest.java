package My422Project.BlackBoxTestCases;

public class HalsteadDifficultyTest {
    public static void main(String[] args) {
        // Test Case 1: Simple Arithmetic Operations
        int a = 5; // Operand
        int b = 10; // Operand
        int c = a + b; // Operators: =, + | Operands: a, b, c

        // Test Case 2: Compound Operators
        c += a; // Operator: += | Operand: c, a

        // Test Case 3: Loop with Conditional Operators
        for (int i = 0; i < 10; i++) { // Operators: =, <, ++, for | Operands: i, 10
            c = c * 2; // Operators: =, * | Operands: c, 2
        }

        // Test Case 4: Function Call with Arguments
        int result = multiply(c, b); // Operators: =, () | Operands: result, multiply, c, b
    }

    public static int multiply(int x, int y) { // Operators: (), return, * | Operands: x, y
        return x * y; // Operators: return, * | Operands: x, y
    }
}

//Expected Halstead Difficulty: 7.071