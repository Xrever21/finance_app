import java.util.HashMap;
import java.util.Map;

class Category {
    private String name;
    private double budget;
    private double expenses;

    public Category(String name, double budget) {
        this.name = name;
        this.budget = budget;
        this.expenses = 0;
    }

    public void addExpense(double amount) {
        expenses += amount;
    }

    public double remainingBudget() {
        return budget - expenses;
    }

    public String getName() {
        return name;
    }

    public double getBudget() {
        return budget;
    }

    public double getExpenses() {
        return expenses;
    }
}

class Wallet {
    private double balance;
    private Map<String, Category> categories;

    public Wallet() {
        balance = 0;
        categories = new HashMap<>();
    }

    public void addIncome(double amount) {
        balance += amount;
    }

    public void addExpense(double amount, String categoryName) {
        if (categories.containsKey(categoryName)) {
            balance -= amount;
            categories.get(categoryName).addExpense(amount);
        } else {
            System.out.println("Категория " + categoryName + " не найдена.");
        }
    }

    public void addCategory(Category category) {
        categories.put(category.getName(), category);
    }

    public double getBalance() {
        return balance;
    }

    public Map<String, Category> getCategories() {
        return categories;
    }
}

class User {
    private String login;
    private String password;
    private Wallet wallet;

    public User(String login, String password) {
        this.login = login;
        this.password = password;
        this.wallet = new Wallet();
    }

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }

    public Wallet getWallet() {
        return wallet;
    }
}

class FinanceSystem {
    private Map<String, User> users;
    private User currentUser;

    public FinanceSystem() {
        users = new HashMap<>();
        currentUser = null;
    }

    public void registerUser(String login, String password) {
        if (users.containsKey(login)) {
            System.out.println("Пользователь с таким логином уже существует.");
        } else {
            users.put(login, new User(login, password));
            System.out.println("Пользователь зарегистрирован.");
        }
    }

    public void authenticate(String login, String password) {
        if (users.containsKey(login) && users.get(login).getPassword().equals(password)) {
            currentUser = users.get(login);
            System.out.println("Добро пожаловать, " + login + "!");
        } else {
            System.out.println("Неверный логин или пароль.");
        }
    }

    public void addIncome(double amount) {
        if (currentUser != null) {
            currentUser.getWallet().addIncome(amount);
            System.out.println("Доход " + amount + " добавлен.");
        } else {
            System.out.println("Пользователь не авторизован.");
        }
    }

    public void addExpense(double amount, String category) {
        if (currentUser != null) {
            currentUser.getWallet().addExpense(amount, category);
            System.out.println("Расход " + amount + " добавлен в категорию " + category + ".");
        } else {
            System.out.println("Пользователь не авторизован.");
        }
    }

    public void addCategoryToWallet(String categoryName, double budget) {
        if (currentUser != null) {
            Category category = new Category(categoryName, budget);
            currentUser.getWallet().addCategory(category);
            System.out.println("Категория " + categoryName + " с бюджетом " + budget + " добавлена.");
        } else {
            System.out.println("Пользователь не авторизован.");
        }
    }

    public void showReport() {
        if (currentUser != null) {
            Wallet wallet = currentUser.getWallet();
            System.out.println("Общий баланс: " + wallet.getBalance());
            System.out.println("Отчет по категориям:");
            for (Map.Entry<String, Category> entry : wallet.getCategories().entrySet()) {
                Category category = entry.getValue();
                System.out.println("Категория: " + category.getName() +
                        ", Бюджет: " + category.getBudget() +
                        ", Потрачено: " + category.getExpenses() +
                        ", Осталось: " + category.remainingBudget());
            }
        } else {
            System.out.println("Пользователь не авторизован.");
        }
    }
}

public class Main {
    public static void main(String[] args) {
        FinanceSystem financeSystem = new FinanceSystem();

        // Пример использования
        financeSystem.registerUser("user1", "password1");
        financeSystem.authenticate("user1", "password1");

        // Добавляем категории
        financeSystem.addCategoryToWallet("Еда", 4000);
        financeSystem.addCategoryToWallet("Развлечения", 3000);

        // Добавляем доходы и расходы
        financeSystem.addIncome(20000);
        financeSystem.addIncome(40000);
        financeSystem.addExpense(300, "Еда");
        financeSystem.addExpense(500, "Еда");
        financeSystem.addExpense(3000, "Развлечения");

        // Показываем отчет
        financeSystem.showReport();
    }
}
