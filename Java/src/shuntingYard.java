import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

public class shuntingYard {

    //Check if we have input.
    private boolean inputGiven;
    private String input;
    private char[] inputToChars;

    //Require default operator array.
    private boolean defaultOperators;
    private static boolean debugSwitch = false;


    private int inputLength;





    //For separating input.
    private double[] numArray;
    private OperatorsFunctions[] opFunArray;
    private char[] varArray;
    private String[] opArray;
    private String[] functionArray;


    //For transforming to RPN
    private LinkedList<Double> numQueue;
    private LinkedList<String> opQueue;
    private Stack<Double> numStack;
    private Stack<Character> opStack;
    private int ArrayLength = 0;

    private OperatorsFunctions[] operatorsArray;
    private int operatorsNumber;

    public shuntingYard()
    {
        numStack = new Stack<Double>();
        opStack = new Stack<Character>();

        numQueue = new LinkedList<Double>();
        opQueue = new LinkedList<String>();



    }
    public shuntingYard(String in)
    {
        numStack = new Stack<Double>();
        opStack = new Stack<Character>();

        numQueue = new LinkedList<Double>();
        opQueue = new LinkedList<String>();


        getInput(in);
        numArray = new double[this.input.length()];
        opFunArray = new OperatorsFunctions[this.input.length()];
        opArray = new String[this.input.length()];


        inputNoSpaces();
        createDefaultOperatorsArray();
        separateInput();
        dbgPrintArrays();
        applyShuntingYard();
        testPrint();;

    }



    public void getInput(String input)
    {

        this.input = input;
        this.inputToChars = input.toCharArray();
    }
    public void getInput(char[] input) {
        this.input = input.toString();
        this.inputToChars = input;
    }

    private void inputNoSpaces()
    {
        char[] input =  new char[this.input.length()];
        int counter = 0;
       // char space = " ";
        for(int i = 0; i < this.input.length(); i++ )
        {
            if(this.inputToChars[i] == ' ') continue;
            input[counter] = this.inputToChars[i];
            counter++;
            System.out.print(this.inputToChars[i]);

        }





        this.inputToChars = new char[counter];
        for(int i = 0; i < counter; i++)
        {
            this.inputToChars[i] = input[i];
        }

        this.input = inputToChars.toString();
    }


    private boolean findOperator(char op)
    {
        for (OperatorsFunctions of : operatorsArray ) {
            if (!of.isFunction()) {
                if (of.isOneChar()) {
                    if (op == of.getNotation()) {
                        return true;
                    }
                }
            }
        }
        return false;
    }
    private boolean findOperator(char[] op)
    {
        for (OperatorsFunctions of : operatorsArray ) {
            if (!of.isFunction()) {
                if (!of.isOneChar()) {
                    if (op.length == of.getNotationString().toCharArray().length) {
                        if (op == of.getNotationString().toCharArray()) return true;
                    }
                }
            }
        }

        return false;
    }
    private boolean findFunction(char[] fun)
    {
        for (OperatorsFunctions of : operatorsArray ) {
            if (of.isFunction()) {
                if (!of.isOneChar()) {
                    if (fun.length == of.getNotationString().toCharArray().length) {
                        if (fun == of.getNotationString().toCharArray()) return true;
                    }
                }
            }
        }
        return false;
    }

    private boolean isOperator(char op)
    {
        if(Character.isDigit(op)) return false;
        if(Character.isLetter(op)) return false;
        if(op == '.' || op == '(' || op == ')') return false;
        return true;
    }

    private OperatorsFunctions getOperator(char op)
    {
        for (OperatorsFunctions of : operatorsArray ) {
            if (!of.isFunction()) {
                if (of.isOneChar()) {
                    if (op == of.getNotation()) {
                        return of;
                    }
                }
            }
        }
        return new OperatorsFunctions();
    }

    private OperatorsFunctions getOperator(char[] op)
    {
        for (OperatorsFunctions of : operatorsArray ) {
            if (!of.isFunction()) {
                if (!of.isOneChar()) {
                    if (op.length == of.getNotationString().toCharArray().length) {
                        if (op == of.getNotationString().toCharArray()) return of;
                    }
                }
            }
        }

        return new OperatorsFunctions();
    }

    private OperatorsFunctions getFunction(char[] fun)
    {
        for (OperatorsFunctions of : operatorsArray ) {
            if (of.isFunction()) {
                if (!of.isOneChar()) {
                    if (fun.length == of.getNotationString().toCharArray().length) {
                        if (fun == of.getNotationString().toCharArray()) return of;
                    }
                }
            }
        }
        return new OperatorsFunctions();
    }

    private void createDefaultOperatorsArray()
    {
        OperatorsFunctions add = new addOperator();
        OperatorsFunctions sub = new subOperator();
        OperatorsFunctions mul = new mulOperator();
        OperatorsFunctions div = new divOperator();
        OperatorsFunctions pow = new powOperator();

        this.operatorsArray = new OperatorsFunctions[5];
        this.operatorsArray[0] = add;
        this.operatorsArray[1] = sub;
        this.operatorsArray[2] = mul;
        this.operatorsArray[3] = div;
        this.operatorsArray[4] = pow;
        this.operatorsNumber = 5;

    }

    private boolean getOperator(String op)
    {
        for (OperatorsFunctions of : operatorsArray ) {
            if (!of.isFunction()) {
                if (of.isOneChar()) {
                    if(op.length() == 1) {
                        if (op.toCharArray()[0] == of.getNotation()) {
                            return true;
                        }
                    }
                }else
                {
                    if(op == of.getNotationString()) return true;
                }
            }
        }
        return false;
    }

    public static void enableDubugLog(boolean debugOnOff)
    {
        debugSwitch = debugOnOff;
    }
    public static void Debug(String strdebug)
    {
        if(debugSwitch) System.out.println(strdebug);
    }
    public static void Debug(String strdebug ,int intdebug)
    {
        if(debugSwitch) System.out.println(strdebug + " " + String.valueOf(intdebug));
    }


    private void separateInput()
    {
        //TODO check if minus signs is not operator.
        int counter = 0;
        double store = 0;
        double afterPoint = 10;
        boolean atEnd = false;
        StringBuilder tempOp =  new StringBuilder();

        int i = 0;

        while(i < this.inputToChars.length && !atEnd)
        {
             while((Character.isDigit(this.inputToChars[i]) || this.inputToChars[i] == '.') &&!atEnd)
             {
                 //add to array and reset temps
                 //increase arrayCounter
                 store = 10*store + Character.getNumericValue(this.inputToChars[i]);
                 i++;
                 if(this.inputToChars[i] == '.')
                 {
                     i++;
                     while(Character.isDigit(this.inputToChars[i]))
                     {
                         store = store + Character.getNumericValue(this.inputToChars[i])/afterPoint;
                         i++;
                         if(i == this.inputToChars.length - 1)
                         {
                             atEnd = true;
                             break;
                         }
                         if(!Character.isDigit(this.inputToChars[i+1]))
                         {
                             store = 0;
                             afterPoint = 10;
                         }

                     }
                 }
                 if(i == this.inputToChars.length - 1)
                 {
                     atEnd = true;
                     break;
                 }
                 if(!Character.isDigit(this.inputToChars[i]) && !atEnd)
                 {
                     store = 0;
                     afterPoint = 10;
                 }

             }

         while(isOperator(this.inputToChars[i]) && atEnd)
         {
            tempOp.append(this.inputToChars[i]);
            i++;
            if(i == this.inputToChars.length - 1)
            {
                //add to array and reset temps
                atEnd = true;
                break;
            }
            if(!isOperator(this.inputToChars[i]))
            {
                //add to array and reset temps
                //increase arrayCounter
                break;
            }


         }

             //TODO

        }



    }

    public void dbgPrintArrays()
    {
        System.out.println("starttter");
        for(int i = 0; i < ArrayLength; i++)
        {

                System.out.print(numArray[i]);
                System.out.print(" \t|-|  ");

        }
        System.out.println(" ");
        for(int i = 0; i < ArrayLength; i++)
        {

            System.out.print(opArray[i]);
            System.out.print("\t|-|  ");

        }

        System.out.println("DONENEE");
    }

    private void applyShuntingYard()
    {
        //TODO
    }




    private void testPrint()
    {
        //TODO
    }

}
