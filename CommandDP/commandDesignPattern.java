package CommandDP;

// ---------------------------------------------
//         COMMAND INTERFACE
// ---------------------------------------------
interface ICommand {
    void execute();
    void undo();
}

// ---------------------------------------------
//        Recievers
// ---------------------------------------------

class Light{
    void on(){
        System.out.println("Light is On");
    }
    void off(){
        System.out.println("Light is Off");
    }
}

class Fan {
    void on(){
        System.out.println("Fan is On");
    }
    void off(){
        System.out.println("Fan is Off");
    }
}

// ---------------------------------------------
//        CONCRETE COMMAND FOR LIGHT
// ---------------------------------------------

class LightCommand implements ICommand{
    Light light;

    public LightCommand(Light l){
        this.light = l;
    }

    @Override
    public void execute(){
        light.on();
    }

    @Override
    public void undo(){
        light.off();
    }
}

// ---------------------------------------------
//        CONCRETE COMMAND FOR FAN
// ---------------------------------------------

class FanCommand implements ICommand{
    Fan fan;
    public FanCommand(Fan f) {
        this.fan = f;
    }

    @Override
    public void execute(){
        fan.on();
    }
    @Override
    public void undo(){
        fan.off();
    }
}

// ---------------------------------------------------------------------
//       SOURCE: Remote Controller with static array of 4 buttons
// ---------------------------------------------------------------------

class RemoteController{
    private static final int totalBtn = 4;
    private ICommand[] buttons;
    private boolean[] buttonPressed;

    public RemoteController(){
        buttons = new ICommand[totalBtn];
        buttonPressed = new boolean[totalBtn];

        for(int i=0; i<totalBtn; i++){
            buttons[i]=null;
            buttonPressed[i]=false;
        }
    }

    public void setCommand(int idx, ICommand command){
        if(idx>=0 && idx<totalBtn){
            buttons[idx]=command;
            buttonPressed[idx]=false;
        }
    }

    public void pressButton(int idx){
        if(idx>=0 && idx<totalBtn && buttons[idx]!=null){
            if(!buttonPressed[idx]){
                buttons[idx].execute();
            }else{
                buttons[idx].undo();
            }
            buttonPressed[idx]= !buttonPressed[idx];
        }else{
            System.out.println("No Command assigned at button" + idx);
        }
    }
}


public class commandDesignPattern{
    public static void main(String[] args) {
        Light livingRoomLight = new Light();
        Fan ceilingFan = new Fan();

        RemoteController remote = new RemoteController();
        remote.setCommand(0, new LightCommand(livingRoomLight));
        remote.setCommand(1, new FanCommand(ceilingFan));

        System.out.println("--------- Toggling Light button 0 ---------");
        remote.pressButton(0);
        remote.pressButton(0);

        System.out.println("--------- Toggling Fan button 1 -----------");
        remote.pressButton(1);
        remote.pressButton(1);
    }
}