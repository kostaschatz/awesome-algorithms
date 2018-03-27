public class powOperator extends OperatorsFunctions {
    public powOperator()
    {
        this.notation = '^';
        this.oneChar = true;
        this.precedence = 5;
        this.leftAssociative = false;
        this.oneParam = false;
        this.function = false;
    }

    @Override
    public double doOperation(double x, double y)
    {
        return Math.pow(x , y);
    }

}
