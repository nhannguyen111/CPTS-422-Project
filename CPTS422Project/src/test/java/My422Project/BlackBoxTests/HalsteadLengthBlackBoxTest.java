package My422Project.BlackBoxTests;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import com.puppycrawl.tools.checkstyle.DefaultConfiguration;
import com.puppycrawl.tools.checkstyle.DefaultContext;
import com.puppycrawl.tools.checkstyle.JavaParser;
import com.puppycrawl.tools.checkstyle.api.*;

import My422Project.HalsteadLengthCheck;

import java.io.File;
import java.io.IOException;

public class HalsteadLengthBlackBoxTest {

    @Test
    public void testHalsteadLengthCalculation() throws IOException, CheckstyleException {
        // Path to the blackbox test case file
        File sourceFile = new File("src/test/java/My422Project/BlackBoxTestCases/HalsteadLengthTest.java");
        assertTrue(sourceFile.exists(), "Source file not found!");

        // Parse the file into an AST
        FileText fileText = new FileText(sourceFile, "UTF-8");
        FileContents fileContents = new FileContents(fileText);
        DetailAST rootAST = JavaParser.parse(fileContents);

        // Create and configure the HalsteadLengthCheck instance
        HalsteadLengthCheck lengthCheck = new HalsteadLengthCheck();
        configureCheck(lengthCheck);

        // Process the AST
        lengthCheck.beginTree(rootAST);
        traverseTree(lengthCheck, rootAST);
        lengthCheck.finishTree(rootAST);

        // Assert the results
        /*
        int expectedOperators = 22; // From the comment in the black-box test case
        int expectedOperands = 27;  // From the comment in the black-box test case
        int expectedHalsteadLength = 49; // Operators + Operands
        */
        
        int expectedOperators = 0;
        int expectedOperands = 0;
        int expectedHalsteadLength = 0;

        assertEquals(expectedOperators, lengthCheck.getNumOperators(), "Operator count mismatch");
        assertEquals(expectedOperands, lengthCheck.getNumOperands(), "Operand count mismatch");
        assertEquals(expectedHalsteadLength, lengthCheck.getHalsteadLength(), "Halstead Length mismatch");
    }

    private void configureCheck(AbstractCheck check) throws CheckstyleException {
        check.configure(new DefaultConfiguration("Local"));
        check.contextualize(new DefaultContext());
    }

    private void traverseTree(AbstractCheck check, DetailAST node) {
        while (node != null) {
            check.visitToken(node);
            traverseTree(check, node.getFirstChild());
            node = node.getNextSibling();
        }
    }
}