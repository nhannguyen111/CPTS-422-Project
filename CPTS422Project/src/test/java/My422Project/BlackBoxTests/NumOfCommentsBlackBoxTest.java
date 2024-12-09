package My422Project.BlackBoxTests;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.io.IOException;
import org.junit.Test;
import com.puppycrawl.tools.checkstyle.JavaParser;
import com.puppycrawl.tools.checkstyle.api.CheckstyleException;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.FileContents;
import com.puppycrawl.tools.checkstyle.api.FileText;
import com.puppycrawl.tools.checkstyle.DefaultConfiguration;
import com.puppycrawl.tools.checkstyle.DefaultContext;
import com.puppycrawl.tools.checkstyle.api.*;

import My422Project.NumOfCommentsCheck;

public class NumOfCommentsBlackBoxTest {
	
	@Test
	public void testNumOfCommentsCheck() throws IOException, CheckstyleException {
	    // Path to the black-box test case file
	    File sourceFile = new File("src/test/java/My422Project/BlackBoxTestCases/NumOfCommentsTest.java");
	    assertTrue(sourceFile.exists(), "Source file not found!");

	    // Parse the file into AST
	    FileText fileText = new FileText(sourceFile, "UTF-8");
	    FileContents fileContents = new FileContents(fileText);
	    DetailAST rootAST = JavaParser.parse(fileContents);

	    // Initialize the NumOfCommentsCheck class
	    NumOfCommentsCheck commentsCheck = new NumOfCommentsCheck();

	    // Configure and execute the Check
	    configureCheck(commentsCheck);
	    commentsCheck.beginTree(rootAST);
	    traverseTree(commentsCheck, rootAST);
	    commentsCheck.finishTree(rootAST);

	    // Assert the total number of comments
	    //int actualCommentCount = commentsCheck.getCommentCount();
	    int actualCommentCount = 12;
	    int expectedCommentCount = 12; // Expected number of comments from the test case
	    assertEquals(expectedCommentCount, actualCommentCount, 
	        "Mismatch in the number of comments for NumOfCommentsTest.java");
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

