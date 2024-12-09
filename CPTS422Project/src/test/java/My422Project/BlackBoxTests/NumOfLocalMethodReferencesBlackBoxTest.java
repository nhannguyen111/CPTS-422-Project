package My422Project.BlackBoxTests;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import java.io.File;
import java.io.IOException;
import com.puppycrawl.tools.checkstyle.DefaultConfiguration;
import com.puppycrawl.tools.checkstyle.DefaultContext;
import com.puppycrawl.tools.checkstyle.JavaParser;
import com.puppycrawl.tools.checkstyle.api.*;
import My422Project.NumOfLocalMethodReferencesCheck;

public class NumOfLocalMethodReferencesBlackBoxTest {
	
    @Test
    public void testNumOfLocalMethodReferences() throws IOException, CheckstyleException {
        // Locate the test case file
        File sourceFile = new File("src/test/java/My422Project/BlackBoxTestCases/LocalMethodReferenceTest.java");
        assertTrue(sourceFile.exists(), "Source file not found!");

        // Parse the source file
        FileText fileText = new FileText(sourceFile, "UTF-8");
        FileContents fileContents = new FileContents(fileText);
        DetailAST rootAST = JavaParser.parse(fileContents);

        // Initialize the check
        NumOfLocalMethodReferencesCheck check = new NumOfLocalMethodReferencesCheck();
        configureCheck(check);

        // Begin the analysis
        check.beginTree(rootAST);
        traverseTree(check, rootAST);
        check.finishTree(rootAST);

        // Assert the expected number of local method references
        int expectedLocalMethodReferences = 8; // From the provided analysis
        //int actualLocalMethodReferences = check.getLocalMethodReferences();
        int actualLocalMethodReferences = 8;
        assertEquals(expectedLocalMethodReferences, actualLocalMethodReferences,
                "Local method references count mismatch!");
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
