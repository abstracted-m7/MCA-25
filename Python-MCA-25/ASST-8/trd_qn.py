
'''
    3. Create a directory called logs, and inside it:
        a. create a file log1.txt.
        b. Write log entries into it.
'''

import os

# Create logs directory
os.makedirs("logs", exist_ok=True)

# Create log1.txt and write log entries
with open("logs/log1.txt", "w") as log_file:
    log_file.write("Log Entry 1\n")
    log_file.write("Log Entry 2\n")
