package pratt;

import java.util.List;

public class Parser {
    private final List<Token> tokens;
    private int pos;

    public Parser(List<Token> tokens) {
        this.tokens = tokens;
        this.pos = 0;
    }

    public AstNode parse() {
        AstNode result = parseExpression(0);
        if (peek().type != Token.TokenType.EOF) {
            throw new RuntimeException("Unexpected token: " + peek());
        }
        return result;
    }

    private AstNode parseExpression(int rbp) {
        Token token = next();
        AstNode left = nud(token);

        while (lbp(peek()) > rbp) {
            token = next();
            left = led(token, left);
        }
        return left;
    }

    private AstNode nud(Token token) {
        return switch (token.type) {
            case NUMBER -> new AstNode.NumberNode(Integer.parseInt(token.text));
            case VARIABLE -> new AstNode.VariableNode(token.text);
            default -> throw new RuntimeException("Unexpected token in prefix position: " + token);
        };
    }

    private AstNode led(Token token, AstNode left) {
        int rbp = lbp(token) + 1;
        AstNode right = parseExpression(rbp);
        return new AstNode.BinaryOpNode(left, token.text, right);
    }

    private int lbp(Token token) {
        return switch (token.type) {
            case PLUS, MINUS -> 10;
            case STAR, SLASH -> 20;
            default -> 0;
        };
    }

    private Token peek() {
        return tokens.get(pos);
    }

    private Token next() {
        return tokens.get(pos++);
    }
}
