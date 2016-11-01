/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package managedbean.mas;

import ejb.mas.entity.MEPSMasterAccountTransaction;
import ejb.mas.session.MEPSMasterAccountTransactionSessionBeanLocal;
import java.util.List;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;

/**
 *
 * @author Jingyuan
 */
@Named(value = "visaMasterAccountTransactionManagedBean")
@RequestScoped
public class VisaMasterAccountTransactionManagedBean {

    /**
     * Creates a new instance of VisaMasterAccountTransactionManagedBean
     */
    @EJB
    private MEPSMasterAccountTransactionSessionBeanLocal mEPSMasterAccountTransactionSessionBeanLocal;

    public VisaMasterAccountTransactionManagedBean() {
    }

    public List<MEPSMasterAccountTransaction> getVisaMasterAccountTransactions() {

        List<MEPSMasterAccountTransaction> visaMasterAccountTransactions = mEPSMasterAccountTransactionSessionBeanLocal.retrieveMEPSMasterAccountTransactionByAccNum("20160307");

        return visaMasterAccountTransactions;
    }

}
