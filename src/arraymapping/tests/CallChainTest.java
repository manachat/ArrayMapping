package arraymapping.tests;

import arraymapping.src.CallChain;
import arraymapping.src.FilterCall;
import arraymapping.src.MapCall;
import arraymapping.src.SYNTAX_ERROR;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CallChainTest {

    @Test
    void testConstructor(){
        CallChain test = new CallChain("filter{(element<0)}%>%map{(element*element)}");
        assertEquals(FilterCall.class, test.getCallChain().get(0).getClass());
        assertEquals(MapCall.class, test.getCallChain().get(1).getClass());
        assertThrows(SYNTAX_ERROR.class, () -> new CallChain("map{element}%>%"));
        assertThrows(SYNTAX_ERROR.class, () -> new CallChain("map{element}%>filter{(1<2)}"));
        assertThrows(SYNTAX_ERROR.class, () -> new CallChain("map{elemnt}"));
    }

    @Test
    void testToString() {
        CallChain test = new CallChain("map{(element+10)}%>%filter{((element>3)&(element<20))}");
        assertEquals("map{(element+10)}%>%filter{((element>3)&(element<20))}", test.toString());
    }

    @Test
    void refactorTest(){
        CallChain test = new CallChain("filter{(element>10)}%>%filter{(element<20)}");
        test.refactor();
        assertEquals("filter{((element>10)&(element<20))}%>%map{element}", test.toString());
        test = new CallChain("map{(element+10)}%>%filter{(element>10)}%>%map{(element*element)}");
        test.refactor();
        assertEquals("filter{((element+10)>10)}%>%map{((element+10)*(element+10))}", test.toString());
        test = new CallChain("map{(element*element)}");
        test.refactor();
        assertEquals("filter{(0<1)}%>%map{(element*element)}", test.toString());
        test = new CallChain("map{(element-4)}%>%filter{((element+1)>0)}%>%map{(element*4)}%>%filter{((element*element)<100)}");
        test.refactor();
        assertEquals("filter{((((element-4)+1)>0)&((((element-4)*4)*((element-4)*4))<100))}%>%map{((element-4)*4)}",
                test.toString());
        test = new CallChain("map{(element+8)}%>%filter{(element>0)}%>%map{42}");
        test.refactor();
        assertEquals("filter{((element+8)>0)}%>%map{42}", test.toString());
        test = new CallChain("map{42}%>%map{element}");
        test.refactor();
        assertEquals("filter{(0<1)}%>%map{42}", test.toString());
    }
}