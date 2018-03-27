public class divOperator extends OperatorsFunctions{

    public divOperator()
    {
        this.notation = '/';
        this.oneChar = true;
        this.precedence = 4;
        this.leftAssociative = true;
        this.oneParam = false;
        this.function = false;
    }

    @Override
    public double doOperation(double x, double y)
    {
        if(y == 0) return Double.MAX_VALUE;
        return x / y;
    }
}
