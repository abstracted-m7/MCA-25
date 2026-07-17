n = 10

arr = [[0] * n for _ in range(n)]

top, bottom = 0 , n - 1
left, right = 0, n - 1

num = 1

while num <= n * n:

    # i = top (constant), j++
    i = top
    for j in range(left, right + 1):
        arr[i][j] = num
        num += 1
    top += 1

    # j = right (constant), i++
    j = right
    for i in range(top, bottom + 1):
        arr[i][j] = num
        num += 1
    right -= 1

    # i = bottom (constant), j--
    if top <= bottom:
        i = bottom
        for j in range(right, left - 1, -1):
            arr[i][j] = num
            num += 1
        bottom -= 1

    # j = left (constant), i--
    if left <= right:
        j = left
        for i in range(bottom, top - 1, -1):
            arr[i][j] = num
            num += 1
        left += 1

# Print
for row in arr:
    for x in row:
        print(f"{x:4}", end="")
    print()