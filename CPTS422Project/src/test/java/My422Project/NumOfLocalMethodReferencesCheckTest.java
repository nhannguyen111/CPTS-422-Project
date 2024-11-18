package My422Project;

import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class NumOfLocalMethodReferencesCheckTest {

    private NumOfLocalMethodReferencesCheck check;

    @BeforeEach
    void setUp() {
        check = new NumOfLocalMethodReferencesCheck();
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
    void testVisitToken_withUnqualifiedLocalMethodCall() {
        DetailAST classDefAST = mock(DetailAST.class);
        when(classDefAST.getType()).thenReturn(TokenTypes.CLASS_DEF);
        DetailAST identAST = mock(DetailAST.class);
        when(identAST.getText()).thenReturn("TestClass");
        when(classDefAST.findFirstToken(TokenTypes.IDENT)).thenReturn(identAST);
        check.visitToken(classDefAST);

        DetailAST methodCallAST = mock(DetailAST.class);
        when(methodCallAST.getType()).thenReturn(TokenTypes.METHOD_CALL);
        when(methodCallAST.findFirstToken(TokenTypes.DOT)).thenReturn(null);

        check.visitToken(methodCallAST);

        assertEquals(1, check.getLocalMethodReferences(), "The total local method reference count should be 1 for an unqualified local method call.");
    }

    @Test
    void testVisitToken_withQualifiedLocalMethodCall() {
        DetailAST classDefAST = mock(DetailAST.class);
        when(classDefAST.getType()).thenReturn(TokenTypes.CLASS_DEF);
        DetailAST identAST = mock(DetailAST.class);
        when(identAST.getText()).thenReturn("TestClass");
        when(classDefAST.findFirstToken(TokenTypes.IDENT)).thenReturn(identAST);
        check.visitToken(classDefAST);

        DetailAST methodCallAST = mock(DetailAST.class);
        when(methodCallAST.getType()).thenReturn(TokenTypes.METHOD_CALL);

        DetailAST dotAST = mock(DetailAST.class);
        when(methodCallAST.findFirstToken(TokenTypes.DOT)).thenReturn(dotAST);

        DetailAST sameClassAST = mock(DetailAST.class);
        when(dotAST.findFirstToken(TokenTypes.IDENT)).thenReturn(sameClassAST);
        when(sameClassAST.getText()).thenReturn("TestClass");

        check.visitToken(methodCallAST);

        assertEquals(1, check.getLocalMethodReferences(), "The total local method reference count should be 1 for a qualified local method call.");
    }

    @Test
    void testVisitToken_withExternalMethodCall() {
        DetailAST classDefAST = mock(DetailAST.class);
        when(classDefAST.getType()).thenReturn(TokenTypes.CLASS_DEF);
        DetailAST identAST = mock(DetailAST.class);
        when(identAST.getText()).thenReturn("TestClass");
        when(classDefAST.findFirstToken(TokenTypes.IDENT)).thenReturn(identAST);
        check.visitToken(classDefAST);

        DetailAST methodCallAST = mock(DetailAST.class);
        when(methodCallAST.getType()).thenReturn(TokenTypes.METHOD_CALL);

        DetailAST dotAST = mock(DetailAST.class);
        when(methodCallAST.findFirstToken(TokenTypes.DOT)).thenReturn(dotAST);

        DetailAST otherClassAST = mock(DetailAST.class);
        when(dotAST.findFirstToken(TokenTypes.IDENT)).thenReturn(otherClassAST);
        when(otherClassAST.getText()).thenReturn("OtherClass");

        check.visitToken(methodCallAST);

        assertEquals(0, check.getLocalMethodReferences(), "The total local method reference count should remain 0 for an external method call.");
    }

    @Test
    void testVisitToken_withDotButNoIdent() {
        DetailAST classDefAST = mock(DetailAST.class);
        when(classDefAST.getType()).thenReturn(TokenTypes.CLASS_DEF);
        DetailAST identAST = mock(DetailAST.class);
        when(identAST.getText()).thenReturn("TestClass");
        when(classDefAST.findFirstToken(TokenTypes.IDENT)).thenReturn(identAST);
        check.visitToken(classDefAST);

        // Create a method call with a DOT token but no IDENT token
        DetailAST methodCallAST = mock(DetailAST.class);
        when(methodCallAST.getType()).thenReturn(TokenTypes.METHOD_CALL);

        DetailAST dotAST = mock(DetailAST.class);
        when(methodCallAST.findFirstToken(TokenTypes.DOT)).thenReturn(dotAST);
        when(dotAST.findFirstToken(TokenTypes.IDENT)).thenReturn(null);

        check.visitToken(methodCallAST);

        assertEquals(0, check.getLocalMethodReferences(), "The total local method reference count should remain 0 when there is a DOT but no IDENT token.");
    }

    @Test
    void testFinishTree() {
        // Simulate visiting tokens
        DetailAST classDefAST = mock(DetailAST.class);
        when(classDefAST.getType()).thenReturn(TokenTypes.CLASS_DEF);
        DetailAST identAST = mock(DetailAST.class);
        when(identAST.getText()).thenReturn("TestClass");
        when(classDefAST.findFirstToken(TokenTypes.IDENT)).thenReturn(identAST);
        check.visitToken(classDefAST);

        DetailAST methodCallAST = mock(DetailAST.class);
        when(methodCallAST.getType()).thenReturn(TokenTypes.METHOD_CALL);
        when(methodCallAST.findFirstToken(TokenTypes.DOT)).thenReturn(null);
        check.visitToken(methodCallAST);

        // Create a spy and call finishTree
        NumOfLocalMethodReferencesCheck spyCheck = spy(check);
        doNothing().when(spyCheck).log(anyInt(), anyString());
        spyCheck.finishTree(null);

        // Verify that log was called with expected arguments
        verify(spyCheck).log(eq(0), contains("Total number of local method references: 1"));

        // Ensure state is reset
        assertEquals(0, spyCheck.getLocalMethodReferences(), "The total local method reference count should be reset to 0 after finishTree.");
        assertEquals("", spyCheck.getCurrentClassName(), "The current class name should be reset to an empty string after finishTree.");
    }
}
