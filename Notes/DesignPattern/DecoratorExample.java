// define Beverage interface
interface Beverage {
    String getDescription();

    double cost();
}

// Concreate coffee types
class Espresso implements Beverage {
    @Override
    public String getDescription() {
        return "Expresso";
    }

    @Override
    public double cost() {
        return 2.0;
    }
}

class Latte implements Beverage {
    @Override
    public String getDescription() {
        return "Latte";
    }

    @Override
    public double cost() {
        return 2.5;
    }
}

// Decorator class, implementing Beverage
abstract class CondimentDecorator implements Beverage {
    protected Beverage beverage; // the object being decorated

    public CondimentDecorator(Beverage beverage) {
        this.beverage = beverage;
    }

    public abstract String getDescription(); // method signature only, subclass to implement
}

// Concrete Decorator
class Milk extends CondimentDecorator {
    public Milk(Beverage beverage) {
        super(beverage); // 调用父类构造函数
    }

    @Override
    public String getDescription() {
        return beverage.getDescription() + ", Milk";
    }

    @Override
    public double cost() {
        return beverage.cost() + 0.5;
    }
}

class Sugar extends CondimentDecorator {
    public Sugar(Beverage beverage) {
        super(beverage);
    }

    @Override
    public String getDescription() {
        return beverage.getDescription() + ", Sugar";
    }

    @Override
    public double cost() {
        return beverage.cost() + 0.2;
    }
}

public class DecoratorExample {
    public static void main(String[] args) {
        Beverage coffee = new Espresso();
        System.out.println(coffee.getDescription() + " $" + coffee.cost());

        coffee = new Milk(coffee); // add milk
        coffee = new Sugar(coffee); // add sugar
        System.out.println(coffee.getDescription() + " $" + coffee.cost());

        Beverage latte = new Latte();
        latte = new Sugar(new Sugar(new Milk(latte))); // add two sugar and oen milk
        System.out.println(latte.getDescription() + " $" + latte.cost());
    }
}

/*
 * 为什么 CondimentDecorator 需要 abstract String getDescription();
 * 
 * CondimentDecorator 作为装饰器，需要修改 getDescription() 的行为，即它的子类（如 Milk、Sugar）需要
 * 拼接额外的描述。既然 CondimentDecorator 不能提供通用的 getDescription() 实现，就强制子类实现，所以把它声明为
 * abstract。
 * 
 * CondimentDecorator 本身已经隐式继承 了 Beverage 接口，而 Beverage 里面已经定义了 double cost()：所以
 * 所有 CondimentDecorator 的子类必须实现 cost()，否则编译报错。
 * 
 * 结论：
 * CondimentDecorator 不是必须要定义 abstract getDescription();，但这么做有助于代码清晰性：
 * ✅ 定义 abstract getDescription();，强制子类实现
 * ✅ 不定义 abstract cost();，因为 Beverage已经定义了，子类必须实现
 */