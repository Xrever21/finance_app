import json
import os
from app.finance_app import FinanceApp

if __name__ == "__main__":
    app = FinanceApp()
    print("Приложение управления финансами запущено...")
    app.register_user("test_user", "password123")
    user = app.login("test_user", "password123")
    if user:
        user.wallet.add_income("Зарплата", 50000)
        user.wallet.add_expense("Еда", 3000)
        user.wallet.set_budget("Еда", 5000)
        print(user.wallet.get_summary())
    app.save_data()
