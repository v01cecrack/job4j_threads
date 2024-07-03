package ru.job4j.cash;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;

import java.util.HashMap;
import java.util.Optional;

@ThreadSafe
public class AccountStorage {
    @GuardedBy("this")
    private final HashMap<Integer, Account> accounts = new HashMap<>();

    public synchronized boolean add(Account account) {
        if (accounts.containsKey(account.id())) {
            return false;
        }
        accounts.put(account.id(), account);
        return true;
    }

    public synchronized boolean update(Account account) {
        if (accounts.containsKey(account.id())) {
            return false;
        }
        accounts.put(account.id(), account);
        return true;
    }

    public synchronized void delete(int id) {
        accounts.remove(id);
    }

    public synchronized Optional<Account> getById(int id) {
        return Optional.ofNullable(accounts.get(id));
    }

    public synchronized boolean transfer(int fromId, int toId, int amount) {

        Account fromAccount = accounts.get(fromId);
        Account toAccount = accounts.get(toId);

        if (fromAccount == null || toAccount == null || fromAccount.amount() < amount) {
            return false;
        }
        accounts.put(fromId, new Account(fromId, fromAccount.amount() - amount));
        accounts.put(toId, new Account(toId, toAccount.amount() + amount));
        return true;
    }
}