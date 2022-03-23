# ets-demo
Demo spring boot project for ets

==========================================================================================================================

This project is a spring boot app which implements the following requirements:

 1.       Create a handler that accepts a userId, transactionDate, transactionAmount from client. transactionAmount could be 
 		negative (in case of withdrawal) or positive (in case of deposit).
	a. Store userId, transactionDate in Table A.
	b. Store transactionAmount in Table B.
	c. Access Table C with userId and Store totalAmount as previousAmount + transactionAmount.

Take these into considerations.
Table A information must be persisted always.
Persistence of Table B and Table C are dependent on each other.

2.       Create a handler to retrieve following information based on userId.
	a. Recent transactionDate
	b. Last transactionAmount
	c. totalAmount

3.       Create a simple UI for the handlers described above.

The database is an in-memory H2 database which is populated with accounts for user_1, user_2, user_3, user_4, and user_5
when the application starts.  The app runs on localhost:8080 with the command mvn spring-boot:run.  There are 2 web pages:
1 - AccountInfo shows the user id, balance, last transaction timestamp, and last transaction amount for a specified user_id.
    It is accessed as http://localhost:8080/account/{userId}.  Ex: http://localhost:8080/account/user_1
    Any error 9such as invalid userid) is displayed on the web page.  This web page is A Thymeleaf template.
2 - DoTransaction takes a user id and transaction amount (positive for deposit, negative for withdrawl) and attempts to
    execute it on the server.  The web page is accessed as http://localhost:8080/doTransaction.  It sends the data to the
    server using AJAX.  the server returns the new balance or an error message (such as insufficient funds).  the
    result is displayed as an alert.  You can use AccountInfo to see the transaction.

Note that the database was implemented as required, but I would have done it differently.  I would have used just 2 tables:
Account ; user_id and total_amount
Account_transactions: user_id, transaction_timestamp, transaction_amount
There is no real need fo a table containing just the user_id and transaction_date.

Also note that the transaction data (date and amount) was not implemented as a lazily loaded set on the account model.
This was done so we could pull the latest transaction as one row from the database, so there was no need to pull all
the transactions for one user in.

Lastly, foreign keys were not implemented for the transaction and transaction date tables just to keep things simple.
If they were, the user_id field would have been replaced with an AccountModel field and annotated with @MonyToOne
specifying user_id as the join column.
