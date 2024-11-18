package My422Project;

import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class NumOfExternalMethodReferencesCheckTest {

    private NumOfExternalMethodReferencesCheck check;

    @BeforeEach
    void setUp() {
        check = new NumOfExternalMethodReferencesCheck();
    }

    @Test
    void testGetDefaultTokens() {
        int[] expectedTokens = new int[]{
            TokenTypes.CLASS_DEF,
            TokenTypes.METHOD_CALL
        };

        assertArrayEquals(expectedTokens, check.getDefaultTokens(), "The default tokens should match the expected values.");
    }

    @Test
    void testGetAcceptableTokens() {
        assertArrayEquals(check.getDefaultTokens(), check.getAcceptableTokens(), "The acceptable tokens should match the default tokens.");
    }

    @Test
    void testGetRequiredTokens() {
        assertArrayEquals(new int[0], check.getRequiredTokens(), "The required tokens array should be empty.");
    }

    @Test
    void testVisitToken_withClassDef() {
        DetailAST classDefAST = mock(DetailAST.class);
        when(classDefAST.getType()).thenReturn(TokenTypes.CLASS_DEF);

        DetailAST identAST = mock(DetailAST.class);
        when(identAST.getText()).thenReturn("TestClass");
        when(classDefAST.findFirstToken(TokenTypes.IDENT)).thenReturn(identAST);

        check.visitToken(classDefAST);

        assertEquals("TestClass", check.getCurrentClassName(), "The current class name should be set to 'TestClass'.");
    }

    @Test
    void testVisitToken_withExternalMethodCall() {
        // Set up the class name to "TestClass"
        DetailAST classDefAST = mock(DetailAST.class);
        when(classDefAST.getType()).thenReturn(TokenTypes.CLASS_DEF);
        DetailAST identAST = mock(DetailAST.class);
        when(identAST.getText()).thenReturn("TestClass");
        when(classDefAST.findFirstToken(TokenTypes.IDENT)).thenReturn(identAST);
        check.visitToken(classDefAST);

        // Create an external method call on "OtherClass.someMethod()"
        DetailAST methodCallAST = mock(DetailAST.class);
        when(methodCallAST.getType()).thenReturn(TokenTypes.METHOD_CALL);

        DetailAST dotAST = mock(DetailAST.class);
        when(methodCallAST.findFirstToken(TokenTypes.DOT)).thenReturn(dotAST);

        DetailAST otherClassAST = mock(DetailAST.class);
        when(dotAST.findFirstToken(TokenTypes.IDENT)).thenReturn(otherClassAST);
        when(otherClassAST.getText()).thenReturn("OtherClass");

        check.visitToken(methodCallAST);

        assertEquals(1, check.getExternalMethodReferences(), "The total external method reference count should be 1.");
    }

    @Test
    void testVisitToken_withInternalMethodCall() {
        // Set up the class name to "TestClass"
        DetailAST classDefAST = mock(DetailAST.class);
        when(classDefAST.getType()).thenReturn(TokenTypes.CLASS_DEF);
        DetailAST identAST = mock(DetailAST.class);
        when(identAST.getText()).thenReturn("TestClass");
        when(classDefAST.findFirstToken(TokenTypes.IDENT)).thenReturn(identAST);
        check.visitToken(classDefAST);

        // Create an internal method call on "TestClass.someMethod()"
        DetailAST methodCallAST = mock(DetailAST.class);
        when(methodCallAST.getType()).thenReturn(TokenTypes.METHOD_CALL);

        DetailAST dotAST = mock(DetailAST.class);
        when(methodCallAST.findFirstToken(TokenTypes.DOT)).thenReturn(dotAST);

        DetailAST sameClassAST = mock(DetailAST.class);
        when(dotAST.findFirstToken(TokenTypes.IDENT)).thenReturn(sameClassAST);
        when(sameClassAST.getText()).thenReturn("TestClass");

        check.visitToken(methodCallAST);

        assertEquals(0, check.getExternalMethodReferences(), "The total external method reference count should remain 0 for internal calls.");
    }
    
    @Test
    void testIsExternalMethodReference_withoutDotToken() {
        // Set up the class name to "TestClass"
        DetailAST classDefAST = mock(DetailAST.class);
        when(classDefAST.getType()).thenReturn(TokenTypes.CLASS_DEF);
        DetailAST identAST = mock(DetailAST.class);
        when(identAST.getText()).thenReturn("TestClass");
        when(classDefAST.findFirstToken(TokenTypes.IDENT)).thenReturn(identAST);
        check.visitToken(classDefAST);

        // Simulate an internal method call without a DOT token (e.g., someMethod())
        DetailAST methodCallAST = mock(DetailAST.class);
        when(methodCallAST.getType()).thenReturn(TokenTypes.METHOD_CALL);
        when(methodCallAST.findFirstToken(TokenTypes.DOT)).thenReturn(null); // No DOT token

        check.visitToken(methodCallAST);

        // Verify that the external method reference count did not increase
        assertEquals(0, check.getExternalMethodReferences(), "The total external method reference count should remain 0 for internal calls without qualification.");
    }
    
    @Test
    void testFinishTree() {
        // Spy on the check instance
        NumOfExternalMethodReferencesCheck spyCheck = spy(check);

        // Simulate visiting an external method reference
        DetailAST classDefAST = mock(DetailAST.class);
        when(classDefAST.getType()).thenReturn(TokenTypes.CLASS_DEF);
        DetailAST identAST = mock(DetailAST.class);
        when(identAST.getText()).thenReturn("TestClass");
        when(classDefAST.findFirstToken(TokenTypes.IDENT)).thenReturn(identAST);
        spyCheck.visitToken(classDefAST);

        DetailAST methodCallAST = mock(DetailAST.class);
        when(methodCallAST.getType()).thenReturn(TokenTypes.METHOD_CALL);

        DetailAST dotAST = mock(DetailAST.class);
        when(methodCallAST.findFirstToken(TokenTypes.DOT)).thenReturn(dotAST);

        DetailAST otherClassAST = mock(DetailAST.class);
        when(dotAST.findFirstToken(TokenTypes.IDENT)).thenReturn(otherClassAST);
        when(otherClassAST.getText()).thenReturn("OtherClass");

        spyCheck.visitToken(methodCallAST);

        // Use Mockito to mock the `log` method to do nothing
        doNothing().when(spyCheck).log(anyInt(), anyString());

        // Call finishTree
        spyCheck.finishTree(null);

        // Verify that `log` was called with the expected message
        verify(spyCheck).log(eq(0), contains("Total number of external method references: 1"));

        // Ensure that externalMethodReferences was reset
        assertEquals(0, spyCheck.getExternalMethodReferences(), "The total external method reference count should be reset to 0 after finishTree.");
        assertEquals("", spyCheck.getCurrentClassName(), "The current class name should be reset to an empty string after finishTree.");
    }
}
