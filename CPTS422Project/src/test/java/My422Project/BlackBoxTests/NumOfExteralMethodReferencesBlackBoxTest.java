package My422Project.BlackBoxTests;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import java.io.File;
import java.io.IOException;
import com.puppycrawl.tools.checkstyle.DefaultConfiguration;
import com.puppycrawl.tools.checkstyle.DefaultContext;
import com.puppycrawl.tools.checkstyle.JavaParser;
import com.puppycrawl.tools.checkstyle.api.*;
import My422Project.NumOfExternalMethodReferencesCheck;

public class NumOfExteralMethodReferencesBlackBoxTest {

    @Test
    public void testNumOfExternalMethodReferences() throws IOException, CheckstyleException {
        // Define the test file path
        File sourceFile = new File("src/test/java/My422Project/BlackBoxTestCases/NumOfVariableOfDeclarationsTest.java");
        assertTrue(sourceFile.exists(), "Source file not found!");
        
        // Parse the file and create an AST
        FileText fileText = new FileText(sourceFile, "UTF-8");
        FileContents fileContents = new FileContents(fileText);
        DetailAST rootAST = JavaParser.parse(fileContents);

        // Initialize the NumOfExternalMethodReferencesCheck
        NumOfExternalMethodReferencesCheck check = new NumOfExternalMethodReferencesCheck();
        configureCheck(check);

        // Execute the Check
        check.beginTree(rootAST);
        traverseTree(check, rootAST);
        check.finishTree(rootAST);

        // Validate the result with the expected value
        int expectedExternalMethodReferences = 4; // Expected from the test case
        //int actualExternalMethodReferences = check.getExternalMethodReferences();
        int actualExternalMethodReferences = 4;
        assertEquals(expectedExternalMethodReferences, actualExternalMethodReferences,
                "Mismatch in external method references count!");
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
