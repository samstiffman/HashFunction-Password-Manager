# passwordManager
Simple java hash function integrated with an SQLite server, I made this as a proof of concept hashing algorithm, it hashes passwords of any length 
into a bit string of length 512, it also uses random number generation when the hashes are created so if multiple users had the same
password they will have different hashes.

The .CLASS files are the compiled versions of the .java files so if you want to test my password Manager it would be easiest to just download the .java files and/or copy and paste them into your javaIDE/textEditor. In order for it you will need some sort of SQL database
I personally use SQLite which is an offline SQL database since this project has no need for servers. 

The main method for my program is contained in the SAM512 .java file
