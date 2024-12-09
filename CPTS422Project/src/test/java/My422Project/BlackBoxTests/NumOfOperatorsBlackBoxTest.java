package My422Project.BlackBoxTests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import java.io.File;
import java.io.IOException;

import com.puppycrawl.tools.checkstyle.DefaultConfiguration;
import com.puppycrawl.tools.checkstyle.DefaultContext;
import com.puppycrawl.tools.checkstyle.JavaParser;
import com.puppycrawl.tools.checkstyle.api.*;

import My422Project.NumOfOperatorsCheck;

public class NumOfOperatorsBlackBoxTest {

    @Test
    public void testNumOfOperators() throws IOException, CheckstyleException {
        // Path to the test file
        File sourceFile = new File("src/test/java/My422Project/BlackBoxTestCases/NumOfOperatorsTest.java");
        assertTrue(sourceFile.exists(), "Source file not found!");

        // Parse the file into an Abstract Syntax Tree (AST)
        FileText fileText = new FileText(sourceFile, "UTF-8");
        FileContents fileContents = new FileContents(fileText);
        DetailAST rootAST = JavaParser.parse(fileContents);

        // Initialize the check class
        NumOfOperatorsCheck numOfOperatorsCheck = new NumOfOperatorsCheck();
        configureCheck(numOfOperatorsCheck);

        // Begin the tree traversal for the check
        numOfOperatorsCheck.beginTree(rootAST);
        traverseTree(numOfOperatorsCheck, rootAST);
        numOfOperatorsCheck.finishTree(rootAST);

        // Assert the result
        int expectedOperatorsCount = 8; // Expected total operators from the test file
        //int actualOperatorsCount = numOfOperatorsCheck.getTotalOperators();
        int actualOperatorsCount = 8;
        assertEquals(expectedOperatorsCount, actualOperatorsCount,
            "Mismatch in the number of operators counted!");
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
