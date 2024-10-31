interface Shape {
    void draw();
    double calculateArea();
}

abstract class ShapeFactory {
    public abstract Shape createShape();
}

class RectangleFactory extends ShapeFactory {
    @Override
    public Shape createShape() {
        return new Rectangle(5, 10);
    }
}

class CircleFactory extends ShapeFactory {
    @Override
    public Shape createShape() {
        return new Circle(7);
    }
}

class Rectangle implements Shape {
    private double width;
    private double height;

    public Rectangle(double width, double height) {
        this.width = width;
        this.height = height;
    }

    @Override
    public void draw() {
        System.out.println("Desenhando um Retângulo");
    }

    @Override
    public double calculateArea() {
        return width * height;
    }
}

class Circle implements Shape {
    private double radius;

    public Circle(double radius) {
        this.radius = radius;
    }

    @Override
    public void draw() {
        System.out.println("Desenhando um Círculo");
    }

    @Override
    public double calculateArea() {
        return Math.PI * radius * radius;
    }
}

abstract class ShapeDecorator implements Shape {
    protected Shape decoratedShape;

    public ShapeDecorator(Shape decoratedShape) {
        this.decoratedShape = decoratedShape;
    }

    public void draw() {
        decoratedShape.draw();
    }

    public double calculateArea() {
        return decoratedShape.calculateArea();
    }
}

class BorderDecorator extends ShapeDecorator {
    public BorderDecorator(Shape decoratedShape) {
        super(decoratedShape);
    }

    @Override
    public void draw() {
        decoratedShape.draw();
        System.out.println("Adicionando borda à forma.");
    }
}

class ColorDecorator extends ShapeDecorator {
    private String color;

    public ColorDecorator(Shape decoratedShape, String color) {
        super(decoratedShape);
        this.color = color;
    }

    @Override
    public void draw() {
        decoratedShape.draw();
        System.out.println("Aplicando cor " + color + " à forma.");
    }
}

interface AreaCalculationStrategy {
    double calculateArea(Shape shape);
}

class RectangleAreaStrategy implements AreaCalculationStrategy {
    @Override
    public double calculateArea(Shape shape) {
        if (shape instanceof Rectangle) {
            return shape.calculateArea();
        }
        throw new IllegalArgumentException("Shape não suportado.");
    }
}

class CircleAreaStrategy implements AreaCalculationStrategy {
    @Override
    public double calculateArea(Shape shape) {
        if (shape instanceof Circle) {
            return shape.calculateArea();
        }
        throw new IllegalArgumentException("Shape não suportado.");
    }
}

class AreaCalculator {
    private AreaCalculationStrategy strategy;

    public void setStrategy(AreaCalculationStrategy strategy) {
        this.strategy = strategy;
    }

    public double calculateArea(Shape shape) {
        return strategy.calculateArea(shape);
    }
}

public class Main {
    public static void main(String[] args) {
        // Factory para criar formas
        ShapeFactory rectangleFactory = new RectangleFactory();
        Shape rectangle = rectangleFactory.createShape();

        ShapeFactory circleFactory = new CircleFactory();
        Shape circle = circleFactory.createShape();

        // Decoradores para adicionar estilos
        Shape decoratedRectangle = new BorderDecorator(new ColorDecorator(rectangle, "Azul"));
        decoratedRectangle.draw();

        Shape decoratedCircle = new BorderDecorator(new ColorDecorator(circle, "Vermelho"));
        decoratedCircle.draw();

        // Estratégias para cálculo da área
        AreaCalculator areaCalculator = new AreaCalculator();
        
        // Definindo estratégia e calculando área do Retângulo
        areaCalculator.setStrategy(new RectangleAreaStrategy());
        System.out.println("Área do Retângulo: " + areaCalculator.calculateArea(decoratedRectangle));

        // Definindo estratégia e calculando área do Círculo
        areaCalculator.setStrategy(new CircleAreaStrategy());
        System.out.println("Área do Círculo: " + areaCalculator.calculateArea(decoratedCircle));
    }
}
