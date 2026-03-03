package DecoratorDP;
import java.util.*;


interface ICharacter{
    public String getAbilities();
}
class Mario implements ICharacter{
    public String getAbilities(){
        return "Mario";
    }
}

abstract class CharacterDecorator implements ICharacter{
    protected ICharacter character;
    public CharacterDecorator(ICharacter c){
        this.character = c;
    }
}

class HeightUp extends CharacterDecorator{
    public HeightUp(ICharacter c){
        super(c);
    }
    public String getAbilities(){
        return character.getAbilities() + " with HeightUp";
    }
}
class GunPower extends CharacterDecorator{
    public GunPower(ICharacter c){
        super(c);
    }
    public String getAbilities(){
        return character.getAbilities() + " with Gun";
    }
}
class StarPower extends CharacterDecorator{
    public StarPower(ICharacter c){
        super(c);
    }
    public String getAbilities(){
        return character.getAbilities()+" with Star Power (Limited Time)";
    }
}

public class DecoratorPattern {
    public static void main(String[] args) {
        ICharacter mario = new Mario();
        System.out.println("Simple Character: "+ mario.getAbilities());

        // Here i am wrapping the existing Mario object inside another object(HeightUp)
        mario = new HeightUp(mario);
        System.out.println("After HeightUp: "+ mario.getAbilities());

        // Again wrapping the existing mario object inside another object(GunPower)
        mario = new GunPower(mario);
        System.out.println("After Gun power: "+ mario.getAbilities());

        // Again wrapping the existing mario object inside another object(StarPower)
        mario = new StarPower(mario);
        System.out.println("After star power "+ mario.getAbilities());
    }
}
