package arraymapping.tests;

import arraymapping.src.Element;
import arraymapping.src.SYNTAX_ERROR;
import arraymapping.util.Utilities;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

class ElementTest {

    @Test
    void parseContent() {
        assertThrows(SYNTAX_ERROR.class, () -> new Element("123"));
        assertThrows(SYNTAX_ERROR.class, () -> new Element("elemen"));
        assertThrows(SYNTAX_ERROR.class, () -> new Element("(element)"));
        assertDoesNotThrow(() -> new Element("element"));
        assertEquals(Utilities.ReturnType.ARITHMETIC, new Element("element").getReturnType());
    }

    @Test
    void testToString() {
        Element testElement = new Element("element");
        assertEquals("element", testElement.toString());
    }

}