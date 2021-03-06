public class addOperator extends OperatorsFunctions {

    public addOperator()
    {
        this.notation = '+';
        this.oneChar = true;
        this.precedence = 2;
        this.leftAssociative = true;
        this.oneParam = false;
        this.function = false;
    }

    @Override
    public double doOperation(double x, double y)
    {
        return x + y;
    }
}
