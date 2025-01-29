import json
import os
from app.user import User
from app.wallet import Wallet

class FinanceApp:
    def __init__(self):
        self.users = {}
        self.load_data()

    def register_user(self, username, password):
        if username in self.users:
            print("Ошибка: пользователь уже существует.")
        else:
            self.users[username] = User(username, password)
            self.users[username].wallet = Wallet()
            print(f"Пользователь {username} успешно зарегистрирован.")

    def login(self, username, password):
        user = self.users.get(username)
        if user and user.password == password:
            print(f"Успешный вход. Добро пожаловать, {username}!")
            return user
        print("Ошибка авторизации.")
        return None

    def save_data(self):
        with open("finance_data.json", "w") as f:
            json.dump({username: {"password": user.password, "wallet": user.wallet.__dict__} for username, user in self.users.items()}, f)

    def load_data(self):
        if os.path.exists("finance_data.json"):
            with open("finance_data.json", "r") as f:
                data = json.load(f)
                for username, user_data in data.items():
                    user = User(username, user_data["password"])
                    user.wallet = Wallet()
                    user.wallet.__dict__ = user_data["wallet"]
                    self.users[username] = user
