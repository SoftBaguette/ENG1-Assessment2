package Sprites;

import Ingredients.Ingredient;

public class Stack {
    Ingredient arr[];
    public int top;
    int capacity;

    
    /**
     * Initialize stack with a given size
     * 
     * @param size The size of the stackc
     */
    public Stack(int size){
        arr = new Ingredient[size];
        capacity = size;
        top = -1;
    }


    /**
     * Push an ingredient to the top of the stack
     * 
     * @param x Ingredient to push to the top of the stack
     */
    public void push(Ingredient x){
        // can't push item to stack if the stack is full
        if (isFull()){
            System.out.println("Stack OverFlow");
        }else{
            // increments top after adding x to stack
            arr[++top] = x;
        }
        
    }


    /**
     * Remove the item from the top of the stack
     * 
     * @return the item that has been popped
     */
    public Ingredient pop(){
        // if stack is empty, no ellement to pop
        if (isEmpty()){
            System.out.println("Stack Empty");
            return null;
        }
        // decrements top after popping
        Ingredient topIngredient = arr[top];
        arr[top] = null;
        top--;
        return topIngredient;
    }


    /**
     * @return size of stack
     */
    public int getSize(){
        return top +1;
    }

    /**
     * @return if the stack is empty
     */
    public boolean isEmpty(){
        return top == -1;
    }

    /**
     * 
     * @return if the stack is full
     */
    public boolean isFull(){
        return top == capacity -1;
    }


    /**
     * display elements of stack
     */
    public void printStack(){
        System.out.print( "\n Items: ");
        for (int i = 0; i <= top; i++) {
            System.out.print("(" + arr[i].name + ", "+ arr[i].status+")" + ", ");
        }
    }

    /**
     * 
     * @return the ingredient at the top of the stack
     */
    public Ingredient peak(){
        if (isEmpty()){
            return null;
        }
        return arr[top];
    }

//    added for testing purposes
    public void setStack(Ingredient[] ingredients) {arr = ingredients;}


}
