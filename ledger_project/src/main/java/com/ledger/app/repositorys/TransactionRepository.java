package com.ledger.app.repositorys;

import com.ledger.app.models.Transaction;
import com.ledger.app.models.TransactionToAdd;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class TransactionRepository {
    private final JdbcTemplate jdbcTemplate;

    // Database columns name
    private final String TABLE = "transactions";
    private final String COLUMN_ID = "id";
    private final String COLUMN_SENDER = "sender";
    private final String COLUMN_RECIPIENT = "recipient";
    private final String COLUMN_TRANSACTION_VALUE = "transaction_value";
    private final String COLUMN_SOFT_DELETE = "soft_delete";

    @Autowired
    public TransactionRepository(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    /**
     * Query the database for a single transaction row (object)
     * @param id The transaction id
     * @return A nullable transaction object
     */
    public Transaction findOne(long id) {
        String sqlQuery = "select " + COLUMN_ID +
            ", " + COLUMN_SENDER +
            ", " + COLUMN_RECIPIENT +
            ", " + COLUMN_TRANSACTION_VALUE +
            " from " + TABLE + " where id = ?" +  // The ? is an argument field which is replace with the id value in the below line
            " and " + COLUMN_SOFT_DELETE + " = false";

        return jdbcTemplate.query(sqlQuery, this::rowToEmployee, id) // Returns a list of transactions
            .stream()
            .findFirst() // Find and get the first transaction in the list
            .orElse(null); // If the list is empty, return null
    }

    /**
     * Query the database for all transactions
     * @return A list of transactions
     */
    public List<Transaction> findAll() {
        String sqlQuery = "select " + COLUMN_ID +
            ", " + COLUMN_SENDER +
            ", " + COLUMN_RECIPIENT +
            ", " + COLUMN_TRANSACTION_VALUE +
            " from " + TABLE + " where " + COLUMN_SOFT_DELETE + " = false";

        return jdbcTemplate.query(sqlQuery, this::rowToEmployee);
    }

    /**
     * Inserts a new transaction into the database
     * @param newTransaction Transaction to add
     * @return An integer value representing if a record has been added
     */
    public int add(TransactionToAdd newTransaction){
        String sqlQuery = "insert into " + TABLE + " (" + COLUMN_SENDER +
            ", " + COLUMN_RECIPIENT +
            ", " + COLUMN_TRANSACTION_VALUE +
            ") values (?, ?, ?)";
        return jdbcTemplate.update(
            sqlQuery,
            newTransaction.sender,
            newTransaction.recipient,
            newTransaction.transactionValue
        );
    }

    /**
     * Update the transaction value
     * @param id The transaction id
     * @param value Transaction value
     * @return  An integer value representing if a record has been updated
     */
    public int updateValue(Long id, BigDecimal value){
        String sqlQuery = "update " + TABLE + " set " +
            COLUMN_TRANSACTION_VALUE + " = ? where id = ?" +
            " and " + COLUMN_SOFT_DELETE + " = false";
        return jdbcTemplate.update(sqlQuery, value, id);
    }

    /**
     * Updates the record soft delete column to true
     * @param id The transaction id
     * @return An integer value representing if a record has been updated
     */
    public int softDelete(Long id){
        String sqlQuery = "update " + TABLE + " set " +
                COLUMN_SOFT_DELETE + " = true where id = ?";
        return jdbcTemplate.update(sqlQuery, id);
    }

    /**
     * Transforms a database row to a Transaction object
     * @param resultSet Table data
     * @param rowNum
     * @return A nullable transaction object
     * @throws SQLException
     */
    private Transaction rowToEmployee(ResultSet resultSet, int rowNum) throws SQLException {
        return new Transaction(
            resultSet.getLong(COLUMN_ID),
            resultSet.getString(COLUMN_SENDER),
            resultSet.getString(COLUMN_RECIPIENT),
            resultSet.getBigDecimal(COLUMN_TRANSACTION_VALUE)
        );
    }
}
