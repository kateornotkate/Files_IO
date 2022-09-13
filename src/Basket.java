import java.io.*;

public class Basket implements Serializable {
    private static String[] products;
    private static int[] prices;
    private final int[] amount;

    // конструктор объекта;
    public Basket(String[] products, int[] prices) {
        this.products = products;
        this.prices = prices;
        this.amount = new int[products.length];
    }

    // конструктор объекта для метода loadFromBinFile;
    public Basket(String[] products, int[] prices, int[] amount) {
        this.products = products;
        this.prices = prices;
        this.amount = amount;
    }

    public void addToCart(int productNumber, int productCount) {
        amount[productNumber] += productCount;
    }

    public void printCart() {
        int sum = 0;
        System.out.println("Ваша корзина: ");
        for (int i = 0; i < amount.length; i++) {
            if (amount[i] != 0) {
                System.out.println(products[i] + " " + amount[i] + " шт " + prices[i]
                        + " руб/шт " + (amount[i] * prices[i]) + " руб в сумме.");
                sum += amount[i] * prices[i];
            }
        }
        System.out.println("Итого: " + sum + " руб.");
    }

    //  метод сохранения корзины в бинарном формате;
    public void saveBin(File file) {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(file))) {
            out.writeObject(this);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    //  метод восстановления корзины из бинарного файла;
    public static Basket loadFromBinFile(File file) { // метод для загрузки корзины из бинарного файла
        Basket basket = null;
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(file))) {
            basket = (Basket) in.readObject();
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
        return basket;
    }
}