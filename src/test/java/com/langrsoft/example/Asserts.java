package com.langrsoft.example;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.within;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

/**
 * scratch class used as the basis for many of the slide examples.
 */

@ExtendWith(MockitoExtension.class)
class Asserts {
    interface StockLookupSvc {
        int price(String symbol);
    }

    @Test
    void stuff() {
        boolean condition = false;
        int idleSpeed = 1000;
        String text = "something";
        String otherText = "";
        List<String> tokens = new ArrayList<>();
        tokens.add("alpha");
        tokens.add("beta");
        tokens.add("gamma");
        tokens.add("delta");
        class AutoEngine {}

        AutoEngine obj = new AutoEngine();

        assertThat(condition).isFalse();
        assertThat(text).isEqualTo("something");
        assertThat(idleSpeed).isEqualTo(1000);
        assertThat(idleSpeed).isGreaterThan(950).isLessThan(1100);
        assertThat(otherText).isNotEqualTo("something");
        assertThat(otherText).isEmpty();
        assertThat(tokens).hasSize(4);
        assertThat(tokens).contains("beta");
        assertThat(tokens).containsExactly("alpha", "beta", "gamma", "delta");
        assertThat(tokens).containsExactlyInAnyOrder("gamma", "alpha", "beta", "delta");
        assertThat(obj).isInstanceOf(AutoEngine.class);
    }

    class Portfolio {
        private StockLookupSvc service;

        Portfolio(StockLookupSvc service) {
            this.service = service;
        }

        public void purchase(String symbol, int shares) {}

        public int value() {
            return service.price("IBM");
        }
    }

    @Test
    void mockTest() {
        StockLookupSvc service = mock(StockLookupSvc.class);
        org.mockito.Mockito.when(service.price("IBM")).thenReturn(50);

        assertThat(service.price("IBM")).isEqualTo(50);
    }

    class Auditor {
        public void recordEvent(String s) {}
        public void initialize() {}
    }

    public class Scanner {
        private Auditor auditor;

        public Scanner(Auditor auditor) {
            this.auditor = auditor;
        }

        public void scan(String upc) {
            auditor.recordEvent("scanned:" + upc);
        }
    }

    @Test
    void recordsAuditEventWhenScanned() {
        Auditor auditor = mock(Auditor.class);
        Scanner scanner = new Scanner(auditor);

        scanner.scan("123");

        verify(auditor).recordEvent("scanned:123");
    }

    private Auditor auditor = new Auditor();

    public void Add(String word, String definition) {
        auditor.initialize();
        auditor.recordEvent(String.format("adding %s:%s", word, definition));
    }

    @Test
    void recordsAuditEventWhenScannedX() {
        Auditor auditor = mock(Auditor.class);
        Scanner scanner = new Scanner(auditor);

        scanner.scan("123");

        verify(auditor).recordEvent("scanned:123");
    }

    class Item {
        private String description;
        private BigDecimal amount;
        private boolean isDiscountable = true;

        public Item(String description, BigDecimal price) {
            this.description = description;
            this.amount = price;
        }

        public String getDescription() {
            return description;
        }

        public BigDecimal price() {
            return amount;
        }

        public void setNonDiscountable() {
            isDiscountable = false;
        }

        public boolean isDiscountable() {
            return isDiscountable;
        }
    }

    static class DisplayDevice {
        static void appendMessage(String message) {
            throw new RuntimeException("nope");
        }
    }

    class Register {
        private List<Item> purchases = new ArrayList<>();
        private BigDecimal total;
        private BigDecimal memberDiscount = BigDecimal.ZERO;
        private BigDecimal totalOfDiscountedItems;
        private List<String> registerMessages = new ArrayList<>();

        BigDecimal getTotal() {
            return total;
        }

        void setMemberDiscount(BigDecimal memberDiscount) {
            this.memberDiscount = memberDiscount;
        }

        public void completeSale() {
            registerMessages.clear();
            total = BigDecimal.ZERO;
            totalOfDiscountedItems = BigDecimal.ZERO;
            for (Item item : purchases) {
                BigDecimal itemTotal = BigDecimal.ZERO;
                String message = "";
                if (item.isDiscountable()) {
                    BigDecimal discounted = item.price().multiply(BigDecimal.ONE.subtract(memberDiscount));
                    totalOfDiscountedItems = totalOfDiscountedItems.add(discounted);
                    message =
                       "item: " + item.getDescription() +
                          " price: " + new DecimalFormat("#0.00").format(item.price()) +
                          " discounted price: " + new DecimalFormat("#0.00").format(discounted);
                    itemTotal = itemTotal.add(discounted);
                } else {
                    itemTotal = item.price();
                    message =
                       "item: " + item.getDescription() +
                          " price: " + new DecimalFormat("#0.00").format(itemTotal);
                }
                total = total.add(itemTotal);
                appendMessage(message);
                registerMessages.add(message);
            }
        }

        void appendMessage(String message) {
            DisplayDevice.appendMessage(message);
        }

        public void purchase(Item item) {
            purchases.add(item);
        }

        public BigDecimal getTotalOfDiscountedItems() {
            return totalOfDiscountedItems;
        }

        public List<String> getRegisterMessages() {
            return registerMessages;
        }
    }

    @Test
    void includesLineItemWithDiscount() {
        Register register = new Register() {
            @Override
            void appendMessage(String m) {}
        };
        register.setMemberDiscount(new BigDecimal(0.1));
        register.purchase(new Item("milk", new BigDecimal(10)));

        register.completeSale();

        assertThat(register.getRegisterMessages().get(0)).isEqualTo("item: milk price: 10.00 discounted price: 9.00");
    }

    @Test
    void completeSaleAnswersItemsTotal() {
        Register register = new Register() {
            @Override
            void appendMessage(String m) {}
        };
        register.purchase(new Item("milk", new BigDecimal(42)));

        register.completeSale();

        assertThat(register.getTotal()).isEqualTo(new BigDecimal(42));
    }

    static final BigDecimal TOLERANCE = new BigDecimal(0.005);

    @Test
    void completeSaleIncorporatesDiscounts() {
        Register register = new Register() {
            @Override
            void appendMessage(String m) {}
        };
        register.setMemberDiscount(new BigDecimal(0.1));
        register.purchase(new Item("milk", new BigDecimal(5)));
        register.purchase(new Item("cookies", new BigDecimal(5)));

        register.completeSale();

        assertThat(register.getTotal()).isCloseTo(new BigDecimal(9), within(TOLERANCE));
    }

    @Test
    void someItemsNotDiscountable() {
        Register register = new Register() {
            @Override
            void appendMessage(String m) {}
        };
        register.setMemberDiscount(new BigDecimal(0.1));
        register.purchase(new Item("cookies", new BigDecimal(10)));
        Item nonDiscountable = new Item("scrapple", new BigDecimal(10));
        nonDiscountable.setNonDiscountable();
        register.purchase(nonDiscountable);

        register.completeSale();

        assertThat(register.getTotal()).isCloseTo(new BigDecimal(19), within(TOLERANCE));
    }

    @Test
    void answersTotalOfDiscountedItems() {
        Register register = new Register() {
            @Override
            void appendMessage(String m) {}
        };
        register.setMemberDiscount(new BigDecimal(0.1));
        register.purchase(new Item("cookies", new BigDecimal(10)));
        Item nonDiscountable = new Item("scrapple", new BigDecimal(5));
        nonDiscountable.setNonDiscountable();
        register.purchase(nonDiscountable);

        register.completeSale();

        assertThat(register.getTotalOfDiscountedItems()).isCloseTo(new BigDecimal(9), within(TOLERANCE));
    }

    class VerificationService {}

    class Verifier {
        private final VerificationService verificationService;
        private final int timeout;

        public Verifier(int timeout) {
            this(timeout, new VerificationService());
        }

        public Verifier(int timeout, VerificationService verificationService) {
            this.verificationService = verificationService;
            this.timeout = timeout;
        }
    }
}