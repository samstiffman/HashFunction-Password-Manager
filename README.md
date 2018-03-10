# Password Manager/Hash Function
Simple java hash function integrated with an SQLite server, I made this as a proof of concept hashing algorithm, it hashes passwords of any length. It is integrated with a database but the database needs to have specific parameters that are detailed in the Database.java file.

The hash function transforms a password into a bit string of length 512, it also uses random number generation when the hashes are created so if multiple users have the same password the hashes stored in the database will still be different. 

To construct a database I used SQLite studio a free SQLite database editor but this can be accomplished using the 
Java SQLite library commands. You can find an SQLite java tutorial at http://www.sqlitetutorial.net/sqlite-java/
This tutorial will teach the basics of using SQL commands in Java.

For the Java to work without editing the code you will need a database with a table named hashedPasswords
this table has to contain 3 columns **_userName_**, **_seed_**, and **_hash_** **_userName_** and **_hash_** must both be some sort of 
text data type TEXT/TINYTEXT/VARCHAR etc _I personally used VARCHAR(128) for hash since each hash has exactly 128 hexidecimal bits stored in a text format_. The **_seed_** column should probably be a BIGINTEGER since it is 17 digits however it would be possible to store it as text if you wanted however the Java would have to be modified so it stores as the correct type.

In order to use my program you will need some sort of SQL database
I personally use SQLite which is an offline SQL database since this project has no need for servers. 

The main method for my program is contained in the SAM512 .java file

If you have any ideas or want to learn more about Cryptography or hashing please comment, Thank You!
