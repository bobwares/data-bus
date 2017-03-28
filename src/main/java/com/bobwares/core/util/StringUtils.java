package com.bobwares.core.util;

import com.google.common.base.Strings;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.security.SecureRandom;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class StringUtils {

    protected static final String NON_ACCENTED_CHARS =
            "AaEeIiOoUu"	// grave \
                    + "AaEeIiOoUuYy"  // acute /
                    + "AaEeIiOoUuYy"  // circumflex ^
                    + "AaOoNn"		// tilde ~
                    + "AaEeIiOoUuYy"  // umlaut ..
                    + "Aa"			// ring o
                    + "Cc"			// cedilla c
                    + "OoUu"		  // double acute
            ;

    protected static final String ACCENTED_CHARS =
            "\u00C0\u00E0\u00C8\u00E8\u00CC\u00EC\u00D2\u00F2\u00D9\u00F9"
                    + "\u00C1\u00E1\u00C9\u00E9\u00CD\u00ED\u00D3\u00F3\u00DA\u00FA\u00DD\u00FD"
                    + "\u00C2\u00E2\u00CA\u00EA\u00CE\u00EE\u00D4\u00F4\u00DB\u00FB\u0176\u0177"
                    + "\u00C3\u00E3\u00D5\u00F5\u00D1\u00F1"
                    + "\u00C4\u00E4\u00CB\u00EB\u00CF\u00EF\u00D6\u00F6\u00DC\u00FC\u0178\u00FF"
                    + "\u00C5\u00E5"
                    + "\u00C7\u00E7"
                    + "\u0150\u0151\u0170\u0171"
            ;

    public static final String CHARS_NUMERIC = "0123456789";
    public static final String CHARS_SPECIAL = "~!@#$%^&*()-=+?";
    public static final String CHARS_ALPHA_UPPER = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    public static final String CHARS_ALPHA_LOWER = "abcdefghijklmnopqrstuvwxyz";
    public static final String CHARS_ALPHA = CHARS_ALPHA_UPPER + CHARS_ALPHA_LOWER;
    public static final String CHARS_ALPHA_NUMERIC = CHARS_NUMERIC + CHARS_ALPHA;
    public static final String CHARS_ALPHA_NUMERIC_SPECIAL = CHARS_ALPHA_NUMERIC + CHARS_SPECIAL;

    public static final String CHARS_ALPHA_UPPER_NO_I_OR_O = "ABCDEFGHJKMNPQRSTUVWXYZ";
    public static final String CHARS_ALPHA_LOWER_NO_I_OR_O = "abcdefghjkmnpqrstuvwxyz";
    public static final String CHARS_NUMERIC_NO_1_OR_0 = "23456789";
    public static final String CHARS_ALPHA_NUMERIC_NO_I_OR_O_OR_ZERO_OR_ONE = CHARS_NUMERIC_NO_1_OR_0 + CHARS_ALPHA_LOWER_NO_I_OR_O + CHARS_ALPHA_UPPER_NO_I_OR_O;

    protected static final Random RANDOM = new SecureRandom();

    public static final Pattern PATTERN_BYTE_SPEC = Pattern.compile("([\\d.]+)([GMK]B)", Pattern.CASE_INSENSITIVE);


    /**
     * @param s
     * @return
     */
    public static String trim(String s) {
        if (s != null) {

            int len = s.length();
            int start = 0;
            while (start < len) {
                char c = s.charAt(start);
                if (Character.isWhitespace(c) || Character.isSpaceChar(c)) start++;
                else break;
            }

            int end = len;
            while (end > 0) {
                char c = s.charAt(end - 1);
                if (Character.isWhitespace(c) || Character.isSpaceChar(c)) end--;
                else break;
            }

            if (start >= end) {
                s = null;
            }
            else if (start > 0 || end < len) {
                s = s.substring(start, end);
            }
            //else return the string unchanged
        }
        return s;
    }

    public static String trim(String s, int maxLength) {
        String result = null;
        if (s != null && maxLength > 0) {
            s = trim(s);	//trim off the leading and traiing spaces first
            if (s != null && s.length() > maxLength) s = trim(s.substring(0, maxLength));	//too large, so truncate and trim again to avoid trailing spaces
            result = s;
        }
        return result;
    }

    /**
     * Strip all non digit characters from the string
     * @param s
     * @return
     */
    public static String stripNonDigits(String s) {
        if (s != null) {
            StringBuilder sb = new StringBuilder(16);
            int n = s.length();
            for (int i = 0; i < n; i++) {
                char c = s.charAt(i);
                if (Character.isDigit(c) == true) {
                    sb.append(c);
                }
            }
            s = sb.toString();
        }
        return s;
    }

    /**
     * Strip all non digit characters from the string except for decimal points and positive/negative signs
     * @param s
     * @return
     */
    public static String stripNonDigitsDecimals(String s) {
        if (s != null) {
            StringBuilder sb = new StringBuilder(16);
            int n = s.length();
            for (int i = 0; i < n; i++) {
                char c = s.charAt(i);
                if (Character.isDigit(c) == true || c == '.' || c == '-' || c == '+') {
                    sb.append(c);
                }
            }
            s = sb.toString();
        }
        return s;
    }

    /**
     * Replaces common name punctuation characters (periods, commas, spaces) with a single space.
     * Dashes and ticks are not removed, but any spaces around them are.
     *
     * Ex:
     * 		"David St. Hubbins Jr."	-> "David St Hubbins Jr"
     * 		"David St.Hubbins Jr."	-> "David St Hubbins Jr"
     * 		"Baba O' Riley"			-> "Baba O'Riley"
     * 		"Baba O 'Riley"			-> "Baba O'Riley"
     * @param name
     * @return
     */
    public static String stripNamePunctuation(String name) {
        String result = null;

        if (name != null) {
            name = name.trim();
            if (name.length() > 0) {

                StringBuilder sb = new StringBuilder(64);
                StringTokenizer st = new StringTokenizer(name, " .,");
                while (st.hasMoreTokens()) {
                    String t = st.nextToken();

                    //if the first char of the next token is a letter or digit
                    //and the last char of the buffer is a letter or digit
                    //then add a space in between them
                    if (Character.isLetterOrDigit(t.charAt(0)) && sb.length() > 0 && Character.isLetterOrDigit(sb.charAt(sb.length() - 1))) {
                        sb.append(" ");
                    }

                    sb.append(t);
                }

                result = sb.toString();
                if (result.length() == 0) result = null;
            }
        }

        return result;
    }

    /**
     * Replaces the first 5 numbers the given SSN with *****
     *
     * @param ssn
     * @return
     */
    public static String maskSSN(String ssn) {
        return mask(ssn, '*', 5);
    }

    public static String mask(String clear) {
        return mask(clear, '*', Integer.MAX_VALUE);
    }

    public static String mask(String clear, int maskCount) {
        return mask(clear, '*', maskCount);
    }

    public static String mask(String clear, char maskChar, int maskCount) {
        if (clear == null) return null;

        int clearLen = clear.length();
        if (clearLen == 0) return "";

        String masked = Strings.repeat(new String(new char[]{maskChar}), Math.min(maskCount, clearLen));
        if (maskCount < clearLen) masked += clear.substring(maskCount);
        return masked.substring(0, clearLen);
    }

    public static String getStringValue(String s, String strDefault) {
        return (s != null) ? s : strDefault;
    }

    public static int getIntValue(String s, int intDefault) {
        return (s != null) ? Integer.parseInt(s) : intDefault;
    }

    public static Integer parseInteger(String s, Integer defaultValue) {
        Number number = parseNumber(s, defaultValue);
        return number != null ? Integer.valueOf(number.intValue()) : defaultValue;
    }

    public static Long parseLong(String s, Long defaultValue) {
        Number number = parseNumber(s, defaultValue);
        return number != null ? Long.valueOf(number.longValue()) : defaultValue;
    }

    public static Double parseDouble(String s, Double defaultValue) {
        Number number = parseNumber(s, defaultValue);
        return number != null ? Double.valueOf(number.doubleValue()) : defaultValue;
    }

    public static Number parseNumber(String s, Number defaultValue) {
        s = trim(s);
        if (s == null) return defaultValue;

        char firstChar = s.charAt(0);
        if (firstChar == '+') {
            //skip the leading plus sign:	+1 -> 1
            s = s.substring(1);
        }
        else if (firstChar == '(') {
            //change the parens surrounding the value into a leading minus sign: (1) -> -1
            s = "-" + s.substring(1, s.length() - 1);
        }

        try {
            return NumberFormat.getNumberInstance().parse(s);
        }
        catch (ParseException e) {
            return defaultValue;
        }
    }

    public static Number parseByteSpec(String byteSpec) {
        if (byteSpec == null) return null;

        Matcher matcher = PATTERN_BYTE_SPEC.matcher(byteSpec);
        if (!matcher.find()) {
            //no byte units specified so treat the number as bytes
            return parseNumber(byteSpec, null);
        }

        Map<String, Long> unitMap = new HashMap<>(3);
        unitMap.put("GB", 1024L * 1024L * 1024L);
        unitMap.put("MB", 1024L * 1024L);
        unitMap.put("KB", 1024L);

        Double bytes = Double.valueOf(matcher.group(1)) * unitMap.get(matcher.group(2).toUpperCase());
        return Long.valueOf(bytes.longValue());
    }


    public static boolean isIn(String s, String... args) {
        boolean result = false;
        if (s != null && args != null) {
            for (int i = 0; i < args.length; i++) {
                if (s.equalsIgnoreCase(args[i])) {
                    result = true;
                    break;
                }
            }
        }
        return result;
    }

    public static boolean isIn(String s, List<String> testValues) {
        return isIn(s, (Collection<String>)testValues);
    }

    /**
     * @param s
     * @param testValues
     * @return
     */
    public static boolean isIn(String s, Collection<String> testValues) {
        boolean result = false;
        if (s != null && testValues != null) {
            for (String t : testValues) {
                if (s.equalsIgnoreCase(t)) {
                    result = true;
                    break;
                }
            }
        }
        return result;
    }

    /**
     * Replaces accented charaters with non-accented characters
     *
     * @param s
     * @return
     */
    public static String replaceAccents2(String s) {
        String result = null;
        if (s != null) {
            StringBuilder sb = new StringBuilder();
            int n = s.length();
            for (int i = 0; i < n; i++) {
                char c = s.charAt(i);
                int pos = ACCENTED_CHARS.indexOf(c);
                if (pos > -1) c = NON_ACCENTED_CHARS.charAt(pos);
                sb.append(c);
            }
            result = sb.toString();
        }
        return result;
    }

    public static boolean isDifferent(String prev, String curr) {
        boolean result = false;
        if ((prev == null && curr != null) || (prev != null && curr == null)) result = true;
        else if (prev != null && curr != null && !curr.equalsIgnoreCase(prev)) result = true;
        return result;
    }

    public static String convertDatabaseToCamelCase(String database, boolean capFirstChar) {
        String result = null;

        if (database != null) {
            int len = database.length();
            StringBuilder sb = new StringBuilder(len);

            // create a java friendly field name
            if ( database.indexOf( '_' ) >= 0 || database.indexOf(' ') >= 0) {
                sb.append(database.toLowerCase());

                // the column names contains underscores
                // strip them and use the position as a word indicater
                for (int j = len - 1; j >= 0; j--) {
                    char c = sb.charAt(j);
                    if (c  == '_' || c == ' '){
                        sb.deleteCharAt(j);
                        len--;
                        if (j < len) sb.setCharAt(j, Character.toUpperCase(sb.charAt(j)));
                    }
                }
            }
            else {
                // no underscores in the database name
                // if the name is all upper or lower, lower case it
                // if the name contains mixed case do nothing
                boolean mixed = false;
                for (int j = 0; j < len; j++) {
                    char c = database.charAt(j);
                    if (!Character.isDigit(c)) {
                        if (Character.isUpperCase(database.charAt(0)) && !Character.isUpperCase(c)){
                            mixed = true;
                            break;
                        }
                    }
                }
                if (mixed) {
                    sb.append(database);
                    sb.setCharAt(0, Character.toLowerCase(sb.charAt(0)));
                }
                else {
                    sb.append(database.toLowerCase());
                }
            }

            if (capFirstChar) sb.setCharAt(0, Character.toUpperCase(sb.charAt(0)));

            result = sb.toString();
        }

        return result;
    }

    public static String convertCamelCaseToDatabase(String camelCase) {
        String result = null;

        if (camelCase != null) {
            int len = camelCase.length();

            //change ProgramActivityId into PROGRAM_ACTIVITY_ID
            StringBuilder sb = new StringBuilder(len + 10);
            for (int i = 0; i < len; i++) {
                char c = camelCase.charAt(i);
                if (i > 0 && i < (len - 1) && Character.isUpperCase(c)) {
                    sb.append("_").append(c);
                }
                else {
                    sb.append(Character.toUpperCase(c));
                }
            }

            result = sb.toString();
        }

        return result;
    }

    public static String addPrefix(String s, String prefix) {
        if (prefix != null && s != null && !s.startsWith(prefix)) s = prefix + s;
        return s;
    }

    public static String removePrefix(String s, String prefix) {
        if (prefix != null && s != null && s.startsWith(prefix)) s = s.substring(prefix.length());
        return s;
    }

    public static String addSuffix(String s, String suffix) {
        if (suffix != null && s != null && !s.endsWith(suffix)) s = s + suffix;
        return s;
    }

    public static String removeSuffix(String s, String suffix) {
        if (suffix != null && s != null && s.endsWith(suffix)) s = s.substring(s.length() - suffix.length());
        return s;
    }


    // *******************************************************************
    // Random String Methods
    // *******************************************************************

    public static String generateRandomStringAlphaNumericSpecial(int len) {
        return generateRandomString(len, CHARS_ALPHA_NUMERIC_SPECIAL);
    }

    public static String generateRandomStringAlpha(int len) {
        return generateRandomString(len, CHARS_ALPHA);
    }

    public static String generateRandomStringAlphaNumeric(int len) {
        return generateRandomString(len, CHARS_ALPHA_NUMERIC);
    }

    public static String generateRandomString(int len, String chars) {
        String result = null;
        if (chars != null) {
            int charsLen = chars.length();
            StringBuilder sb = new StringBuilder(len);
            for (int i = 0; i < len; i++) sb.append(chars.charAt(RANDOM.nextInt(charsLen)));
            result = sb.toString();
        }
        return result;
    }


    // *******************************************************************
    // Namespace Methods
    // *******************************************************************

    public static String replaceNamespaceName(String name, String s) {
        return makeNamespaceString(name, getNamespaceIdentifier(s));
    }

    public static String replaceNamespaceIdentifier(String id, String s) {
        return makeNamespaceString(getNamespaceName(s), id);
    }

    public static String makeNamespaceString(String name, String id) {
        StringBuilder sb = new StringBuilder();
        if (name != null) {
            sb.append(name);
            if (!name.endsWith(":")) sb.append(":");
        }
        if (id != null) sb.append(id);
        return sb.toString();
    }

    public static String getNamespaceName(String s) {
        String result = null;
        if (s != null) {
            int i = s.indexOf(":");
            if (i > 0) result = s.substring(0, i);
        }
        return result;
    }

    public static String getNamespaceIdentifier(String s) {
        String result = null;
        if (s != null) {
            int i = s.indexOf(":");
            result = (i >= 0) ? s.substring(i + 1) : s;
            if (result != null) result = trim(result);
        }
        return result;
    }


    // *******************************************************************
    // Delimited Data Methods
    // *******************************************************************

    public static String quoteString(String data) {
        char[] needQuoteChars = { ',' };
        return quoteString(data, needQuoteChars, '"');
    }

    public static String quoteString(String data, char delimiter) {
        char[] needQuoteChars = { delimiter };
        return quoteString(data, needQuoteChars, '"');
    }

    public static String quoteString(String data, char delimiter, char quote) {
        char[] needQuoteChars = { delimiter };
        return quoteString(data, needQuoteChars, quote);
    }

    public static String quoteString(String data, char[] needQuoteChars, char quote) {
        if (data == null) data = "";
        StringBuilder sb = new StringBuilder(data.length() + 10);

        boolean needQuote = false;

        //loop throught the column value character by character to see if it needs quotes
        int dataLen = data.length();
        for (int i = 0; i < dataLen; i++) {
            char c = data.charAt(i);

            //if the string contains any of these then it needs to be quoted
            if (!needQuote && needQuoteChars != null) {
                for (int j = 0; j < needQuoteChars.length; j++) {
                    if (c == needQuoteChars[j]) {
                        needQuote = true;
                        break;
                    }
                }
            }

            //if the current char is the quote char, then it needs to be escaped and the string needs to be quoted
            if (c == quote) {
                sb.append(quote);
                needQuote = true;
            }

            sb.append(c);
        }

        //if the value needs to be quoted add quotes to the beginning and ending of a column value
        if (needQuote) sb.insert(0, quote).append(quote);

        return sb.toString();
    }

    public static String formatDelimitedData(String... data) {
        return formatDelimitedData(Arrays.asList(data), ',', '"');
    }

    public static String formatDelimitedData(char delimiter, String... data) {
        return formatDelimitedData(Arrays.asList(data), delimiter, '"');
    }

    public static String formatDelimitedData(char delimiter, char quote, String... data) {
        return formatDelimitedData(Arrays.asList(data), delimiter, quote);
    }

    public static String formatDelimitedData(Collection<String> list) {
        return formatDelimitedData(list, ',', '"');
    }

    public static String formatDelimitedData(Collection<String> list, char delimiter) {
        return formatDelimitedData(list, delimiter, '"');
    }

    public static String formatDelimitedData(Collection<String> list, char delimiter, char quote) {
        StringBuilder sb = new StringBuilder(1024);
        char[] needQuoteChars = { delimiter, '\n', '\r' };

        for (String s : list) {
            sb.append(quoteString(s, needQuoteChars, quote)).append(delimiter);
        }
        if (sb.length() > 0) sb.setLength(sb.length() - 1);	//trim trailing delimiter

        return sb.toString();
    }

    public static List<String> parseDelimitedData(String data) {
        return parseDelimitedData(data, ',', '"');
    }

    public static List<String> parseDelimitedData(String data, char delimiter) {
        return parseDelimitedData(data, delimiter, '"');
    }

    public static List<String> parseDelimitedData(String data, char delimiter, char quoteChar) {
        List<String> result = null;
        if (data != null) {
            try {
                result = parseDelimitedData(new StringReader(data), delimiter, quoteChar);
            }
            catch (IOException ignored) {}
        }
        return result;
    }

    public static List<String> parseDelimitedData(Reader reader) throws IOException {
        return parseDelimitedData(reader, ',', '"');
    }

    public static List<String> parseDelimitedData(Reader reader, char delimiter) throws IOException {
        return parseDelimitedData(reader, delimiter, '"');
    }

    public static List<String> parseDelimitedData(Reader reader, char delimiter, char quoteChar) throws IOException {
        List<String> fields = null;

        if (reader != null) {
            fields = new ArrayList<String>();

            final int UNQUOTED = 0;
            final int QUOTED = 1;
            final int QUOTEDPLUS = 2;

            boolean sawDelimiter = false;
            int state = UNQUOTED;
            StringBuilder fieldValue = new StringBuilder(256);

            boolean endOfLine = false;
            while (!endOfLine) {
                int c = reader.read();
                if (c < 0) {
                    endOfLine = true;
                    if (sawDelimiter || fieldValue.length() > 0) {
                        fields.add(fieldValue.toString());
                        fieldValue.setLength(0);
                        sawDelimiter = false;
                    }
                    else if (fields.isEmpty()) {
                        //return null at end of file
                        fields = null;
                    }
                    break;
                }

                if (state == QUOTEDPLUS) {
                    if (c == quoteChar) {
                        fieldValue.append(quoteChar);
                        state = QUOTED;
                        continue;
                    }
                    else {
                        state = UNQUOTED;
                    }
                }

                if (state == QUOTED) {
                    if (c == quoteChar) {
                        state = QUOTEDPLUS;
                        continue;
                    }
                    else {
                        fieldValue.append((char)c);
                        continue;
                    }
                }

                if (state == UNQUOTED) {
                    switch (c) {
                        case '\r':
                            //skip over these
                            break;

                        case '\n':
                            if (sawDelimiter || fieldValue.length() > 0) {
                                fields.add(fieldValue.toString());
                                fieldValue.setLength(0);
                                sawDelimiter = false;
                            }
                            if (!fields.isEmpty()) endOfLine = true;
                            break;

                        default:
                            if (c == quoteChar) {
                                state = QUOTED;
                            }
                            else if (c == delimiter) {
                                fields.add(fieldValue.toString());
                                fieldValue.setLength(0);
                                sawDelimiter = true;
                            }
                            else {
                                fieldValue.append((char)c);
                            }
                            break;
                    }
                }
            }
        }

        return fields;
    }

}
