#!/bin/bash
#TODO: run frontend over a number of banking transactions
#save to seperate files
#at end concatenate output files
for i in {0..2}
do
  frontend/bank.exe frontend/CurrentBankAccounts.dat ${i}Transactions.trf
done;

#TODO: run backend over concatenated file

echo "Implement the script to run the daily system here"
