import asyncio
from typing import Callable, Dict, List

class CharNotificationSystem:
    def __init__(self):
        self.users: Dict[str, List[Callable[[str], None]]] = {}
        self.lock = asyncio.Lock() # ensure thread-safe access to shared data 
        
    async def register_user(self, username: str, callback: Callable[[str], None]):
        """register a user to receive notifications.

        Args:
            username (str): name of the user
            callback (Callable[[str], None]): callback function to hndle notifications
        """
        async with self.lock:
            if username not in self.users:
                self.users[username] = []
            self.users[username].append(callback)
            print(f"User '{username}' registered. ")
            
    async def unregister_user(self, username:str, callback: Callable[[str], None]):
        """Unregister a user from receiving notification

        Args:
            username (str): name of the user
            callback (Callable[[str], None]): Callback function to remove
        """
        async with self.lock:
            if username in self.users and callback in self.users[username]:
                self.users[username].remove(callback)
                print(f"User '{username}' unregistered.")
                if not self.users[username]: # remove use if no callback left
                    del self.users[username]
    
    async def send_message(self, sender, recipient, message):
        """Send a message to a recipient, triggering their notification.

        Args:
            sender (_type_): name of the sender
            recipient (_type_): name of the recipient
            message (_type_): the message content
        """
        
        notification = f"New message from {sender}: {message}"
        async with self.lock:
            if recipient in self.users:
                for callback in self.users[recipient]:
                    callback(notification) # Trigger all callbacks for the recipient
                print(f"Notification sent to '{recipient}'.")
            else:
                print(f"User '{recipient}' is not registered. No notification sent. ")

if __name__ == "__main__":
    async def main():
        def user_callback_1(notification: str):
            print(f"User1 received: {notification}")
        def user_callback_2(notification: str):
            print(f"User2 reveived: {notification}")
        
        # Create chat notification system
        chat_notifier = CharNotificationSystem()
        
        # Register two users with their respective callbacks
        await chat_notifier.register_user("user1", user_callback_1)
        await chat_notifier.register_user("user_2", user_callback_2)
            
        # Simulate sending messages
        await chat_notifier.send_message("user2", "user1", "Hello, big kitty!")
        await asyncio.sleep(1) # simulate delay
        await chat_notifier.send_message("user1", "user2", "Hi kitty, how are you?")
        
        # Unregister user1 after some time
        await asyncio.sleep(2)
        await chat_notifier.unregister_user("user1", user_callback_1)
        
        # Attemtp to sent a message to an unregistered user
        await chat_notifier.send_message("user2", "user1", "Are you still there, kitty?")
    asyncio.run(main())