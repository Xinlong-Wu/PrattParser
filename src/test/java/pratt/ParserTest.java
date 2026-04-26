package pratt;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import static org.junit.jupiter.api.Assertions.*;

class ParserTest {

    // ---- 单个原子 ----

    @Test
    @DisplayName("单个数字")
    void singleNumber() {
        assertEquals("5", parse("5"));
    }

    @Test
    @DisplayName("单个变量")
    void singleVariable() {
        assertEquals("a", parse("a"));
    }

    // ---- 优先级 ----

    @Test
    @DisplayName("* 优先级高于 +")
    void multiplyBeforePlus() {
        assertEquals("+ 1 * 2 3", parse("1 + 2 * 3"));
    }

    @Test
    @DisplayName("* 优先级高于 -")
    void multiplyBeforeMinus() {
        assertEquals("- 1 * 2 3", parse("1 - 2 * 3"));
    }

    @Test
    @DisplayName("/ 优先级高于 +")
    void divideBeforePlus() {
        assertEquals("+ a / b c", parse("a + b / c"));
    }

    @Test
    @DisplayName("/ 优先级高于 -")
    void divideBeforeMinus() {
        assertEquals("- a / b c", parse("a - b / c"));
    }

    @Test
    @DisplayName("* 在左边时优先结合")
    void multiplyLeft() {
        assertEquals("+ * a b c", parse("a * b + c"));
    }

    // ---- 左结合 ----

    @Test
    @DisplayName("+ 左结合")
    void plusLeftAssociative() {
        assertEquals("+ + 1 2 3", parse("1 + 2 + 3"));
    }

    @Test
    @DisplayName("- 左结合")
    void minusLeftAssociative() {
        assertEquals("- - 1 2 3", parse("1 - 2 - 3"));
    }

    @Test
    @DisplayName("* 左结合")
    void starLeftAssociative() {
        assertEquals("* * 1 2 3", parse("1 * 2 * 3"));
    }

    @Test
    @DisplayName("/ 左结合")
    void slashLeftAssociative() {
        assertEquals("/ / 1 2 3", parse("1 / 2 / 3"));
    }

    // ---- 混合 ----

    @Test
    @DisplayName("混合优先级与结合")
    void mixed() {
        assertEquals("+ + a b * c d", parse("a + b + c * d"));
    }

    @Test
    @DisplayName("数字边界 0 和 10")
    void numberBoundary() {
        assertEquals("0", parse("0"));
        assertEquals("10", parse("10"));
        assertEquals("+ 10 0", parse("10 + 0"));
    }

    @Test
    @DisplayName("大写变量")
    void uppercaseVariable() {
        assertEquals("+ A z", parse("A + z"));
    }

    @Test
    @DisplayName("无空格")
    void noSpaces() {
        assertEquals("+ 1 * 2 3", parse("1+2*3"));
    }

    // ---- 错误 ----

    @Test
    @DisplayName("空输入抛异常")
    void emptyInput() {
        assertThrows(RuntimeException.class, () -> parse(""));
    }

    @Test
    @DisplayName("操作符开头抛异常")
    void operatorAtStart() {
        assertThrows(RuntimeException.class, () -> parse("+ 1 2"));
    }

    @Test
    @DisplayName("操作符结尾抛异常")
    void operatorAtEnd() {
        assertThrows(RuntimeException.class, () -> parse("1 +"));
    }

    @Test
    @DisplayName("连续操作符抛异常")
    void consecutiveOperators() {
        assertThrows(RuntimeException.class, () -> parse("1 * + 2"));
    }

    // ---- helper ----

    private String parse(String input) {
        return new Parser(new Lexer(input).tokenize()).parse().printPrefix();
    }
}
