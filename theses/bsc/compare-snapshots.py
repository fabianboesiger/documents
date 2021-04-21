import csv

base_file = "new_snapshot_base.csv"
variant_file = "new_snapshot_flyweight.csv"

base_rows = []
variant_rows = []

def to_int(string):
    return int(string.split()[0].replace("’", ""))

with open(base_file) as csvfile:
    spamreader = csv.reader(csvfile, delimiter=",", quotechar="\"")
    i = 0
    for row in spamreader:
        i += 1
        if i == 1:
            continue
        if row[0][0] != " " and not "sorts$" in row[0]:
            base_rows.append([row[0], to_int(row[3])])

with open(variant_file) as csvfile:
    spamreader = csv.reader(csvfile, delimiter=",", quotechar="\"")
    i = 0
    for row in spamreader:
        i += 1
        if i == 1:
            continue
        if row[0][0] != " " and not "sorts$" in row[0]:
            variant_rows.append([row[0], to_int(row[3])])

result_rows = []

for [base_name, base_allocated] in base_rows:
    for [variant_name, variant_allocated] in variant_rows:
        if base_name == variant_name:
            misses = variant_allocated
            hits = base_allocated - misses
            if hits == 0 and misses == 0:
                continue
            hit_percentage = hits / (hits + misses) * 100
            #print(base_name, base_allocated, variant_allocated, hit_percentage)
            result_rows.append([base_name, hit_percentage])
            break

result_rows.sort(key=lambda row: row[1], reverse=True)

with open("snapshot_flyweight_vs_base.csv", "w") as csvfile:
    spamwriter = csv.writer(csvfile, delimiter=",")
    spamwriter.writerow(["File Name", "Hit Percentage"])
    for row in result_rows:
        spamwriter.writerow(row)
