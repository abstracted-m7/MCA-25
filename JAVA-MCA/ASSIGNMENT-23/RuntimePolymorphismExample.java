//Q23. Write a java program to demonstrate runtime polymorphism using base class references.

// Base class
class Animal {
    void sound() {
        System.out.println("Animal makes a sound");
    }
}

//Child 1
class Dog extends Animal {
    @Override
    void sound() {
        System.out.println("Dog barks");
    }
}

// Child 2
class Cat extends Animal {
    @Override
    void sound() {
        System.out.println("Cat meows");
    }
}

public class RuntimePolymorphismExample {
    public static void main(String[] args) {

        Animal obj;

        obj = new Animal();
        obj.sound();

        obj = new Dog();  
        obj.sound(); 

        obj = new Cat();   
        obj.sound();
    }
}