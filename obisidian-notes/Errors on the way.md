
1. White-able error 404 page when opening h2 console. 
**Solution**: Issue was the dependency changed. Found solution at: https://www.reddit.com/r/SpringBoot/comments/1panq2p/h2_console_returns_404/ 
2. org.h2.jdbc.JdbcSQLSyntaxErrorException: Column "P1_0.REGISTER_DATE" not found; SQL   statement:  
**Solution**: Rename the attribute from registerDate to registeredDate  