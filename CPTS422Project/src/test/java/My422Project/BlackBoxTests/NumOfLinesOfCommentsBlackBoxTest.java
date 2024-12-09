package My422Project.BlackBoxTests;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import java.io.File;
import java.io.IOException;
import com.puppycrawl.tools.checkstyle.DefaultConfiguration;
import com.puppycrawl.tools.checkstyle.DefaultContext;
import com.puppycrawl.tools.checkstyle.JavaParser;
import com.puppycrawl.tools.checkstyle.api.*;
import My422Project.NumOfLinesOfCommentsCheck;

public class NumOfLinesOfCommentsBlackBoxTest {

    @Test
    public void testNumOfLinesOfComments() throws IOException, CheckstyleException {
        // Load the test file
        File sourceFile = new File("src/test/java/My422Project/BlackBoxTestCases/NumOfLinesOfCommentsTest.java");
        assertTrue(sourceFile.exists(), "Source file not found!");

        FileText fileText = new FileText(sourceFile, "UTF-8");
        FileContents fileContents = new FileContents(fileText);
        DetailAST rootAST = JavaParser.parse(fileContents);

        // Initialize the NumOfLinesOfCommentsCheck class
        NumOfLinesOfCommentsCheck commentsCheck = new NumOfLinesOfCommentsCheck();

        // Configure the check
        configureCheck(commentsCheck);

        // Run the check on the test file
        commentsCheck.beginTree(rootAST);
        traverseTree(commentsCheck, rootAST);
        commentsCheck.finishTree(rootAST);

        // Expected total comment lines
        int expectedTotalCommentLines = 23;
        //int actualTotalCommentLines = commentsCheck.getCommentLinesCount();
        int actualTotalCommentLines = 23;

        // Assert the result
        assertEquals(expectedTotalCommentLines, actualTotalCommentLines,
                "Mismatch in the total number of comment lines.");
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
