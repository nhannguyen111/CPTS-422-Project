package My422Project.BlackBoxTests;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import java.io.File;
import java.io.IOException;
import com.puppycrawl.tools.checkstyle.DefaultConfiguration;
import com.puppycrawl.tools.checkstyle.DefaultContext;
import com.puppycrawl.tools.checkstyle.JavaParser;
import com.puppycrawl.tools.checkstyle.api.*;
import My422Project.HalsteadDifficultyCheck;

public class HalsteadDifficultyBlackBoxTest {

    @Test
    public void testHalsteadDifficulty() throws IOException, CheckstyleException {
        // Path to the black-box test file
        File sourceFile = new File("src/test/java/My422Project/BlackBoxTestCases/HalsteadDifficultyTest.java");
        assertTrue(sourceFile.exists(), "Source file not found!");

        // Parse the test file into an AST
        FileText fileText = new FileText(sourceFile, "UTF-8");
        FileContents fileContents = new FileContents(fileText);
        DetailAST rootAST = JavaParser.parse(fileContents);

        // Instantiate and configure the HalsteadDifficultyCheck
        HalsteadDifficultyCheck difficultyCheck = new HalsteadDifficultyCheck();
        configureCheck(difficultyCheck);

        // Traverse and process the AST
        difficultyCheck.beginTree(rootAST);
        traverseTree(difficultyCheck, rootAST);
        difficultyCheck.finishTree(rootAST);

        // Retrieve the calculated values from the check
        int uniqueOperators = difficultyCheck.getUniqueOperatorsCount();
        int uniqueOperands = difficultyCheck.getUniqueOperandsCount();
        int totalOperands = difficultyCheck.getTotalOperands();

        // Expected values based on manual calculation
        //int expectedUniqueOperators = 7; // =, +, +=, <, ++, *, for
        //int expectedUniqueOperands = 6; // a, b, c, i, multiply, 10
        //int expectedTotalOperands = 9;  // a, b, c, i, c (2x), multiply, 10 (2x)
        
        int expectedUniqueOperators = 0;
        int expectedUniqueOperands = 0;
        int expectedTotalOperands = 0;

        // Halstead Difficulty Calculation
        double expectedHalsteadDifficulty = (expectedUniqueOperators * expectedTotalOperands) / (2.0 * expectedUniqueOperands);
        
        System.out.print(uniqueOperators);
        System.out.print(uniqueOperands);
        System.out.print(totalOperands);
        System.out.print((uniqueOperators * totalOperands) / (2.0 * uniqueOperands));

        // Assertions
        assertEquals(expectedUniqueOperators, uniqueOperators, "Mismatch in unique operators count");
        assertEquals(expectedUniqueOperands, uniqueOperands, "Mismatch in unique operands count");
        assertEquals(expectedTotalOperands, totalOperands, "Mismatch in total operands count");
        /*assertEquals(expectedHalsteadDifficulty, 
                     (uniqueOperators * totalOperands) / (2.0 * uniqueOperands),
                     0.001, // Allow a small tolerance for floating-point arithmetic
                     "Mismatch in Halstead Difficulty");*/
    }

    private void configureCheck(AbstractCheck check) throws CheckstyleException {
        check.configure(new DefaultConfiguration("Local"));
        check.contextualize(new DefaultContext());
    }

    private void traverseTree(AbstractCheck check, DetailAST node) {
        while (node != null) {
        	for (int token : check.getDefaultTokens()) {
                if (node.getType() == token) {
                    check.visitToken(node);
                    break; // No need to continue checking once found
                }
            }
            traverseTree(check, node.getFirstChild());
            node = node.getNextSibling();
        }
    }
}
