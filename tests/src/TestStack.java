import Ingredients.Ingredient;
import Sprites.*;
import org.junit.Test;
import static org.junit.Assert.assertTrue;

public class TestStack {

    @Test
    public void TestPush(){
        Stack teststack = new Stack(1);
        Ingredient burger = new Ingredient("Burger", null, 0,0,null);
        Ingredient[] ingredients = {burger};
        teststack.setStack(ingredients);
        teststack.top = teststack.top +1;
        assertTrue("Add burger to stack", teststack.pop() == burger);
    }

    @Test
    public void TestPop(){
        Stack teststack = new Stack(1);
        Ingredient burger = new Ingredient("Burger", null, 0,0,null);
        teststack.push(burger);

        assertTrue("Remove burger from stack", teststack.pop() == burger);

    }

    @Test
    public void TestGetSize(){
        Stack teststack = new Stack(10);
        Ingredient burger = new Ingredient("Burger", null, 0,0,null);
        for (int i= 0; i < 5; i++) {teststack.push(burger);}
        assertTrue("Check number of items in the  stack", teststack.getSize() == 5);
    }

    @Test
    public void TestIsEmpty(){
        Stack teststack = new Stack(0);
        assertTrue("Testing isEmpty function on empty stack", teststack.isEmpty());
    }
    @Test
    public void TestIsEmpty_OnFull(){
        Stack teststack = new Stack(1);
        Ingredient burger = new Ingredient("Burger", null, 0,0,null);
        teststack.push(burger);
        assertTrue("Testing isEmpty function on full stack", teststack.isEmpty()==false);
    }

    @Test
    public void TestIsFull(){
        Stack teststack = new Stack(1);
        Ingredient burger = new Ingredient("Burger", null, 0,0,null);
        teststack.push(burger);
        assertTrue("Testing isFull function on full stack", teststack.isFull());
    }

    @Test
    public void TestIsFull_onEmpty(){
        Stack teststack = new Stack(0);
        assertTrue("Testing isFull function on empty stack", teststack.isFull()==true);
    }

    @Test
    public void TestPushToFullStack(){
        Stack teststack = new Stack(1);
        Ingredient burger = new Ingredient("Burger", null, 0,0,null);
        teststack.push(burger);

        teststack.push(burger);



        assertTrue("Pushing burger to full stack", teststack.getSize() == 1);

    }

    @Test
    public void TestPopFromEmptyStack(){
        Stack teststack = new Stack(0);
        //      Fails due to an out of bounds exception, error should be handled in the stack class?
        assertTrue("Popping from empty stack", teststack.pop() == null);
    }

//    @Test
//    public void TestInvalidStackSize(){
//        Stack teststack = new Stack(-1);
//        //      Fails due to an negative array size exception
//        assertTrue("Initializing a stack with -1 size", teststack == null);
//    }



}
