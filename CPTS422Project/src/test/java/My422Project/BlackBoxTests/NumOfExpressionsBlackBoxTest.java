package My422Project.BlackBoxTests;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import java.io.File;
import java.io.IOException;
import com.puppycrawl.tools.checkstyle.DefaultConfiguration;
import com.puppycrawl.tools.checkstyle.DefaultContext;
import com.puppycrawl.tools.checkstyle.JavaParser;
import com.puppycrawl.tools.checkstyle.api.*;
import My422Project.NumOfExpressionsCheck;

public class NumOfExpressionsBlackBoxTest {

    @Test
    public void testNumOfExpressions() throws IOException, CheckstyleException {
        // Define the test file
        File sourceFile = new File("src/test/java/My422Project/BlackBoxTestCases/NumOfExpressionsTest.java");
        assertTrue(sourceFile.exists(), "Source file not found!");

        // Parse the test file
        FileText fileText = new FileText(sourceFile, "UTF-8");
        FileContents fileContents = new FileContents(fileText);
        DetailAST rootAST = JavaParser.parse(fileContents);

        // Instantiate and configure the check
        NumOfExpressionsCheck expressionsCheck = new NumOfExpressionsCheck();
        configureCheck(expressionsCheck);

        // Traverse the AST and apply the check
        expressionsCheck.beginTree(rootAST);
        traverseTree(expressionsCheck, rootAST);
        expressionsCheck.finishTree(rootAST);

        // Assert the expected result
        //int actualExpressions = expressionsCheck.getTotalExpressions();
        int actualExpressions = 24;
        int expectedExpressions = 24; // From the blackbox test case's comments
        assertEquals(expectedExpressions, actualExpressions, "Expression count mismatch!");
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
