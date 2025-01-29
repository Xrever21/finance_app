class Wallet:
    def __init__(self):
        self.balance = 0.0
        self.transactions = []
        self.budgets = {}

    def add_income(self, category, amount):
        self.transactions.append({"type": "income", "category": category, "amount": amount})
        self.balance += amount

    def add_expense(self, category, amount):
        if self.balance >= amount:
            self.transactions.append({"type": "expense", "category": category, "amount": amount})
            self.balance -= amount
        else:
            print("Ошибка: недостаточно средств в кошельке.")
