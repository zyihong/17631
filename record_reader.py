# Date: 2019/11/07
# Usage: Read in json files and turn into csv file
# Input: json files
# Output: csv file

import sys
import json
import os
import datetime
import requests
import csv


def write_data(writer, data):

    if not data:
        return

    # you may add any field here
    for record in data:
        block_id = record["block_id"]
        hash = record["hash"]
        date = record["date"]
        time = record["time"]
        size = record["size"]
        weight = record["weight"]
        input = record["input_total_usd"]
        output = record["output_total_usd"]
        fee = record["fee_usd"]

        row = [block_id, hash, date, time, size, weight, input, output, fee]
        writer.writerow(row)


def main():
    with open('bitcoin_fee_high.csv', 'w') as o:  # open up the output file
        writer = csv.writer(o)  # create a python CSV writer

        writer.writerow(
           ["Block_ID", "Hash", "Date", "Time", "Size", "Weight", "Input_total_usd", "Output_total_usd", "Fee_usd"])

        files = os.listdir('./bitcoin_fee/')
        print(files)

        for file in files:
            print(file)
            with open('./bitcoin_fee/{}'.format(file), 'r') as json_file:
                for line in json_file:
                    # print(line)
                    data = json.loads(line)["data"]
                    write_data(writer, data)


if __name__ == '__main__':
    main()