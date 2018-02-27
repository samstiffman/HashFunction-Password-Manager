# passwordManager
Simple java hash function integrated with an SQLite server, I made this as a proof of concept hashing algorithm, it hashes passwords of any length 
into a bit string of length 512, it also uses random number generation when the hashes are created so if multiple users had the same
password they will have different hashes.

In order to use my program you will need some sort of SQL database
I personally use SQLite which is an offline SQL database since this project has no need for servers. 

The main method for my program is contained in the SAM512 .java file
