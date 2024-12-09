package My422Project.BlackBoxTestCases;

public class NumOfExpressionsTest {

    public static void main(String[] args) {
        // Example 1: Simple if condition
        int a = 5, b = 10;
        if (a < b) { // Expression: a < b
            System.out.println("a is less than b"); // Expression: method call
        }

        // Example 2: Nested if with logical operators
        if (a < b && b > 0) { // Expressions: a < b, b > 0, a < b && b > 0
            System.out.println("Nested if condition is true"); // Expression: method call
        }

        // Example 3: While loop with compound condition
        while (a < b && a < 10) { // Expressions: a < b, a < 10, a < b && a < 10
            a++; // Expression: a++
        }

        // Example 4: Switch statement with case and default
        int c = 2;
        switch (c) { // Expression: c
            case 1: // Expression: c == 1
                System.out.println("Case 1"); // Expression: method call
                break; // Expression: break (optional for some counts)
            case 2: // Expression: c == 2
                System.out.println("Case 2"); // Expression: method call
                break; // Expression: break
            default: // Expression: c does not match any case
                System.out.println("Default case"); // Expression: method call
        }

        // Example 5: For loop with complex condition
        for (int i = 0; i < 10 && i % 2 == 0; i++) { // Expressions: i = 0, i < 10, i % 2 == 0, i < 10 && i % 2 == 0, i++
            System.out.println(i); // Expression: method call
        }
    }
}

/*
Expected Number of Expressions:
1. if (a < b): 1 (a < b)
2. System.out.println("a is less than b"): 1 (method call)
3. if (a < b && b > 0): 3 (a < b, b > 0, a < b && b > 0)
4. System.out.println("Nested if condition is true"): 1 (method call)
5. while (a < b && a < 10): 3 (a < b, a < 10, a < b && a < 10)
6. a++: 1 (increment)
7. switch (c): 1 (c)
8. case 1: 1 (c == 1)
9. System.out.println("Case 1"): 1 (method call)
10. break: 1 (optional in some counts)
11. case 2: 1 (c == 2)
12. System.out.println("Case 2"): 1 (method call)
13. break: 1 (optional in some counts)
14. default: 1 (c does not match any case)
15. System.out.println("Default case"): 1 (method call)
16. for (int i = 0; i < 10 && i % 2 == 0; i++):
    - i = 0: 1
    - i < 10: 1
    - i % 2 == 0: 1
    - i < 10 && i % 2 == 0: 1 (compound condition)
    - i++: 1 (increment)
17. System.out.println(i): 1 (method call)

Total Expressions: 24
*/
