arr = [1, 0, 0, 8]
n = 4
temp = []
count = 0

for i in arr:
    if i != 0:
        temp.append(i)
    else:
        count += 1

for _ in range(count):
    temp.append(0)


print(temp)

'''
    arr = {1, 0, 0, 8}
    temp = { 1, 8, 0, 0}

'''