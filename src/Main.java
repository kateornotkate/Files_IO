import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.stream.JsonReader;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws IOException {
        Scanner scanner = new Scanner(System.in);

        String[] products = {"Хлеб", "Молоко", "Сыр"};
        int[] prices = {50, 100, 250};

        Basket basket = new Basket(products, prices);
        File basketFile = new File("basket.json");

        GsonBuilder builder = new GsonBuilder();
        Gson gson = builder.create();

        if (basketFile.exists()) {
            System.out.println("""
                    Данные прошлой корзины были успешно восстановлены.
                    Нажмите ENTER, если хотите продолжить покупки.
                    Если хотите сбросить корзину, введите любое число или символ.""");
            String input = scanner.nextLine().toLowerCase();
            if (input.equals("")) {
                try (JsonReader reader = new JsonReader(new FileReader(basketFile))) {
                    basket = gson.fromJson(reader, Basket.class);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            } else {
                basket = new Basket(products, prices);
                System.out.println("Ваша корзина пуста. Добавьте необходимые товары.");
            }
        } else {
            basket = new Basket(products, prices);
        }

        System.out.println("Список возможных товаров для покупки: ");
        for (int i = 0; i < products.length; i++) {
            System.out.println((i + 1) + ". " + products[i] + " " + prices[i] + " руб/шт");
        }

        while (true) {
            System.out.println("Выберите товар и количество или введите \"end\".");
            String input = scanner.nextLine();
            if (input.equals("end")) {
                try (FileWriter file = new FileWriter(basketFile)) {
                    file.write(gson.toJson(basket));
                    break;
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
            String[] parts = input.split(" ");
            int productNumber = Integer.parseInt(parts[0]) - 1;
            int productCount = Integer.parseInt(parts[1]);
            basket.addToCart(productNumber, productCount);
        }
        basket.printCart();
        ClientLog.exportAsCSV(new File("log.csv"));
    }
}