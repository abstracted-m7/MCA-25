// Q22. Develop a java program to demonstrate multilevel inheritance.


// Base class
class Animal {
    void eat() {
        System.out.println("Animal eats food");
    }
}

//Parent class
class Dog extends Animal {
    void bark() {
        System.out.println("Dog barks");
    }
}

//child class
class Puppy extends Dog {
    void weep() {
        System.out.println("Puppy weeps");
    }
}

public class MultilevelInheritanceExample {
    public static void main(String[] args) {
        Puppy obj = new Puppy();

        obj.eat();
        obj.bark();
        obj.weep();
    }
}