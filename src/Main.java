import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws IOException, ClassNotFoundException {
        Scanner scanner = new Scanner(System.in);

        String[] products = {"Хлеб", "Молоко", "Сыр"};
        int[] prices = {50, 100, 250};

        Basket basket = new Basket(products, prices);
        File basketFile = new File("basket.bin");

        if (basketFile.exists()) {
            System.out.println("""
                    Данные прошлой корзины были успешно восстановлены.
                    Нажмите ENTER, если хотите продолжить покупки.
                    Если хотите сбросить корзину, введите любое число или символ.""");
            String input = scanner.nextLine().toLowerCase();
            if (input.equals("")) {
                basket = Basket.loadFromBinFile(basketFile);
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
                basket.saveBin(basketFile); // сохранение корзины в текстовый файл;
                break;
            }
            String[] parts = input.split(" ");
            int productNumber = Integer.parseInt(parts[0]) - 1;
            int productCount = Integer.parseInt(parts[1]);
            basket.addToCart(productNumber, productCount);
        }
        basket.printCart();
    }
}