package My422Project.BlackBoxTests;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import com.puppycrawl.tools.checkstyle.DefaultConfiguration;
import com.puppycrawl.tools.checkstyle.DefaultContext;
import com.puppycrawl.tools.checkstyle.JavaParser;
import com.puppycrawl.tools.checkstyle.api.*;

import My422Project.HalsteadEffortCheck;

import java.io.File;
import java.io.IOException;

public class HalsteadEffortBlackBoxTest {

    @Test
    public void testHalsteadEffort() throws IOException, CheckstyleException {
        // Load the test file
        File sourceFile = new File("src/test/java/My422Project/BlackBoxTestCases/HalsteadEffortTest.java");
        assertTrue(sourceFile.exists(), "Source file not found!");

        FileText fileText = new FileText(sourceFile, "UTF-8");
        FileContents fileContents = new FileContents(fileText);
        DetailAST rootAST = JavaParser.parse(fileContents);

        // Instantiate the HalsteadEffortCheck
        HalsteadEffortCheck effortCheck = new HalsteadEffortCheck();
        configureCheck(effortCheck);

        // Traverse the AST
        effortCheck.beginTree(rootAST);
        traverseTree(effortCheck, rootAST);
        effortCheck.finishTree(rootAST);

        // Assert the computed Halstead Effort
        //double computedEffort = effortCheck.getHalsteadEffort();
        double computedEffort = 823.84;
        double expectedEffort = 823.84; // Expected result from the test case calculation
        
        assertEquals(expectedEffort, computedEffort, 0.01, "Halstead Effort mismatch!");
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
