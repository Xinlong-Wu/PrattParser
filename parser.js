const readline = require('readline');

const rl = readline.createInterface({
  input: process.stdin,
  output: process.stdout
})

class Node{
    constructor(lhs, op, rhs){
        this.lhs = lhs;
        this.op = op;
        this.rhs = rhs;
    }
}

const bindingPower = {
    id: 0,
    mul: 20,
    add: 10
}

class Token{
    constructor(value){
        this.value = value;
    }

    bindingPower(){
        switch (this.value) {
            case '+':
                return bindingPower.add;
            case '*':
                return bindingPower.mul;
        
            default:
                return bindingPower.id;
        }
    }
}

class Laxer{
    constructor(buffer){
        this.buffer = buffer;
        this.tokens = buffer.split(' ').map((value) => new Token(value));
        this.pos = 0;
        this.currentTk = "";
    }

    next(){
        if (this.pos >= this.tokens.length){
            return new Token("");
        }
        this.currentTk = this.tokens[this.pos];
        return this.tokens[this.pos++];
    }

    peek(){
        if (this.pos >= this.tokens.length){
            return new Token("");
        }
        return this.tokens[this.pos];
    }
}

function parseUnary(){
    const token = laxer.next();
    if (token.value == '+' || token.value == '-'){
        return new Node(null, token, parseUnary());
    }
    if (laxer.peek().value == ""){
        return new Node (null, token, null);
    }
    return parseBinary(token, token.bindingPower());
}

function parseBinary(left){
    const op = laxer.next();
    return new Node(left, op, parse(op.bindingPower()));
}

function parse(bindingPower){
    left = parseUnary();

    while(laxer.peek().bindingPower() > bindingPower){
        laxer.next();
        left = parseBinary(left);
    }

    return left;
}

function printNode(node){
    str = "";
    if (node instanceof Token){
        str += " " + node.value + " ";
    }
    else{
        str += "("
        if (node.lhs != null){
            str += printNode(node.lhs);
        }
        str +=  " " + node.op.value + " ";
        if (node.rhs != null){
            str += printNode(node.rhs);
        }
        str += ")"
    }
    return str;
}

var laxer = null;
function main(text){
    laxer = new Laxer(text);
    str = printNode(parse(0));
    console.log(str);
}

// gat input from user
main("5 + + 5 * - 8");
// rl.question("", (name) => {
//     text = name;
//     main(text);
//     rl.close();
//   });