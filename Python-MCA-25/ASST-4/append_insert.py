
# 2.1. append(), insert() operations.

org_list = list(range(1, 6))
print(f"The original list: {org_list}")

#append 20
org_list.append(20)
print(f"After append: {org_list}")

#insert 54 in 2nd position
org_list.insert(1, 54)
print(f"Insert 54 at idx 2: {org_list}")