import java.util.*;
import java.util.stream.Collectors;

public class Main {
    private static long userId = 0;
    private static long orderId = 0;

    public static void main(String[] args) {


        User user1 = new User(userId++, "Vadim", "Kozak", "Kiev", 10000);
        User user2 = new User(userId++, "Ivan", "Petrov", "Kiev", 50000);
        User user3 = new User(userId++, "Ivan", "Ivanov", "Kiev", 1000);
        User user4 = new User(userId++, "Anna", "tree", "a", 300);
        User user5 = new User(userId++, "Igor", "Ivanov", "Kiev", 500);

        Order order1 = new Order(orderId++, 1000, Currency.UAN, "b", "qwerty", user1);
        Order order2 = new Order(orderId++, 1600, Currency.UAN, "B", "qwerty", user2);
        Order order3 = new Order(orderId++, 5000, Currency.USD, "A", "qwerty", user3);
        Order order4 = new Order(orderId++, 500, Currency.UAN, "a", "qwerty", user4);

        List<Order> listOrder = new ArrayList<>();
        listOrder.add(order1);
        listOrder.add(order3);
        listOrder.add(order4);
        listOrder.add(order2);


        printFirstTask(listOrder);
        printSecondTask(listOrder);
        printThirdTask(listOrder);
    }

    private static void printFirstTask(List<Order> list) {
        //-отсортируйте список за ценой заказа по убыванию
        System.out.println("сортировка за ценой заказа по убыванию");
        list.stream()
                .sorted(Comparator.comparing(Order::getPrice).reversed())
                .forEach(System.out::println);
        //-отсортируйте список за ценой заказа по возрастанию и за городом пользователя
        System.out.println("сортировка а ценой заказа по возрастанию и за городом пользователя");
        list.stream()
                .sorted(Comparator.comparing(Order::getPrice)
                        .thenComparing((o1, o2) -> o1.getUser().getCity().compareTo(o2.getUser().getCity())))
                .forEach(System.out::println);
        // -отсортируйте список за наименованием товара, идентификатором заказа, и городом пользователя
        System.out.println("сортировка за наименованием товара, идентификатором заказа, и городом пользователя");
        list.stream()
                .sorted(Comparator.comparing(Order::getItemName)
                        .thenComparing(Order::getId)
                        .thenComparing((o1, o2) -> o1.getUser().getCity().compareTo(o2.getUser().getCity()))).
                forEach(System.out::println);
    }

    private static void printSecondTask(List<Order> list) {

        System.out.println("удалить дублированные данные со списка");
        list.stream().distinct().collect(Collectors.toList()).forEach(System.out::println);
        System.out.println("удалить объекты, где цена меньше 1500");
        list.removeIf(order -> order.getPrice() < 1500);
        list.forEach(System.out::println);
        System.out.println("разделить список на 2 списка - заказы в долларах и в гривнах");
        Map<Currency, List<Order>> mapOfUniqueCurrency = list.stream().collect(Collectors.groupingBy(Order::getCurrency));
        displayMap(mapOfUniqueCurrency);
        System.out.println("разделить список на столько списков, сколько уникальных городов в User");
        Map<String, List<Order>> mapOfUniqueCity = list.stream().collect(Collectors.groupingBy(o -> o.getUser().getCity()));
        displayMap(mapOfUniqueCity);
    }

    private static <K, V> void displayMap(Map<K, V> map) {
        map.forEach((k, v) -> System.out.println("Key: " + k + ". \nValue: " + v + ""));
    }

    private static void printThirdTask(List<Order> list) {
        Set<Order> orderSet = new TreeSet<>();
        orderSet.addAll(list);
        System.out.println("содержит ли сет заказ, где фамилия пользователя - \\“Petrov\\”");
        orderSet.stream().filter(order -> order.getUser().getLastName().equals("Petrov")).forEach(System.out::println);

        System.out.println("удалите заказы в USD");
        orderSet.removeIf(order -> order.getCurrency() == Currency.USD);
        orderSet.forEach(System.out::println);
    }

}
