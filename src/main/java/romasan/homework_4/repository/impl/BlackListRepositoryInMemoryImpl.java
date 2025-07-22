package romasan.homework_4.repository.impl;

import org.springframework.stereotype.Repository;
import romasan.homework_4.repository.BlackListRepository;

import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class BlackListRepositoryInMemoryImpl implements BlackListRepository {
    private final Set<String> withdrawnTokens = ConcurrentHashMap.newKeySet();

    @Override
    public boolean addToken(final String token) {
        return this.withdrawnTokens.add(token);
    }

    @Override
    public boolean containsToken(final String token) {
        return this.withdrawnTokens.contains(token);
    }
}
