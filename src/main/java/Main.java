import br.com.zenon.ingestor.TransactionIngestor;
import br.com.zenon.ingestor.TransactionListRepository;
import br.com.zenon.ingestor.TransactionMapRepository;
import br.com.zenon.ingestor.TransactionRepository;
import br.com.zenon.model.Transaction;

import java.util.List;

public class Main {

    static void main() {

        final String FILE_COMPLETE_DATA_CSV = "data/PS_20174392719_1491204439457_log.csv";
        final String FILE_BAD_DATA_CSV = "data/paysim_with_bad_data.csv";
        final String FILE_TEST_DATA_CSV = "data/paysim_test_data.csv";

//        Transaction t1 = new Transaction(1, TransactionTypeEnum.PAYMENT, new BigDecimal("9839.64"),
//                new TransactionCustomer("C1231006815", new BigDecimal("170136.0"), new BigDecimal("160296.36")),
//                new TransactionCustomer("M1979787155", new BigDecimal("0.0"), new BigDecimal("0.0")), false, false);
//
//        Transaction t2 = new Transaction(743, TransactionTypeEnum.CASH_OUT, new BigDecimal("850002.52"),
//                new TransactionCustomer("C1280323807", new BigDecimal("850002.52"), new BigDecimal("0.0")),
//                new TransactionCustomer("C873221189", new BigDecimal("6510099.11"), new BigDecimal("7360101.63")), true, false);


//        IO.println(t1);
//        IO.println(t2);

//        IO.println("----------------------------------------");

        TransactionIngestor transactionIngestor = new TransactionIngestor();
        List<Transaction> transactions = transactionIngestor.read(FILE_COMPLETE_DATA_CSV);
//        transactions.forEach(IO::println);
//        IO.println(transactions.size());

//        FraudAnalyzer fraudAnalyzer = new FraudAnalyzer(transactions);
//        IO.println("Total frauds: " + fraudAnalyzer.getCountFrauds());
//        IO.println("Top 3 fraudes de maior valor:");
//        fraudAnalyzer.findHighestValueFrauds(3).forEach(amount -> IO.println("- %.2f".formatted(amount)));
//
//        IO.println("Clientes Suspeitos:");
//        fraudAnalyzer.findTopSuspiciousCustomer(5).forEach(IO::println);
//
//        IO.println("Prejuízo Total: " + fraudAnalyzer.totalAmountFrauds());
//
//        IO.println("5. Fraudes por Tipo:");
//        fraudAnalyzer.countFraudByType().forEach((key, value) -> IO.println(" - %s: %d".formatted(key, value)));

        TransactionRepository transactionRepository;
        transactionRepository = new TransactionListRepository(transactions);

        long startTime = System.nanoTime();
        Transaction transactionOrigin = transactionRepository
                .findByOriginName("C1868032458")
                .orElseThrow(() -> new IllegalArgumentException(
                    "Transaction not found for origin"
                ));
        long endTime = System.nanoTime();
        IO.println(transactionOrigin);
        double totalTimeMs = (endTime - startTime) / 1_000_000.0;
        IO.println("Tempo de busca List em ms: " + totalTimeMs);

        transactionRepository = new TransactionMapRepository(transactions);

        startTime = System.nanoTime();
        transactionOrigin = transactionRepository
                .findByOriginName("C1868032458")
                .orElseThrow(() -> new IllegalArgumentException(
                        "Transaction not found for origin"
                ));
        endTime = System.nanoTime();
        IO.println(transactionOrigin);
        totalTimeMs = (endTime - startTime) / 1_000_000.0;
        IO.println("Tempo de busca Map em ms: " + totalTimeMs);



    }

}
