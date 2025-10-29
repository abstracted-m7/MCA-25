try:
    num1 = float(input("Enter the first number: "))
    num2 = float(input("Enter the second number: "))
    result = num1 / num2
    print(f"The result of division is: {result}")

    #For check IndexError
    my_list = [1,2,3]
    print(my_list[4])
    
except ZeroDivisionError:
    print("Error: Division by zero is not allowed.")
    
except FileNotFoundError:
    print("Error: Invalid file access (e.g., file not found).")
    
except ValueError:
    print("Error: Please enter valid numbers.")
    
except (EOFError, KeyboardInterrupt):
    print("Input interrupted by user.")

except IndexError: #array/list..etc based
    print("Error: Index out of bound.")
    
except Exception as e:
    print(f"An unexpected error occurred: {e}")
    
finally:
    print("Program execution completed.")
