package com.langrsoft.service.library;

import com.langrsoft.domain.Patron;
import com.langrsoft.persistence.PatronStore;

import java.util.Collection;

public class PatronService {
    public final PatronStore patronAccess = new PatronStore();
    private CheckCreditStub checkCredit;

    public PatronService() {
    }

    public String add(String name) {
        return save(new Patron(name));
    }

    public String add(String id, String name) {
        if (!id.startsWith("p")) throw new InvalidPatronIdException();

        return save(new Patron(id, name));
    }

    private String save(Patron newPatron) {
        if (patronAccess.find(newPatron.getId()) != null)
            throw new DuplicatePatronException();
        patronAccess.add(newPatron);
        return newPatron.getId();
    }

    public Patron find(String id) {
        return patronAccess.find(id);
    }

    public Collection<Patron> allPatrons() {
        return patronAccess.getAll();
    }

    public String add(String id, String name, boolean cardNumber) {
        if (cardNumber){
            return add(id,name);
        }
        throw new InvalidCardNumber("Wrong card number");
    }

    public String validateCreditTrue(CheckCredit checkCredit, String pid, String name, String cardNumber) {

        return add(pid, name,  checkCredit.hasCreditValid(cardNumber));
    }

    public void addValidator(CheckCreditStub checkCreditMock) {
        this.checkCredit = checkCreditMock;
    }

    public String add(String pid, String name, String cardNumber) {
        if(checkCredit.hasCreditValid(cardNumber)){
            return add(pid, name);
        }
        return null;
    }
}
