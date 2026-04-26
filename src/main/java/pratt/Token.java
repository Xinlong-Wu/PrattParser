package pratt;

public class Token {
    public enum TokenType {
        NUMBER, VARIABLE, PLUS, MINUS, STAR, SLASH, EOF
    }

    public final TokenType type;
    public final String text;

    public Token(TokenType type, String text) {
        this.type = type;
        this.text = text;
    }

    @Override
    public String toString() {
        return type + (text != null ? "(" + text + ")" : "");
    }
}
