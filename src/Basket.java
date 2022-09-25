import java.io.*;

public class Basket {
    private String[] products;
    private int[] prices;
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
        ClientLog.log(productNumber, productCount);
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
        int index = 0;
        int size = 3;
        String[] products = new String[size];
        int[] amount = new int[size];
        int[] prices = new int[size];
        try (BufferedReader br = new BufferedReader(new FileReader(textFile))) {
            String lines;
            while ((lines = br.readLine()) != null) {
                String[] line = lines.split("\\s"); //
                products[index] = line[0];
                amount[index] = Integer.parseInt(line[1]);
                prices[index] = Integer.parseInt(line[3]);
                index++;
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return new Basket(products, prices, amount);
    }
}