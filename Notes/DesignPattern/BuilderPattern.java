/*
 * The Builder Pattern is a creational design pattern that provides a way to construct complex objects step by step. It separates the construction of an object from its representatin, allowing the same construction process to create different representations. 
 
 Characteristics

 Encapsulation of Construction Logic 
 – Instead of having a large constructor with multiple parameters, the builder pattern uses a step-by-step approach to configure an object.

 Fluent Interface 
 – The pattern often uses method chaining to improve readability and usability.

 Immutable Object Creation
 -– The final object is typically immutable once built.


 Components:
 1. Product(Complex Object)
 2. Builder Interface(Abstract Builder) - defines the building steps
 3. Concrete Builder - implements the steps to construct the object
 4. Direction - Manages the construction process

 When to use?
 1. when creating objects with many optional parameters
 2. when working with immutable objects
 3. when the construction process is complex and should be separated from the object itself.
 */

// product class
class Car {
    private String engine;
    private int wheels;
    private boolean sunroof;

    private Car(CarBuilder builder) {
        this.engine = builder.engine;
        this.wheels = builder.wheels;
        this.sunrof = builder.sunroof;
    }

    public static class CarBuilder {
        private String engine;
        private int wheels;
        private boolean sunroof;

        public CarBuilder setEngine(String engine) {
            this.engine = engine;
            return this;
        }

        public CarBuilder setWheels(int wheels) {
            this.wheels = wheels;
            return this;
        }

        public CarBuilder setSunroof(boolean sunroof) {
            this.sunroof = sunroof;
            return this;
        }

        public Car build() {
            return new Car(this);
        }
    }

    @Override
    public String toString() {
        return "Car [engine=" + engine + ", wheels=" + wheels + ", sunroof=" + sunroof + "]";
    }
}

public class BuilderPattern {
    public static void main(String[] args) {
        Car car = new Car.CarBuilder()
                .setEngine("V8")
                .setWheels(4)
                .setSuroof(true)
                .build();
        System.out.println(car);
    }
}
