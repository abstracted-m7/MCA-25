'''
    1. Create a file data.txt and copy its content into backup.txt.
'''

# Create data.txt and write some sample content
with open("data.txt", "w") as f:
    f.write("This is the original data.\n")

# Copy content from data.txt to backup.txt
with open("data.txt", "r") as src, open("backup.txt", "w") as dst:
    dst.write(src.read())
