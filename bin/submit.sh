#!/bin/bash

# This program is used for spark-submiting


username='hadoop'
password='hadoop'
hostname='10.214.208.11'

function putfile() {
    sshpass -p $password scp $1 $username@$hostname:/tmp
}

for file in 'target/scala-2.10/smithwaterman_2.10-1.0.jar' 'blosum' 'dbseqs' 'queryseq'; do
    putfile $file
done

sshpass -p $password ssh $username@$hostname 'cd /tmp && /usr/local/spark/bin/spark-submit --class SmithWater --master spark://node1:7077 smithwaterman_2.10-1.0.jar blosum queryseq dbseqs 8 5'