package ru.job4j.cash;

import java.util.HashMap;
import java.util.Optional;

public class AccountStorage {
    private final HashMap<Integer, Account> accounts = new HashMap<>();

    public synchronized boolean add(Account account) {
        return accounts.putIfAbsent(account.id(), account) == null;
    }

    public synchronized boolean update(Account account) {
        return accounts.replace(account.id(), account) != null;
    }

    public synchronized boolean delete(int id) {
        return accounts.remove(id) != null;
    }

    public synchronized Optional<Account> getById(int id) {
        return Optional.ofNullable(accounts.get(id));
    }

    public synchronized boolean transfer(int fromId, int toId, int amount) {
        boolean rsl = false;
        Optional<Account> srcAccount = getById(fromId);
        Optional<Account> destAccount = getById(toId);
        if (srcAccount.isPresent() && destAccount.isPresent()
                && srcAccount.get().amount() >= amount) {
            update(new Account(fromId, srcAccount.get().amount() - amount));
            update(new Account(toId, destAccount.get().amount() + amount));
            rsl = true;
        }
        return rsl;
    }
}

