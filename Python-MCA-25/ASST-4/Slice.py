
# 2.4. lice the list (e.g., first 5 elements, every second element).

org_list = list(range(1, 21))

#print list
print(f"The list is: {org_list}")

#first 5 element
print(f"First 5 element: {org_list[:5]}")

#last 5 element
print(f"Last 5 element: {org_list[-5:]}")

#every second element
print(f"Every Second element: {org_list[1::2]}")

#ELement from idx 2 to 7
print(f"Elements idx 2 to 7: {org_list[2:8]}")