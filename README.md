# Simple Expense Tracker

### *For managing budget as a student*

#### Description:

Being in college/university also tends to coincide with the fact that you're going to be away from your family. This means you have a lot more responsibilities that you need to take care of as an adult.  This includes money, and unless you have someone in your family who can handle it for you, you most likely will have to manage it by yourself. It can be hard to do it on paper, and it's 2022 (or whichever year you're reading this file in), so you want to use a software that can help you keep track of your spending and let you manage your budget better. But Excel can be complicated and inconvenient to use, and you don't want to pay a subscription fee, so you wonder if there's a much better way to manage your finance.

And that's where this program comes in. Intended for student use, this program helps you manage your budget with these ***features***:
- Convenient way to enter the amount of money spent, the category it belongs to and the date of the transaction.
- Creates a list that shows your spending of the month, which includes how much money, on what categories, date, and perhaps a brief description if you like.
- Calculates the total amount spent on each category, and the overall sum of money spent for that month.
- Allows you to compare your budget spending with the previous month(s) with a brief report.

As mentioned above, managing finance can be difficult, especially when you're new to adulthood and thus may not realize what your spending habits are. This application is a convenient, simple and straightforward tool to assist you on this matter.

#### User Stories:
- As a user, I want to be able to enter the **amount of money spent** into the list as a **standalone entry**.
- As a user, I want to be able to add a new **budget list** into the list of existing **budget lists**.
- As a user, I want to be able to **set the date** when the money was spent, associated with the entry mentioned in the 1st bullet point, into the list.
- As a user, I want to be able to **categorize** the money spent, associated with the entry mentioned in the 1st bullet point, in the budget list.
- As a user, I want to be able to **write a brief description** on the money spent, associated with the entry mentioned in the 1st bullet point, in the list.
- As a user, I want to be able to **compare two different budget lists** in terms of **total money spent**, **money spent on which categories** and **generate a brief report** highlighting the differences between the two budget lists.
- As a user, I want to be able to see an overall **summary** of my budget list, including the total amount of money spent and how much on each category.


- As a user, I want to be able to save my budget lists with their respective entries into a file.
- As a user, I want to be able to load my budget lists with their respective entries from a file.

# Instructions for Grader

- You can generate the first required event related to adding Xs to a Y by clicking the "Add" button and select "Add a new entry" in the menu that opens afterward. This will lead to a series of prompt that allow you to add an entry onto the current displaying budget list.
- You can generate the second required event related to adding Xs to a Y by clicking the "Add" button and select "Add a new budget list" in the menu that opens afterward. This will lead to a prompt that asks you to input the name of the new budget list, and then adding the budget list into the tracker once a non-empty string has been entered.
- You can locate my visual component by starting up the program. The visual is in the loading splash screen.
- You can save the state of my application by clicking the button labelled "Save" in the application. Additionally, clicking the "Quit" button or exiting the application will make a prompt that asks whether the user want to save or not.
- You can reload the state of my application by starting up the program. The loading process is automatically done on startup.