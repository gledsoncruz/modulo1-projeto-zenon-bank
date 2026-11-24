package br.com.zenon.ingestor;

import br.com.zenon.model.Transaction;
import br.com.zenon.model.TransactionCustomer;
import br.com.zenon.model.TransactionTypeEnum;

import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;

public class TransactionIngestor {

    public static final int TRANSACTION_LIMIT = 50000;

    public List<Transaction> read(String filename) {
        Path path = Paths.get(filename);
        try {
            List<String> lines = Files.readAllLines(path);
            return lines.stream()
                    .skip(1)
                    .limit(TRANSACTION_LIMIT)
                    .map(this::getTransaction)
                    .filter(Optional::isPresent)
                    .map(Optional::get)
                    .toList();

        } catch (Exception e) {
            throw new RuntimeException("Erro ao ler o arquivo " + filename, e);
        }
    }

    private Optional<Transaction> getTransaction(String line) {

        try {
            String[] chunks = line.split(",", -1);

            if (chunks.length != 11) {
                throw new IllegalArgumentException(
                        "Invalid number of fields: " + chunks.length);
            }

            int step = Integer.parseInt(chunks[0]);
            TransactionTypeEnum type = TransactionTypeEnum.valueOf(chunks[1]);
            BigDecimal amount = new BigDecimal(chunks[2]);
            TransactionCustomer transactionCustomerOrigin = new TransactionCustomer(chunks[3], new BigDecimal(chunks[4]), new BigDecimal(chunks[5]));
            TransactionCustomer transactionCustomerRecipient = new TransactionCustomer(chunks[6], new BigDecimal(chunks[7]), new BigDecimal(chunks[8]));
            boolean isFraud = "1".equals(chunks[9].trim());
            boolean isFlaggedFraud =  "1".equals(chunks[10].trim());

            return Optional.of(new Transaction(step, type, amount, transactionCustomerOrigin, transactionCustomerRecipient, isFraud, isFlaggedFraud));

        } catch (IllegalArgumentException e) {
            System.err.printf(
                    "Erro: %s | %s: %s%n",
                    line,
                    e.getClass().getName(),
                    e.getMessage()
            );

            return Optional.empty();
        }
    }


}
