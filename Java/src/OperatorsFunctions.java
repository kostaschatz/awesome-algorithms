public class OperatorsFunctions {

    protected char notation;
    protected char[] notationString;
    protected int precedence;
    protected boolean leftAssociative;
    protected boolean function;
    protected boolean oneChar;
    protected boolean oneParam;

    OperatorsFunctions()
    {
        notation = '+';
        String tempNotation = "+";
        notationString = tempNotation.toCharArray();
        precedence = 2;
        leftAssociative = true;
        function = false;
    }

    //Operators(char notation , int precedence , boolean leftAssociative)

    public int getPrecedence() {
        return precedence;
    }

    public char getNotation() {
        return notation;
    }

    public String getNotationString() {
        String tempNotation = this.notationString.toString();
        return tempNotation;
    }

    public boolean isLeftAssociative() {
        return leftAssociative;
    }

    public boolean isFunction() {
        return function;
    }

    public boolean isOneChar() {
        return oneChar;
    }

    public boolean isOneParam() {
        return oneParam;
    }

    public double doOperation(double x , double y)
    {
        return x + y;
    }

    public double doOperation(double x)
    {
        return x;
    }

    public double doFunction(double x)
    {
        return  x;
    }


}
