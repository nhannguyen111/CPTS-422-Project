package My422Project.BlackBoxTestCases;

import java.util.Arrays;
import java.util.List;

public class NumOfOperatorsTest {
    public static void main(String[] args) {
        // Special operator test: Method reference (::)
        List<String> names = Arrays.asList("Alice", "Bob", "Charlie");
        names.forEach(System.out::println); // "::" operator here

        // Regular operators
        int a = 5, b = 10;
        int sum = a + b;  // "+" operator
        int diff = b - a; // "-" operator

        // Complex expression with multiple operators
        int result = (a * b) / (b - a) + a % b; // "*", "/", "-", "+", "%"
    }
}

// Expected Operators Count:
// :: (method reference) - 1
// + (addition) - 2
// - (subtraction) - 2
// * (multiplication) - 1
// / (division) - 1
// % (modulus) - 1
// Total operators = 8
