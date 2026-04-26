package pratt;

public class Main {
    public static void main(String[] args) {
        String[] expressions = {
            "1 + 2 * 3",
            "a * b + c",
            "1 + 2 + 3",
            "10 / 2 * 3",
        };

        for (String expr : expressions) {
            Lexer lexer = new Lexer(expr);
            Parser parser = new Parser(lexer.tokenize());
            AstNode ast = parser.parse();
            System.out.println(expr + "  =>  " + ast.printPrefix());
        }
    }
}
