import java.util.*;

abstract class MoneyHandler{
    protected MoneyHandler nextHandler;
    public MoneyHandler(){
        this.nextHandler = null;
    }
    public void setNextHandler(MoneyHandler next){
        this.nextHandler = next;
    }
    abstract public void dispense(int amount);
}

class ThousandHandler extends MoneyHandler{
    private int numNotes;
    public ThousandHandler(int notes){
        this.numNotes = notes;
    }

    @Override
    public void dispense(int amount) {
        int reqNotes = amount / 1000;
        if(reqNotes > numNotes){
            reqNotes = numNotes;
            numNotes = 0;
        }else{
            numNotes -= reqNotes;
        }

        if(reqNotes > 0) System.out.println("Dispensing amount "+reqNotes+ " x ₹1000 notes.");

        int remainingAmount = amount - reqNotes*1000;
        if(remainingAmount>0){
            if(nextHandler!=null) nextHandler.dispense(remainingAmount);
            else System.out.println("Remaning amount of "+ remainingAmount+" Cann't be fullfilled (Insufficient fund in ATM)");
        }
    }
}

class FiveHundredHandler extends MoneyHandler{
    private int numNotes;
    public FiveHundredHandler(int notes){
        this.numNotes = notes;
    }

    @Override
    public void dispense(int amount) {
        int reqNotes = amount / 500;
        if(reqNotes > numNotes){
            reqNotes = numNotes;
            numNotes = 0;
        }else{
            numNotes -= reqNotes;
        }

        if(reqNotes > 0) System.out.println("Dispensing amount "+reqNotes+ " x ₹500 notes.");

        int remainingAmount = amount - reqNotes*500;
        if(remainingAmount>0){
            if(nextHandler!=null) nextHandler.dispense(remainingAmount);
            else System.out.println("Remaning amount of "+ remainingAmount+" Cann't be fullfilled (Insufficient fund in ATM)");
        }
    }
}

class HundredHandler extends MoneyHandler{
    private int numNotes;
    public HundredHandler(int notes){
        this.numNotes = notes;
    }

    @Override
    public void dispense(int amount) {
        int reqNotes = amount / 100;
        if(reqNotes > numNotes){
            reqNotes = numNotes;
            numNotes = 0;
        }else{
            numNotes -= reqNotes;
        }

        if(reqNotes > 0) System.out.println("Dispensing amount "+reqNotes+ " x ₹100 notes.");

        int remainingAmount = amount - reqNotes*100;
        if(remainingAmount>0){
            if(nextHandler!=null) nextHandler.dispense(remainingAmount);
            else System.out.println("Remaning amount of "+ remainingAmount+" Cann't be fullfilled (Insufficient fund in ATM)");
        }
    }
}

class FiftyHandler extends MoneyHandler{
    private int numNotes;
    public FiftyHandler(int notes){
        this.numNotes = notes;
    }

    @Override
    public void dispense(int amount) {
        int reqNotes = amount / 50;
        if(reqNotes > numNotes){
            reqNotes = numNotes;
            numNotes = 0;
        }else{
            numNotes -= reqNotes;
        }

        if(reqNotes > 0) System.out.println("Dispensing amount "+reqNotes+ " x ₹50 notes.");

        int remainingAmount = amount - reqNotes*50;
        if(remainingAmount>0){
            if(nextHandler!=null) nextHandler.dispense(remainingAmount);
            else System.out.println("Remaning amount of "+ remainingAmount+" Cann't be fullfilled (Insufficient fund in ATM)");
        }
    }
}


public class Chain_of_responsibility{
    public static void main(String[] args) {
        MoneyHandler thousandHandler = new ThousandHandler(6);
        MoneyHandler fiveHunderedHandler = new FiveHundredHandler(4);
        // MoneyHandler hundredHandler = new HundredHandler(3);
        MoneyHandler fiftyHandler = new FiftyHandler(7);

        thousandHandler.setNextHandler(fiveHunderedHandler);
        // fiveHunderedHandler.setNextHandler(hundredHandler);
        fiveHunderedHandler.setNextHandler(fiftyHandler);
        thousandHandler.dispense(4750);
    }
}