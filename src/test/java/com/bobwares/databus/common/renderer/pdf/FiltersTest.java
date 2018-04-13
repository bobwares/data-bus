package com.bobwares.databus.common.renderer.pdf;

import static org.junit.Assert.assertEquals;

import java.util.Date;

import javax.inject.Inject;
import javax.inject.Named;

import org.junit.Test;

import com.bobwares.core.security.Security;
import com.bobwares.core.security.authentication.AuthenticationService;
import com.bobwares.test.AbstractDatabaseTest;

public class FiltersTest extends AbstractDatabaseTest {

	@Inject AuthenticationService authenticationService;
	@Inject @Named("security") Security security;

    long t1 = -360052176392l; // 8/4/1958 12:30 PM
    long t2 = -360048581097l; // 8/4/1958 1:30 PM


    @Test
    public void dateString() throws Exception {
        String dateStr = Filters.filter("date", String.valueOf(t1));
        assertEquals("8/4/1958", dateStr);
    }

    @Test
    public void dateTimeString() throws Exception {
        String dateStr = Filters.filter("datetime", String.valueOf(t1));
        assertEquals("8/4/1958 12:30 PM", dateStr);
        dateStr = Filters.filter("datetime", String.valueOf(t2));
        assertEquals("8/4/1958 1:30 PM", dateStr);
    }

    @Test
    public void objectToDateString() throws Exception {
        String dateStr = Filters.filter("date", new Date(t1));
        assertEquals("8/4/1958", dateStr);
    }

    @Test
    public void objectToDateTimeString() throws Exception {
        String dateStr = Filters.filter("datetime", new Date(t1));
        assertEquals("8/4/1958 12:30 PM", dateStr);
        dateStr = Filters.filter("datetime", new Date(t2));
        assertEquals("8/4/1958 1:30 PM", dateStr);
    }

    @Test
    public void nullNumberToEmptyString() throws Exception {
        Object str = Filters.filter("number", null);
        assertEquals("", str);
    }

    @Test
    public void dollars() {
        String input = "1234567";
        String expected = "1,234,567";
        assertEquals(expected, Filters.filter("dollars", input));
        input = "-1234567";
        expected = "(1,234,567)";
        assertEquals(expected, Filters.filter("dollars", input));
        input = "12345.67";
        expected = "12,345.67";
        assertEquals(expected, Filters.filter("dollars", input));
        input = "-12345.67";
        expected = "(12,345.67)";
        assertEquals(expected, Filters.filter("dollars", input));
        input = "345.67";
        expected = "345.67";
        assertEquals(expected, Filters.filter("dollars", input));
        input = "567";
        expected = "567";
        assertEquals(expected, Filters.filter("dollars", input));
    }

    @Test
    public void monthYear() {
        String input = "195808";
        String expected = "Aug 1958";
        assertEquals(expected, Filters.filter("monthyear", input));
    }

    @Test
    public void monthYearBadValue() {
        String input = "hfkjghkfhk";
        String expected = "hfkjghkfhk expected yyyymm";
        assertEquals(expected, Filters.filter("monthyear", input));
    }

    @Test
    public void booleanTruthy() {
        String input = "1";
        String expected = "Yes";
        assertEquals(expected, Filters.filter("boolean", input));
        input = "true";
        expected = "Yes";
        assertEquals(expected, Filters.filter("boolean", input));
        input = "TRUE";
        expected = "Yes";
        assertEquals(expected, Filters.filter("boolean", input));
    }

    @Test
    public void booleanFalsey() {
        String input = "0";
        String expected = "No";
        assertEquals(expected, Filters.filter("boolean", input));
        input = "false";
        expected = "No";
        assertEquals(expected, Filters.filter("boolean", input));
        input = "FALSE";
        expected = "No";
        assertEquals(expected, Filters.filter("boolean", input));
    }

    @Test
    public void booleanNullEmpty() {
        String input = "";
        String expected = "";
        assertEquals(expected, Filters.filter("boolean", input));
        expected = "";
        assertEquals(expected, Filters.filter("boolean", null));
    }

}