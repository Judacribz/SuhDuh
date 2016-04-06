#!/bin/bash
#TODO: run frontend over a number of banking transactions
#save to seperate files
#at end concatenate output files

#path setup.
path=$(pwd)
front=$path/frontend
txn=$path/txns
in=$path/inputs

#makes transaction file directory if it doesnt exist.
[ -d $txn ] || mkdir $txn


for i in {0..2}
do
  $front/bank.exe $front/CurrentBankAccounts.dat $txn/${i}Transactions.trf < $in/input${i}.in 
done;

#TODO: run backend over concatenated file

echo "Implement the script to run the daily system here"
