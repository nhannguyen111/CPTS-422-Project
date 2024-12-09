package My422Project.BlackBoxTestCases;

public class HalsteadEffortTest {
 public int calculateSum(int a, int b) {
     int sum = a + b; // Simple addition
     if (sum > 0) {   // Conditional check
         return sum * 2; // Multiply and return
     } else {
         return 0; // Default case
     }
 }

 public void printMessage(String message) {
     for (int i = 0; i < 5; i++) { // Looping construct
         System.out.println(message + i); // Concatenation and print
     }
 }
}

/* Halstead Metrics Calculation:
* Total Operators (N1): 13  --> {public, int, =, +, if, >, *, else, return, void, for, <, ++}
* Total Operands (N2): 14  --> {a, b, sum, 0, 2, 5, message, i, "System.out.println"}
* Unique Operators (n1): 10 --> {public, int, =, +, if, >, *, else, return, for}
* Unique Operands (n2): 9  --> {a, b, sum, 0, 2, 5, message, i, "System.out.println"}
*
* Halstead Length (N): 27 = N1 + N2
* Halstead Vocabulary (n): 19 = n1 + n2
* Halstead Volume (V): 114.05 = N * log2(n)
* Halstead Difficulty (D): 7.22 = (n1 / 2) * (N2 / n2)
* Halstead Effort (E): 823.84 = D * V
*
* Expected Halstead Effort: 823.84
*/
