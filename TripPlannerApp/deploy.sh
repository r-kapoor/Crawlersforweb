#!/bin/bash

cd /home/ec2-user/Crawlersforweb/TripPlannerApp

now=`date`
echo "Start at "$now
git pull
npm install
grunt js-task
pm2 restart 0
now=`date`
echo "Ends at "$now
