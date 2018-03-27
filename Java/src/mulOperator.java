public class mulOperator extends OperatorsFunctions {

    public mulOperator()
    {
        this.notation = '*';
        this.oneChar = true;
        this.precedence = 4;
        this.leftAssociative = true;
        this.oneParam = false;
        this.function = false;
    }

    @Override
    public double doOperation(double x, double y)
    {
        return x * y;
    }
}
