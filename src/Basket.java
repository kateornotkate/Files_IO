import java.io.*;

public class Basket {
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

    //  saveTxt(File textFile) - метод сохранения корзины в текстовый файл;
    public void saveTxt(File textFile) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(textFile))) { //  try-with-resources Statement
            for (int i = 0; i < products.length; i++) {
                bw.write(products[i] + " " + amount[i] + " шт " + prices[i]
                        + " руб/шт " + (amount[i] * prices[i]) + " руб в сумме.\n");
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    //  метод восстановления объекта корзины из текстового файла, в который ранее была она сохранена;
    public static Basket loadFromTxtFile(File textFile) {
        int productIndex = 0;
        int[] amount = new int[products.length];
        try (BufferedReader br = new BufferedReader(new FileReader(textFile))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] lines = line.split("\\s"); //
                amount[productIndex] = Integer.parseInt(lines[1]);
                productIndex++;
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return new Basket(products, prices, amount);
    }
}