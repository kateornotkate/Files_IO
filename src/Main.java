import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.stream.JsonReader;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        String[] products = {"Хлеб", "Молоко", "Сыр"};
        int[] prices = {50, 100, 250};
        Basket basket = new Basket(products, prices);

        Configuration config = new Configuration();
        config.loadConfig(); //считываем настройки shop.xml;
        File basketFile = new File(config.getSaveName());

        GsonBuilder builder = new GsonBuilder();
        Gson gson = builder.create();

        // для выгрузки корзины в формате json;
        if (config.getLoadFormat().equals("json") && basketFile.exists()) {
            try (JsonReader reader = new JsonReader(new FileReader(config.getLoadName()))) {
                basket = gson.fromJson(reader, Basket.class);
                System.out.println(
                        "Данные прошлой корзины были успешно восстановлены.\n" +
                                "Давайте продолжим покупки :)");
                basket.printCart();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            // для выгрузки корзины в формате txt;
        } else if (config.getLoadFormat().equals("txt") && basketFile.exists()) {
            basket = Basket.loadFromTxtFile(new File(config.getLoadName()));
            System.out.println(
                    "Данные прошлой корзины были успешно восстановлены.\n" +
                            "Давайте продолжим покупки :)");
            basket.printCart();
            // начинаем с пустой корзины, если никаких раннее сохраненных файлов нет;
        } else {
            basket = new Basket(products, prices);
        }

        System.out.println("\nСписок возможных товаров для покупки: ");
        for (int i = 0; i < products.length; i++) {
            System.out.println((i + 1) + ". " + products[i] + " " + prices[i] + " руб/шт");
        }

        while (true) {
            System.out.println("Выберите товар и количество или введите \"end\".");
            String input = scanner.nextLine();
            if (input.equals("end")) {
                // если в настройках сохранения указан формат json;
                if (config.getSafeFormat().equals("json")) {
                    try (FileWriter jsonWriter = new FileWriter(config.getSaveName())) {
                        jsonWriter.write(gson.toJson(basket));
                        break;
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                    // если в настройках сохранения указан формат txt;
                } else if (config.getSafeFormat().equals("txt")) {
                    basket.saveTxt(new File(config.getSaveName()));
                    break;
                    // если ничего не указанно, то не сохраняем корзину;
                } else {
                    break;
                }
            }
            String[] parts = input.split(" ");
            int productNumber = Integer.parseInt(parts[0]) - 1;
            int productCount = Integer.parseInt(parts[1]);
            basket.addToCart(productNumber, productCount);
        }
        basket.printCart();
        if (config.isLog()) {
            ClientLog.exportAsCSV(new File(config.getLogName()));
        }
    }
}