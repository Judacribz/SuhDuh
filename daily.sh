#!/bin/bash

#path setup.
path=$(pwd)
front=$path/frontend
back=$path/backend
txn=$path/txns
in=$1
merged=$back/Transactions.trf
current=CurrentBankAccounts.dat

#makes transaction file directory if it doesnt exist.
[ -d $txn ] || mkdir $txn

# deletes merged file if there is one
rm -rf $merged

# iterate over every file in the inputs directory
for i in $(ls $in)
do
echo "ITS HERE LOOK $i"
  $front/bank.exe $front/CurrentBankAccounts.dat $txn/${i}.trf < $in/$i
done;

#iterate over every file in the transactions directory
for i in $( ls txns/ )
do
  cat $txn/$i >> $merged
  echo " " >> $merged #adds a newline to the file because cat doesn't
done;

#TODO: get backend to parse transactions properly

cd $back
ant run
cd $path
cp $back/$current $front/$current
