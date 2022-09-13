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

    // метод сохранения корзины в бинарном формате;
    public void saveBin(File file) throws IOException {
        Basket basket = new Basket(products, prices, amount);
        ObjectOutputStream output = new ObjectOutputStream(new FileOutputStream(file));
        output.writeObject(basket);
        output.close();
    }

    // метод восстановления корзины из бинарного файла;
    public static Basket loadFromBinFile(File file) throws IOException, ClassNotFoundException {
        ObjectInputStream input = new ObjectInputStream(new FileInputStream(file));
        Basket basket = (Basket) input.readObject();
        input.close();
        return basket;
    }
}