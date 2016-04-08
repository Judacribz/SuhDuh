#!/bin/bash

#path setup.
path=$(pwd)
front=$path/frontend
back=$path/backend
txn=$path/txns
in=$path/inputs
acc=$path/masterBankAccounts
[ -d $acc ] || mkdir $acc


#makes Master bank accounts directory for each day.
for i in {0..4}
do
    ./daily.sh $in/input${i}
 
    cp $back/MasterBankAccounts.dat $acc/MasterBankAccounts.day${i}
done
