#Move pointer approch (Manhatten)

n = 10
arr = [[0] * n for _ in range(n)]

top, bottom =0, n - 1
left, right = 0, n-1
num = 1

while num <= 100:

    # L to R
    for i in range(left, right + 1):
        arr[top][i] = num
        num += 1
    top += 1

    # Top to Bottom
    for i in range(top, bottom + 1):
        arr[i][right] = num
        num += 1
    right -= 1

    # R to L
    for i in range(right, left - 1, -1):
        arr[bottom][i] = num
        num += 1
    bottom -= 1

    # Bottom to Top
    for i in range(bottom, top - 1, -1):
        arr[i][left] = num
        num += 1
    left += 1

# Print
for row in arr:
    for value in row:
        print(f"{value:4}", end="")
    print()