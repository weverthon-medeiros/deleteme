### Preparation  questions?
-> What's version of Java?
-> Are you using any framework, Spring for instance?
-> This Service should support multithread?
-> Is it possible to have more than one person with the  same first and last name?

------

-> Filename different from Class name;

-> Are you using Spring at all in this project? this could be annotated with @Service or @Component;

-> The Database connection details shouldn't be in that class, mainly the credentials; But, if for some reason you want, those should be private and Static.


### getSecretSantaPairing 
-> Instead of printing, I'd log, using log4j or logback;
-> I'd separate the Database operations in a separate class, a DAO, for instance, trying to keep aligned with the Single Responsibility principle;
-> I'd configure the database through Spring datasource or JNDI Lookup.
-> But if you still want to yuse Statement, I'd use the try-with-resources from java 7, `try (Connection con = Driver....)`, but if you don't  want you need to move the closure of statement and connection  to the finally  block;
-> The comment on line 52 is misleading, there is  no global `alllist` there.
-> I'd use an ORM framework such as Hibernate, because this is a simple scenario where we can leverage the ORM;
-> The Exception should be handled properly, logging and depending on the business, throwing up a custom/threaded exception.
OBS -> Closing the statement would close the result set;


### getPairings
-> As we know that each person will be a Santa and a Receipient, I'd create the pairings list with a defined size (using names size), to avoid the ArrayList expansion process; List<String> pairings = new ArrayList<>();
-> When drawing a Recipient, we'd need to check if  this is the Santa, To make sure that they are different;
->  I'd separate the Recipient Draw in a separete method;



