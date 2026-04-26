package pratt;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class LexerTest {

    @Test
    @DisplayName("单个数字")
    void singleNumber() {
        List<Token> tokens = new Lexer("5").tokenize();
        assertEquals(2, tokens.size());
        assertToken(Token.TokenType.NUMBER, "5", tokens.get(0));
        assertToken(Token.TokenType.EOF, null, tokens.get(1));
    }

    @Test
    @DisplayName("数字边界 0 和 10")
    void numberBoundary() {
        List<Token> t0 = new Lexer("0").tokenize();
        assertToken(Token.TokenType.NUMBER, "0", t0.get(0));

        List<Token> t10 = new Lexer("10").tokenize();
        assertToken(Token.TokenType.NUMBER, "10", t10.get(0));
    }

    @Test
    @DisplayName("单个变量")
    void singleVariable() {
        List<Token> tokens = new Lexer("a").tokenize();
        assertToken(Token.TokenType.VARIABLE, "a", tokens.get(0));
    }

    @Test
    @DisplayName("大写变量")
    void uppercaseVariable() {
        List<Token> tokens = new Lexer("Z").tokenize();
        assertToken(Token.TokenType.VARIABLE, "Z", tokens.get(0));
    }

    @Test
    @DisplayName("四个操作符")
    void operators() {
        List<Token> tokens = new Lexer("+ - * /").tokenize();
        assertToken(Token.TokenType.PLUS, "+", tokens.get(0));
        assertToken(Token.TokenType.MINUS, "-", tokens.get(1));
        assertToken(Token.TokenType.STAR, "*", tokens.get(2));
        assertToken(Token.TokenType.SLASH, "/", tokens.get(3));
        assertToken(Token.TokenType.EOF, null, tokens.get(4));
    }

    @Test
    @DisplayName("完整表达式 token 化")
    void fullExpression() {
        List<Token> tokens = new Lexer("1 + 2 * 3").tokenize();
        assertEquals(6, tokens.size());
        assertToken(Token.TokenType.NUMBER, "1", tokens.get(0));
        assertToken(Token.TokenType.PLUS, "+", tokens.get(1));
        assertToken(Token.TokenType.NUMBER, "2", tokens.get(2));
        assertToken(Token.TokenType.STAR, "*", tokens.get(3));
        assertToken(Token.TokenType.NUMBER, "3", tokens.get(4));
        assertToken(Token.TokenType.EOF, null, tokens.get(5));
    }

    @Test
    @DisplayName("无空格表达式")
    void noSpaces() {
        List<Token> tokens = new Lexer("1+2*3").tokenize();
        assertEquals(6, tokens.size());
        assertToken(Token.TokenType.NUMBER, "1", tokens.get(0));
        assertToken(Token.TokenType.PLUS, "+", tokens.get(1));
    }

    @Test
    @DisplayName("跳过空白字符")
    void skipWhitespace() {
        List<Token> tokens = new Lexer("  1  +  2  ").tokenize();
        assertEquals(4, tokens.size());
        assertToken(Token.TokenType.NUMBER, "1", tokens.get(0));
        assertToken(Token.TokenType.PLUS, "+", tokens.get(1));
        assertToken(Token.TokenType.NUMBER, "2", tokens.get(2));
    }

    @Test
    @DisplayName("非法字符抛异常")
    void illegalCharacter() {
        assertThrows(RuntimeException.class, () -> new Lexer("1 @ 2").tokenize());
    }

    private void assertToken(Token.TokenType expectedType, String expectedText, Token actual) {
        assertEquals(expectedType, actual.type);
        if (expectedText == null) {
            assertNull(actual.text);
        } else {
            assertEquals(expectedText, actual.text);
        }
    }
}
