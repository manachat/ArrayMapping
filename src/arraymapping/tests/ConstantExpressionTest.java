package arraymapping.tests;

import arraymapping.src.ConstantExpression;
import arraymapping.src.SYNTAX_ERROR;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

class ConstantExpressionTest {

    @Test
    void parseContent() {
        assertThrows(SYNTAX_ERROR.class, () -> new ConstantExpression("element"));
        assertDoesNotThrow(() -> new ConstantExpression("-800"));
    }

    @Test
    void getNumber() {
        ConstantExpression test = new ConstantExpression("84");
        assertEquals(84, test.getNumber());
        test = new ConstantExpression("-50");
        assertEquals(-50, test.getNumber());
    }

    @Test
    void testToString() {
        ConstantExpression test = new ConstantExpression("-6");
        assertEquals("-6", test.toString());
        test = new ConstantExpression("42");
        assertEquals("42", test.toString());
    }
}