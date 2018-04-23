PLEASE START THE PROGRAM ONLY FROM TERMINAL (NOT IDE, BECAUSE OF ARGUMENTS)

To start from terminal go to the directory Task_3

javac -d out/production/Task_3/ -cp lib/*.jar src/*.java

Please add 2 argument by running the program,
first argument will set word, which you choosed to search for,
the second one - how often will the webpages checked (PLEASE GIVE TIME IN MINUTES!)
for example if you choose the word "elephant" and you want to check the websites every 5 minutes,
your next command will look like

java -cp out/production/Task_3/:lib/jsoup-1.11.3.jar Main elephant 5

You will find the output in the file out.txt in Task_3/files/out.txt