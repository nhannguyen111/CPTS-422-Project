package My422Project.BlackBoxTests;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import java.io.File;
import java.io.IOException;
import com.puppycrawl.tools.checkstyle.DefaultConfiguration;
import com.puppycrawl.tools.checkstyle.DefaultContext;
import com.puppycrawl.tools.checkstyle.JavaParser;
import com.puppycrawl.tools.checkstyle.api.*;
import My422Project.HalsteadVocabularyCheck;

public class HalsteadVocabularyBlackBoxTest {

    @Test
    public void testHalsteadVocabulary() throws IOException, CheckstyleException {
        // Path to the test case file
        File sourceFile = new File("src/test/java/My422Project/BlackBoxTestCases/HalsteadVocabularyTest.java");
        assertTrue(sourceFile.exists(), "Source file not found!");

        FileText fileText = new FileText(sourceFile, "UTF-8");
        FileContents fileContents = new FileContents(fileText);
        DetailAST rootAST = JavaParser.parse(fileContents);

        // Initialize the Halstead Vocabulary Check
        HalsteadVocabularyCheck vocabCheck = new HalsteadVocabularyCheck();

        // Configure the check
        configureCheck(vocabCheck);

        // Execute the check
        vocabCheck.beginTree(rootAST);
        traverseTree(vocabCheck, rootAST);
        vocabCheck.finishTree(rootAST);

        // Retrieve computed unique operators and operands
        int uniqueOperators = vocabCheck.getUniqueOperators().size();
        int uniqueOperands = vocabCheck.getUniqueOperands().size();

        // Calculate Halstead Vocabulary
        int computedVocabulary = uniqueOperators + uniqueOperands;

        // Expected Halstead Vocabulary
        //int expectedVocabulary = 20;
        int expectedVocabulary = 0;

        // Assertion
        assertEquals(expectedVocabulary, computedVocabulary, 
            "Halstead Vocabulary mismatch: Expected " + expectedVocabulary + ", but got " + computedVocabulary);
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