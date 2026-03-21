import java.util.*;

interface VendingState{
    VendingState insertCoin(VendingMachine machine, int coin);
    VendingState selectItem(VendingMachine machine);
    VendingState dispense(VendingMachine machine);
    VendingState returnCoin(VendingMachine machine);
    VendingState refill(VendingMachine machine , int quantity);
    String getStateName();
}

class VendingMachine{
    private VendingState currentState;
    private int totalItem;
    private int itemPrice;
    private int insertedCoin;

    private VendingState noCoinState;
    private VendingState hasCoinState;
    private VendingState dispenseState;
    private VendingState soldOutState;

    public VendingMachine(int totalItem, int itemPrice){
        this.totalItem = totalItem;
        this.itemPrice = itemPrice;
        this.insertedCoin =0;

        noCoinState= new NoCoinState();
        hasCoinState = new HasCoinState();
        dispenseState = new DispenseState();
        soldOutState = new SoldOutState();
        // HasCoinState hasCoinState = new HasCoinState();

        if(totalItem > 0){
            currentState = noCoinState;
        }else currentState = soldOutState;
    }
    public VendingState getNoCoinState(){
        return noCoinState;
    }
    public VendingState getHasCoinState(){
        return hasCoinState;
    }
    public VendingState getDispenseState(){
        return dispenseState;
    }
    public VendingState getSoldOutState(){
        return soldOutState;
    }

    public void selectItem() {
        currentState = currentState.selectItem(this);
    }

    public void insert(int coin){
        currentState = currentState.insertCoin(this, coin);
    }

    public void dispense() {
        currentState = currentState.dispense(this);
    }
    
    public void returnCoin() {
        currentState = currentState.returnCoin(this);
    }
    
    public void refill(int quantity) {
        currentState = currentState.refill(this, quantity);
    }

    public void printStatus() {
        System.out.println("\n--- Vending Machine Status ---");
        System.out.println("Items remaining: " + totalItem);
        System.out.println("Inserted coin: Rs " + insertedCoin);
        System.out.println("Current state: " + currentState.getStateName() + "\n");
    }

    public void setInsertedCoin(int coin){
        insertedCoin = coin;
    }

    public int getInsertedCoin(){
        return insertedCoin;
    }
    public void addCoin(int coin){
        insertedCoin += coin;
    }

    public void incrementItemCount(int quantity){
        totalItem += quantity;
    }

    public int getPrice(){
        return itemPrice;
    }
    public int getTotalItem(){
        return totalItem;
    }
    public void decrementTotalItem(){
        totalItem -= 1;
    }

}
class NoCoinState implements VendingState{
    public VendingState insertCoin(VendingMachine machine, int coin){
        machine.setInsertedCoin(coin);
        System.out.println("Coin Inserted. Current balance: Rs" + coin);
        return machine.getHasCoinState(); // => transition from noCoinState to hasCoinState
    }
    public VendingState selectItem(VendingMachine machine){
        System.out.println("Please insert coin first");
        return machine.getNoCoinState();
    }
    public VendingState dispense(VendingMachine machine){
        System.out.println("Please insert coin then select Item first!");
        return machine.getNoCoinState();
    }
    public VendingState returnCoin(VendingMachine machine){
        System.out.println("No coin to return");
        return machine.getNoCoinState();
    }
    public VendingState refill(VendingMachine machine, int quantity){
        System.out.println("Items are refilling ...");
        machine.incrementItemCount(quantity);
        return machine.getNoCoinState();
    }
    public String getStateName(){
        return "NO_COIN";
    }
}

class HasCoinState implements VendingState{
    public VendingState insertCoin(VendingMachine machine, int coin){
        machine.addCoin(coin);
        System.out.println("Additional coin inserted. current balance : Rs"+ machine.getInsertedCoin());
        return machine.getHasCoinState();
    }
    public VendingState selectItem(VendingMachine machine){
        if(machine.getInsertedCoin() >= machine.getPrice()){
            System.out.println("Item selected. Dispensing...");
            int change = machine.getInsertedCoin() - machine.getPrice();
            if(change>0){
                System.out.println("Change returned: Rs "+change);
            }
            machine.setInsertedCoin(0);
            return machine.getDispenseState();
        }else{
            int needed = machine.getPrice()-machine.getInsertedCoin();
            System.out.println("Insufficient funds. Need Rs " + needed + " more.");
            return machine.getHasCoinState();
        }
    }
    public VendingState dispense(VendingMachine machine){
        System.out.println("Please select an Item first!");
        return machine.getHasCoinState();
    }
    public VendingState returnCoin(VendingMachine machine){
        System.out.println("Coin returned: Rs " + machine.getInsertedCoin());
        machine.setInsertedCoin(0);
        return machine.getNoCoinState();
    }

    public VendingState refill(VendingMachine machine, int quantity){
        System.out.println("Can't refil in this state");
        return machine.getHasCoinState(); // Stay in same state
    }
    
    public String getStateName() {
        return "HAS_COIN";
    }
}

class DispenseState implements VendingState{
    public VendingState insertCoin(VendingMachine machine, int coin){
        System.out.println("Please wait, already dispensing item. Coin returned: Rs "+ coin);
        return machine.getDispenseState();
    }
    public VendingState selectItem(VendingMachine machine){
        System.out.println("Already dispensing item. Please wait.");
        return machine.getDispenseState();
    }
    public VendingState dispense(VendingMachine machine){
        System.out.println("Item Dispensed");
        machine.decrementTotalItem();
        if(machine.getTotalItem()>0){
            return machine.getNoCoinState();
        }
        else {
            System.out.println("All product sold out!");
            return machine.getSoldOutState();
        }
    }
    public VendingState returnCoin(VendingMachine machine){
        System.out.println("Cann't return coin while dispensing item!");
        return machine.getDispenseState();
    }

    public VendingState refill(VendingMachine machine, int quantity){
        System.out.println("Can't refill in this state");
        return machine.getDispenseState();
    }
    public String getStateName(){
        return "DISPENSING";
    }
}

class SoldOutState implements VendingState{
    public VendingState insertCoin(VendingMachine machine, int coin){
        System.out.println("Product is sold out. coin returned Rs: "+coin);
        return machine.getSoldOutState();
    }
    public VendingState selectItem(VendingMachine machine){
        System.out.println("Product sold out");
        return machine.getSoldOutState();
    }
    public VendingState dispense(VendingMachine machine){
        System.out.println("Product is sold out");
        return machine.getSoldOutState();
    }
    public VendingState returnCoin(VendingMachine machine){
        System.out.println("No coin was inserted");
        return machine.getSoldOutState();
    }
    public VendingState refill(VendingMachine machine, int quantity){
        System.out.println("Items refilling");
        machine.incrementItemCount(quantity);
        return machine.getNoCoinState();
    }

    public String getStateName(){
        return "SOLD_OUT";
    }

}

public class VendingMachineMain{
    public static void main(String[] args) {
        VendingMachine machine = new VendingMachine(2, 20);
        machine.printStatus();

        System.out.println("1. Trying to select item without coin:");
        machine.selectItem();
        machine.printStatus();

        System.out.println("2. Inserting coin:");
        machine.insert(10);  // State changes to HAS_COIN
        machine.printStatus();

        System.out.println("3. Selecting item with insufficient funds:");
        machine.selectItem();  // Insufficient funds, stays in HAS_COIN
        machine.printStatus();
        
        System.out.println("4. Adding more coins:");
        machine.insert(10);  // Add more money, stays in HAS_COIN
        machine.printStatus();
        
        System.out.println("5. Selecting item Now");
        machine.selectItem();  // State changes to SOLD
        machine.printStatus();
        
        System.out.println("6. Dispensing item:");
        machine.dispense(); // State changes to NO_COIN (items remaining)
        machine.printStatus();
        
        System.out.println("7. Buying last item:");
        machine.insert(20);  // State changes to HAS_COIN
        machine.selectItem();  // State changes to SOLD
        machine.dispense(); // State changes to SOLD_OUT (no items left)
        machine.printStatus();

        System.out.println("8. Trying to use sold out machine:");
        machine.insert(5);  // Coin returned, stays in SOLD_OUT

        System.out.println("9. Trying to use sold out machine:");
        machine.refill(2);
        machine.printStatus(); // State changes NO_COIN
        
    }
}