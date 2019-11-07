# Created by: Yihong Zou
# Date: 2019/10/29
# Usage: Use BlockChair Api to request bitcoin tx details
#        based on tx value, one single request can fetch
#        up to 100 records
# Input: None
# Output: bitcoin_trabaction.csv
# Resource: 
#       To see want kind of request you can make based on different coins:
#           https://github.com/Blockchair/Blockchair.Support/blob/master/API_DOCUMENTATION_EN.md
#       To see details of the bitcoin/trancation request:
#           https://github.com/Blockchair/Blockchair.Support/blob/master/API_DOCUMENTATION_EN.md#link_203

import sys
import json
import os
import datetime
import requests
import csv


def write_data(writer, request_uri):
    response = requests.get(request_uri)
    data = json.loads(response.text)["data"]

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
    # the transaction value range, you may change this list as you want
    tx_range = [199828999 + 80000 * i for i in range(1250)]

    with open('bitcoin_trabaction.csv', 'a') as o:  # open up the output file
        writer = csv.writer(o)  # create a python CSV writer

        #writer.writerow(
        #    ["Block_ID", "Hash", "Date", "Time", "Size", "Weight", "Input_total_usd", "Output_total_usd", "Fee_usd"])

        for i, left in enumerate(tx_range[:-1]):
            # this part of request is for tx value between value A and value B,
            # you may change the limit field below, from 0 to 100
            request_uri_format = "https://api.blockchair.com/bitcoin/transactions?q=input_total_usd({}..{})&limit=100"
            request_uri = request_uri_format.format(left, tx_range[i + 1] - 1)
            print(request_uri)
            write_data(writer, request_uri)

        # this part of request is for tx value above value A,
        # you may change the limit field below, from 0 to 100
        # last_request_uri_format = "https://api.blockchair.com/bitcoin/transactions?q=input_total_usd({}..)&limit=100"
        # last_request_uri = last_request_uri_format.format(tx_range[-1])
        # print(last_request_uri)
        # write_data(writer, last_request_uri)


if __name__ == '__main__':
    main()
