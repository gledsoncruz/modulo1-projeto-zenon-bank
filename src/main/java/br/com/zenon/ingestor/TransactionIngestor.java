package br.com.zenon.ingestor;

import br.com.zenon.model.Transaction;
import br.com.zenon.model.TransactionCustomer;
import br.com.zenon.model.TransactionTypeEnum;

import java.io.FileInputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class TransactionIngestor {

    public List<Transaction> read(String filename) {
        List<Transaction> transactions = new ArrayList<>();
        try (FileInputStream fis = new FileInputStream(filename)) {
            Scanner s = new Scanner(fis);
            int lineCount = 0;
            while (s.hasNextLine()) {
                String line = s.nextLine();
                lineCount++;

                if (lineCount == 1)
                    continue;

                if (lineCount > 1000)
                    break;


                String[] chunks = line.split(",");
                Transaction transaction = getTransaction(chunks);
                transactions.add(transaction);

            }
        } catch (Exception e) {
            throw new RuntimeException("Erro ao ler o arquivo " + filename, e);
        }

        return transactions;
    }

    private static Transaction getTransaction(String[] chunks) {
        int step = Integer.parseInt(chunks[0]);
        TransactionTypeEnum type = TransactionTypeEnum.valueOf(chunks[1]);
        BigDecimal amount = new BigDecimal(chunks[2]);
        TransactionCustomer transactionCustomerOrigin = new TransactionCustomer(chunks[3], new BigDecimal(chunks[4]), new BigDecimal(chunks[5]));
        TransactionCustomer transactionCustomerRecipient = new TransactionCustomer(chunks[6], new BigDecimal(chunks[7]), new BigDecimal(chunks[8]));
        boolean isFraud =  Boolean.parseBoolean(chunks[9]);
        boolean isFlaggedFraud =  Boolean.parseBoolean(chunks[10]);


        return new Transaction(step, type, amount, transactionCustomerOrigin, transactionCustomerRecipient, isFraud, isFlaggedFraud);
    }


}
