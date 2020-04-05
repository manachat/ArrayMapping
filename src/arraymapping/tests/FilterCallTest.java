package arraymapping.tests;

import arraymapping.src.*;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class FilterCallTest {

    @Test
    void parseCall(){
        FilterCall test = new FilterCall("(28<element)");
        assertEquals(BinaryExpression.class , test.getExpression().getClass());
        assertEquals(28, ((ConstantExpression)((BinaryExpression)test.getExpression()).getLeftOp()).getNumber());
        assertThrows(TYPE_ERROR.class, () -> new FilterCall("(element*6)"));
        assertThrows(SYNTAX_ERROR.class, () -> new FilterCall("element+8"));
        assertThrows(TYPE_ERROR.class, () -> new FilterCall("(element&8)"));
        assertThrows(TYPE_ERROR.class, () -> new FilterCall("42"));
    }

    @Test
    void testToString() {
        FilterCall test = new FilterCall("(28<element)");
        assertEquals("filter{(28<element)}", test.toString());
    }
}