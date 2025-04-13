package dima.model.service.util;

import by.dima.model.data.services.util.GetSecondArgFromArgsUtil;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GetSecondArgFromArgsUtilTest {

    @Test
    void getSecondArgUtilTest() {

        String[] niceText = new String[]{"update", "me"};
        String[] emptyText = new String[]{""};
        String[] badText = new String[]{"update", "me", "please"};


        assertEquals("me", GetSecondArgFromArgsUtil.getSecondArg(niceText));
        assertEquals("", GetSecondArgFromArgsUtil.getSecondArg(emptyText));
        assertEquals("", GetSecondArgFromArgsUtil.getSecondArg(badText));

    }


}