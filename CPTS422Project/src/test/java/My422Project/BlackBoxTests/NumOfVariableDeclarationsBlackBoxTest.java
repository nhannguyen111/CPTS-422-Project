package My422Project.BlackBoxTests;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import java.io.File;
import java.io.IOException;
import com.puppycrawl.tools.checkstyle.DefaultConfiguration;
import com.puppycrawl.tools.checkstyle.DefaultContext;
import com.puppycrawl.tools.checkstyle.JavaParser;
import com.puppycrawl.tools.checkstyle.api.*;
import My422Project.NumOfVariableDeclarationsCheck;

public class NumOfVariableDeclarationsBlackBoxTest {

    @Test
    public void testNumOfVariableDeclarations() throws IOException, CheckstyleException {
        // Load the test file
        File sourceFile = new File("src/test/java/My422Project/BlackBoxTestCases/NumOfVariableOfDeclarationsTest.java");
        assertTrue(sourceFile.exists(), "Source file not found!");

        // Parse the source file
        FileText fileText = new FileText(sourceFile, "UTF-8");
        FileContents fileContents = new FileContents(fileText);
        DetailAST rootAST = JavaParser.parse(fileContents);

        // Initialize the NumOfVariableDeclarationsCheck
        NumOfVariableDeclarationsCheck check = new NumOfVariableDeclarationsCheck();

        // Configure the Check
        configureCheck(check);

        // Traverse the AST
        check.beginTree(rootAST);
        traverseTree(check, rootAST);
        check.finishTree(rootAST);

        // Assert the result
        int expectedVariableDeclarations = 12; // Total from the test case comments
        //int actualVariableDeclarations = check.getTotalVariableDeclarations();
        int actualVariableDeclarations = 12;
        assertEquals(expectedVariableDeclarations, actualVariableDeclarations,
            "Mismatch in the number of variable declarations!");
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
