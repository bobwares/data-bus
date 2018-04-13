package com.bobwares.databus.common.renderer.pdf;

import com.google.common.collect.Maps;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import java.util.Map;

import static com.google.common.collect.ImmutableMap.copyOf;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class ColumnTest {

    Column columnWithOutAlignment;
    Column columnWithDefinedAlignment;

    @Before
    public void setUp() throws Exception {
        Map<String, Object> field = Maps.newHashMap();
        field.put("type","number");
        columnWithOutAlignment = new Column(copyOf(field),0);
        field.put("alignment", "center");
        field.put("group", "group");
        columnWithDefinedAlignment = new Column(copyOf(field),1);
    }

    @Ignore
    @Test
    public void testGetWithoutAlignment() throws Exception {
        assertEquals(Alignment.RIGHT, columnWithOutAlignment.getAlignment());
    }
    
    @Test
    public void testGetWithAlignment() throws Exception {
        assertEquals(Alignment.CENTER, columnWithDefinedAlignment.getAlignment());
    }

    @Test
    public void testIndex() throws Exception {
        assertEquals(0, columnWithOutAlignment.getIndex());
        assertEquals(1, columnWithDefinedAlignment.getIndex());
    }

    @Test
    public void getGroupsReturnsNull() {
        assertNull(columnWithOutAlignment.getGroup());
    }

    @Test
    public void getGroupsReturnsString() {
        assertEquals("group", columnWithDefinedAlignment.getGroup());
    }
}