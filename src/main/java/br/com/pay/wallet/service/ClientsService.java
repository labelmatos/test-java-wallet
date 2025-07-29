package br.com.pay.wallet.service;

import br.com.pay.wallet.dto.ClientDTO;
import br.com.pay.wallet.model.Client;
import br.com.pay.wallet.model.Statement;
import br.com.pay.wallet.model.Wallet;
import br.com.pay.wallet.repository.ClientsRepository;
import br.com.pay.wallet.repository.StatementRepository;
import br.com.pay.wallet.repository.WalletsRepository;
import br.com.pay.wallet.util.ValidUtil;
import org.bson.Document;
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
    private ClientsRepository clientsRepository;
    private WalletsRepository walletsRepository;
    private StatementRepository statementRepository;

    public void register(ClientDTO dto) throws Exception {
        if (!isValidCPF(dto.document)) {
            throw new IllegalArgumentException("Invalid document.");
        }

        if (!isValidPhone(dto.phone)) {
            throw new IllegalArgumentException("Invalid phone number.");
        }

        if(clientsRepository.find(dto.document) != null) {
            throw new IllegalArgumentException("Document already registered.");
        }

        clientsRepository.create(Client.build()
                        .setFirstName(dto.firstName)
                        .setLastName(dto.lastName)
                        .setNickName(dto.nickName)
                        .setDocument(dto.document)
                        .setPhone(dto.phone)
                        .setPassword(hash(dto.password))
                        .setLocation(dto.location)
        );
    }

    public void updatePartial(String document, ClientDTO dto) {
        final Client persisted = clientsRepository.find(document);
        Document updateFields = new Document();
        if (!persisted.getNickName().equals(dto.nickName)) {
            updateFields.append("nickName", dto.nickName);
        }
        if (!persisted.getPhone().equals(dto.phone)) {
            if (ValidUtil.isValidPhone(dto.phone)) {
                throw new IllegalArgumentException("Invalid phone number.");
            }
            updateFields.append("phone", dto.nickName);
        }

        if (updateFields.isEmpty()) {
            throw new IllegalArgumentException("No information to update.");
        }
        clientsRepository.update(document, updateFields);
        auditService.log(document, "UPDATE_PROFILE", updateFields);
    }

    public ClientDTO getClientProfile(String document) {
        final Client client = clientsRepository.find(document);
        if (client == null) {
            throw new IllegalArgumentException("Cliente não encontrado.");
        }
        return client.toDTO();
    }

    public void softDeleteAccount(String document) {
        final List<Wallet> wallets = walletsRepository.list(document);
        for (Wallet wallet : wallets) {
            final Statement last = statementRepository.findById(wallet.getId());
            if (last != null && last.getFinalValue() > 0) {
                throw new IllegalArgumentException("All wallets needs to be with no value to remove your account.");
            }
        }
        clientsRepository.delete(document);
        auditService.log(document, "SOFT_DELETE_ACCOUNT", new Document("status", "deleted"));
    }
}


