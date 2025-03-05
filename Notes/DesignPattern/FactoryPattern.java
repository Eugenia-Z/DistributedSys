/*
 * Factory 模式优点：
 * 1. 封装对象创建逻辑，避免直接使用new 关键字
 * 2. 遵循开闭原则Open Close Principle，新增产品时不修改现有代码，只需扩展新的工厂类
 * 3. 符合依赖倒置Depencency Inversion Principle. 调用方法依赖抽象工厂，而非具体实现
 * 
 * Drawbacks:
 * 1. 增加代码复杂度，需要额外的工厂类
 * 2. 导致过多的类。如果产品种类很多，工厂类也会变多
 * 
 * 适用于：
 * 1. 需要动态创建对象，而不是直接使用new
 * 2. 代码中有多个相似对象的创建逻辑，但具体实例在运行时才能确定
 * 3. 需要解耦调用者与具体类，方便扩展。
 * 适用于数据库连接、日志记录、UI组件等
 */

// Product interface
interface Beverage {
    void drink();
}

// Concrete products: coffee
class Coffee implements Beverage {
    @Override
    public void drink() {
        System.out.println("Drinking Coffee ☕");
    }
}

// Concrete products: tea
class Tea implements Beverage {
    @Override
    public void drink() {
        System.out.println("Drinking Tea 🍵");
    }
}

// Define product factory interface
interface BeverageFactory {
    Beverage creatBeverage();
}

// Concreate product factory
class CoffeeFactory implements BeverageFactory {
    @Override
    public Beverage creatBeverage() {
        return new Coffee();
    }
}

// Concreate product factory
class TeaFactory implements BeverageFactory {
    @Override
    public Beverage creatBeverage() {
        return new Tea();
    }
}

// Client invocation
public class FactoryPattern {
    public static void main(String[] args) {
        BeverageFactory coffeeFactory = new CoffeeFactory();
        Beverage coffee = coffeeFactory.creatBeverage();
        coffee.drink();

        BeverageFactory teaFactory = new TeaFactory();
        Beverate tea = teaFactory.creatBeverage();
        tea.drink();
    }
}