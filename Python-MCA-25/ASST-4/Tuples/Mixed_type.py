# 3. Create tuples - Mixed types, Single-element, Nested tuples 


# mixed type
mix_tup = (1, 1.30, "python")
print(f"Mixed tuple: {mix_tup}")
print(f"Type: {type(mix_tup)}")
print(f"Length: {len(mix_tup)}")

# Signle-element tuple
sig_tup = (46,)
print(f"MixSingleed tuple: {mix_tup}")
print(f"Type: {type(mix_tup)}")
print(f"Length: {len(mix_tup)}")


# nested tuple

nested_basic = ((1, 2), (3, 4), (5, 6))
print(f"Basic nested tuple: {nested_basic}")
print(f"Type: {type(nested_basic)}")
print(f"Length (outer tuple): {len(nested_basic)}")
print(f"Length of first inner tuple: {len(nested_basic[0])}")