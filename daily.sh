#!/bin/bash
#TODO: run frontend over a number of banking transactions
#save to seperate files
#at end concatenate output files

#path setup.
path=$(pwd)
front=$path/frontend
txn=$path/txns
in=$path/inputs
merged=$txn/merged.trf

#makes transaction file directory if it doesnt exist.
[ -d $txn ] || mkdir $txn

# deletes merged file if there is one
rm -rf $txn/merged.trf

# iterate over every file in the inputs directory
for i in $(ls $in)
do
  $front/bank.exe $front/CurrentBankAccounts.dat $txn/${name}.trf < $in/$i
done;

#iterate over every file in the transactions directory
for i in $( ls txns/ )
do
  cat $txn/$i >> $merged
  echo " " >> $merged #adds a newline to the file because cat doesn't
done;
#TODO: run backend over concatenated file

echo "Implement the script to run the daily system here"
