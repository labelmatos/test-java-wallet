package br.com.pay.wallet.service;

import br.com.pay.wallet.dto.ClientDTO;
import br.com.pay.wallet.model.Client;
import br.com.pay.wallet.model.Statement;
import br.com.pay.wallet.model.Wallet;
import br.com.pay.wallet.repository.ClientsRepository;
import br.com.pay.wallet.repository.StatementRepository;
import br.com.pay.wallet.repository.WalletsRepository;
import br.com.pay.wallet.util.ValidUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static br.com.pay.wallet.util.HashUtil.hash;
import static br.com.pay.wallet.util.ValidUtil.isValidCPF;
import static br.com.pay.wallet.util.ValidUtil.isValidPhone;

@Service
public class ClientsService {

    @Autowired
    private AuditService auditService;
    @Autowired
    private ClientsRepository clientsRepository;
    @Autowired
    private WalletsRepository walletsRepository;
    @Autowired
    private StatementRepository statementRepository;

    public void register(ClientDTO dto) throws Exception {
        if (!isValidCPF(dto.document)) {
            throw new IllegalArgumentException("Invalid document.");
        }

        if (!isValidPhone(dto.phone)) {
            throw new IllegalArgumentException("Invalid phone number.");
        }

        clientsRepository.insert(Client.newInstance()
                .setId(dto.document)
                .setFirstName(dto.firstName)
                .setLastName(dto.lastName)
                .setNickName(dto.nickName)
                .setPhone(dto.phone)
                .setPassword(hash(dto.password))
                .setLocation(dto.location)
        );
    }

    public void updatePartial(String document, ClientDTO dto) throws Exception {
        final Client persisted = clientsRepository.findById(document).orElseGet(null);

        if (persisted == null) {
            throw new IllegalArgumentException("Client not found.");
        }
        if (!persisted.getNickName().equals(dto.nickName)) {
            persisted.setNickName(dto.nickName);
        }
        if (!persisted.getPhone().equals(dto.phone)) {
            if (!ValidUtil.isValidPhone(dto.phone)) {
                throw new IllegalArgumentException("Invalid phone number.");
            }
            persisted.setPhone(dto.phone);
        }

        clientsRepository.save(persisted);
        auditService.log(document, "UPDATE_PROFILE", persisted);
    }

    public ClientDTO getClientProfile(String document) {
        final Client client = clientsRepository.findById(document).orElseGet(null);
        if (client == null) {
            throw new IllegalArgumentException("Client not found.");
        }
        return client.toDTO();
    }

    public void softDeleteAccount(String document) throws Exception {
        Client client = clientsRepository.findById(document).orElseGet(null);
        if (client == null) {
            throw new IllegalArgumentException("Client not found.");
        }

        final List<Wallet> wallets = walletsRepository.findByOwnerDocument(document);
        for (Wallet wallet : wallets) {
            final Statement last = statementRepository.findFirstByWalletIdOrderByCreatedAtDesc(wallet.getId());
            if (last != null && last.getFinalValue() > 0) {
                throw new IllegalArgumentException("All wallets needs to be with no value to remove your account.");
            }
        }
        clientsRepository.save(client.setDeleted(true));
        auditService.log(document, "SOFT_DELETE_ACCOUNT", client);
    }
}


