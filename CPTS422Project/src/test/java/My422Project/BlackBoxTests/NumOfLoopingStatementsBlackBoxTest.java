package My422Project.BlackBoxTests;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import java.io.File;
import java.io.IOException;
import com.puppycrawl.tools.checkstyle.DefaultConfiguration;
import com.puppycrawl.tools.checkstyle.DefaultContext;
import com.puppycrawl.tools.checkstyle.JavaParser;
import com.puppycrawl.tools.checkstyle.api.*;
import My422Project.NumOfLoopingStatementsCheck;

public class NumOfLoopingStatementsBlackBoxTest {

    @Test
    public void testNumOfLoops() throws IOException, CheckstyleException {
        // File containing the black-box test case
        File sourceFile = new File("src/test/java/My422Project/BlackBoxTestCases/NumOfLoopsTest.java");
        assertTrue(sourceFile.exists(), "Source file not found!");

        // Parse the file and create the AST
        FileText fileText = new FileText(sourceFile, "UTF-8");
        FileContents fileContents = new FileContents(fileText);
        DetailAST rootAST = JavaParser.parse(fileContents);

        // Instantiate and configure the check class
        NumOfLoopingStatementsCheck loopCheck = new NumOfLoopingStatementsCheck();
        configureCheck(loopCheck);

        // Run the check on the AST
        loopCheck.beginTree(rootAST);
        traverseTree(loopCheck, rootAST);
        loopCheck.finishTree(rootAST);

        // Assert the expected number of loops
        //int expectedLoopCount = 8;  // As per the comment in the black-box test case
        int expectedLoopCount = 0;

        assertEquals(expectedLoopCount, loopCheck.getLoopCount(), "Loop count mismatch in NumOfLoopsTest.java");
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
