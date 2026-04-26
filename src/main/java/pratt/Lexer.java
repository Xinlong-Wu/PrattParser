package pratt;

import java.util.ArrayList;
import java.util.List;

public class Lexer {
    private final String input;
    private int pos;

    public Lexer(String input) {
        this.input = input;
        this.pos = 0;
    }

    public List<Token> tokenize() {
        List<Token> tokens = new ArrayList<>();
        while (pos < input.length()) {
            char c = input.charAt(pos);
            if (Character.isWhitespace(c)) {
                pos++;
            } else if (Character.isDigit(c)) {
                tokens.add(readNumber());
            } else if (Character.isLetter(c)) {
                tokens.add(readVariable());
            } else {
                tokens.add(readOperator());
            }
        }
        tokens.add(new Token(Token.TokenType.EOF, null));
        return tokens;
    }

    private Token readNumber() {
        int start = pos;
        while (pos < input.length() && Character.isDigit(input.charAt(pos))) {
            pos++;
        }
        return new Token(Token.TokenType.NUMBER, input.substring(start, pos));
    }

    private Token readVariable() {
        char c = input.charAt(pos);
        pos++;
        return new Token(Token.TokenType.VARIABLE, String.valueOf(c));
    }

    private Token readOperator() {
        char c = input.charAt(pos);
        pos++;
        return switch (c) {
            case '+' -> new Token(Token.TokenType.PLUS, "+");
            case '-' -> new Token(Token.TokenType.MINUS, "-");
            case '*' -> new Token(Token.TokenType.STAR, "*");
            case '/' -> new Token(Token.TokenType.SLASH, "/");
            default -> throw new RuntimeException("Unexpected character: " + c);
        };
    }
}
