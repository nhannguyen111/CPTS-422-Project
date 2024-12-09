/*package My422Project.BlackBoxTests;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import java.io.File;
import java.io.IOException;
import com.puppycrawl.tools.checkstyle.DefaultConfiguration;
import com.puppycrawl.tools.checkstyle.DefaultContext;
import com.puppycrawl.tools.checkstyle.JavaParser;
import com.puppycrawl.tools.checkstyle.api.*;
import My422Project.HalsteadLengthCheck;
import My422Project.HalsteadVocabularyCheck;
import My422Project.HalsteadVolumeCheck;
import My422Project.HalsteadDifficultyCheck;
import My422Project.HalsteadEffortCheck;
import My422Project.NumOfCommentsCheck;
import My422Project.NumOfLinesOfCommentsCheck;
import My422Project.NumOfLoopingStatementsCheck;
import My422Project.NumOfOperatorsCheck;
import My422Project.NumOfOperandsCheck;
import My422Project.NumOfExpressionsCheck;
import My422Project.NumOfVariableDeclarationsCheck;
import My422Project.NumOfExternalMethodReferencesCheck;

public class BlackboxTest {

    @Test
    public void testHalsteadMetrics() throws IOException, CheckstyleException {
        // Directory containing black-box test case files
        File testCasesDir = new File("src/test/java/My422Project/BlackBoxTestCases/");
        assertTrue(testCasesDir.exists() && testCasesDir.isDirectory(), "Test cases directory not found!");

        // List all Java test files in the directory
        File[] testFiles = testCasesDir.listFiles((dir, name) -> name.endsWith(".java"));
        assertNotNull(testFiles, "No test files found in the directory!");

        for (File sourceFile : testFiles) {
            System.out.println("Processing file: " + sourceFile.getName());

            FileText fileText = new FileText(sourceFile, "UTF-8");
            FileContents fileContents = new FileContents(fileText);
            DetailAST rootAST = JavaParser.parse(fileContents);

            // Initialize all Halstead Check classes
            HalsteadLengthCheck halsteadLengthCheck = new HalsteadLengthCheck();
            HalsteadVolumeCheck	halsteadVolumeCheck = new HalsteadVolumeCheck();
            HalsteadVocabularyCheck halsteadVocabularyCheck = new HalsteadVocabularyCheck();
            HalsteadDifficultyCheck halsteadDifficultyCheck = new HalsteadDifficultyCheck();
            HalsteadEffortCheck halsteadEffortCheck = new HalsteadEffortCheck();
            NumOfCommentsCheck numOfCommentsCheck = new NumOfCommentsCheck();
            NumOfLinesOfCommentsCheck numOfLinesOfCommentsCheck = new NumOfLinesOfCommentsCheck();
            NumOfLoopingStatementsCheck numOfLoopingStatementsCheck = new NumOfLoopingStatementsCheck();
            NumOfOperatorsCheck numOfOperatorsCheck = new NumOfOperatorsCheck();
            NumOfOperandsCheck numOfOperandsCheck = new NumOfOperandsCheck();
            NumOfExpressionsCheck numOfExpressionsCheck = new NumOfExpressionsCheck();
            NumOfVariableDeclarationsCheck NumOfVariableDeclarationsCheck = new NumOfVariableDeclarationsCheck();
            NumOfExternalMethodReferencesCheck NumOfExternalMethodReferencesCheck = new NumOfExternalMethodReferencesCheck();

            // Configure and execute each Check
            configureAndExecuteCheck(halsteadLengthCheck, rootAST);
            configureAndExecuteCheck(halsteadVolumeCheck, rootAST);
            configureAndExecuteCheck(halsteadVocabularyCheck, rootAST);
            configureAndExecuteCheck(halsteadDifficultyCheck, rootAST);
            configureAndExecuteCheck(halsteadEffortCheck, rootAST);
            configureAndExecuteCheck(numOfCommentsCheck, rootAST);
            configureAndExecuteCheck(numOfLinesOfCommentsCheck, rootAST);
            configureAndExecuteCheck(numOfLoopingStatementsCheck, rootAST);
            configureAndExecuteCheck(numOfOperatorsCheck, rootAST);
            configureAndExecuteCheck(numOfOperandsCheck, rootAST);
            configureAndExecuteCheck(numOfExpressionsCheck, rootAST);
            configureAndExecuteCheck(NumOfVariableDeclarationsCheck, rootAST);
            configureAndExecuteCheck(NumOfExternalMethodReferencesCheck, rootAST);

            // Add assertions for expected results per file if known
            // For example:
            // assertEquals(expectedLength, lengthCheck.getHalsteadLength(), "Halstead Length mismatch for " + sourceFile.getName());
            assertEquals(49, halsteadLengthCheck.getHalsteadLength(), "Halstead Length mismatch.");
            assertEquals(20, halsteadVocabularyCheck.getUniqueOperators().size() + halsteadVocabularyCheck.getUniqueOperands().size(), "Halstead Vocabulary mismatch!");
            
            
            assertEquals(16.875, halsteadDifficulty, 0.001, "Halstead Difficulty mismatch!");
        }

        System.out.println("Blackbox Test Completed.");
    }

    private void configureAndExecuteCheck(AbstractCheck check, DetailAST rootAST) throws CheckstyleException {
        configureCheck(check);
        check.beginTree(rootAST);
        traverseTree(check, rootAST);
        check.finishTree(rootAST);
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


*/