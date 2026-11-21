import br.com.zenon.ingestor.TransactionIngestor;
import br.com.zenon.model.Transaction;

import java.util.List;
import java.util.Optional;

public class Main {

    static void main() {

        final String FILE_COMPLETE_DATA_CSV = "data/PS_20174392719_1491204439457_log.csv";
        final String FILE_BAD_DATA_CSV = "data/paysim_with_bad_data.csv";

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
        List<Optional<Transaction>> transactions = transactionIngestor.read(FILE_BAD_DATA_CSV);
        transactions.stream()
                .filter(Optional::isPresent)
                .map(Optional::get)
                .forEach(IO::println);
        IO.println(transactions.size());

    }

}
