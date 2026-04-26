package pratt;

public sealed interface AstNode permits AstNode.NumberNode, AstNode.VariableNode, AstNode.BinaryOpNode {

    String printPrefix();

    record NumberNode(int value) implements AstNode {
        @Override
        public String printPrefix() {
            return String.valueOf(value);
        }
    }

    record VariableNode(String name) implements AstNode {
        @Override
        public String printPrefix() {
            return name;
        }
    }

    record BinaryOpNode(AstNode left, String op, AstNode right) implements AstNode {
        @Override
        public String printPrefix() {
            return op + " " + left.printPrefix() + " " + right.printPrefix();
        }
    }
}
