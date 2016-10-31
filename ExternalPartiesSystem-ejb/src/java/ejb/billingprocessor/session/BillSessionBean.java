package ejb.billingprocessor.session;

import ejb.billingprocessor.entity.Bill;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import javax.persistence.NonUniqueResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

@Stateless
public class BillSessionBean implements BillSessionBeanLocal {

    @PersistenceContext(unitName = "ExternalPartiesSystem-ejbPU")
    private EntityManager entityManager;

    @Override
    public Long addNewBill(String customerName, String customerMobile, String billReference,
            String billingOrganizationName, String creditBank, String creditBankAccountNum,
            String debitBank, String debitBankAccountNum, String paymentLimit) {
        Bill bill = new Bill();

        bill.setCustomerName(customerName);
        bill.setCustomerMobile(customerMobile);
        bill.setBillReference(billReference);
        bill.setBillingOrganizationName(billingOrganizationName);
        bill.setDebitBank(debitBank);
        bill.setDebitBankAccountNum(debitBankAccountNum);
        bill.setPaymentLimit(paymentLimit);

        entityManager.persist(bill);
        entityManager.flush();

        return bill.getBillId();
    }

    @Override
    public Bill retrieveBillByBillOrgName(String billingOrganizationName) {
        Bill bill = new Bill();

        try {
            Query query = entityManager.createQuery("Select b From Bill b Where b.billingOrganizationName=:billingOrganizationName");
            query.setParameter("billingOrganizationName", billingOrganizationName);

            if (query.getResultList().isEmpty()) {
                return new Bill();
            } else {
                bill = (Bill) query.getSingleResult();
            }
        } catch (EntityNotFoundException enfe) {
            System.out.println("Entity not found error: " + enfe.getMessage());
            return new Bill();
        } catch (NonUniqueResultException nure) {
            System.out.println("Non unique result error: " + nure.getMessage());
        }

        return bill;
    }

    @Override
    public List<Bill> getAllBill(String billingOrganizationName) {

        Query query = entityManager.createQuery("SELECT b FROM Bill b Where b.billingOrganizationName=:billingOrganizationName");
        query.setParameter("billingOrganizationName", billingOrganizationName);

        return query.getResultList();
    }

    @Override
    public Bill retrieveBillByBillId(Long billId) {
        Bill bill = new Bill();

        try {
            Query query = entityManager.createQuery("Select b From Bill b Where b.billId=:billId");
            query.setParameter("billId", billId);

            if (query.getResultList().isEmpty()) {
                return new Bill();
            } else {
                bill = (Bill) query.getSingleResult();
            }
        } catch (EntityNotFoundException enfe) {
            System.out.println("Entity not found error: " + enfe.getMessage());
            return new Bill();
        } catch (NonUniqueResultException nure) {
            System.out.println("Non unique result error: " + nure.getMessage());
        }

        return bill;
    }

    @Override
    public void updateBillingPayment(Long billId, Double paymentAmt) {

        Bill bill = retrieveBillByBillId(billId);
        bill.setPaymentAmt(paymentAmt.toString());
    }
}
