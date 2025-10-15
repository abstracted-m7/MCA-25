
'''
    4. Delete log1.txt and then remove the logs directory.
'''

import os

# Delete log1.txt
os.remove("logs/log1.txt")

# Remove logs directory
os.rmdir("logs")
