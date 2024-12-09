package My422Project.BlackBoxTestCases;

//Sample code for Halstead Vocabulary Testing

public class HalsteadVocabularyTest {

 public static void main(String[] args) {
     int FOR = 10; // Variable name is "FOR"
     int forValue = 20; // Variable name is "forValue"
     
     // FOR and forValue should be counted as distinct operands.
     int result = FOR + forValue; // '+' is an operator
     
     // Case-insensitivity for keywords "for" and "FOR"
     for (int i = 0; i < result; i++) { // 'for', '<', '++', ';' are operators
         if (i % 2 == 0) { // 'if', '%', '==', '0' are operators/operands
             System.out.println("Even: " + i); // 'System.out.println' is an operand
         }
     }

     // External library reference
     Math.pow(2, 3); // External method reference to test unique operands
 }
}

/* 
Expected Halstead Vocabulary Calculation:

- Unique Operators: 
+, <, ==, %, ++, ;, if, for, ., (, )
(Total = 11)

- Unique Operands: 
FOR, forValue, result, i, 2, 3, 0, Math, System.out.println
(Total = 9)

Expected Halstead Vocabulary (n): 
11 (operators) + 9 (operands) = 20
*/

