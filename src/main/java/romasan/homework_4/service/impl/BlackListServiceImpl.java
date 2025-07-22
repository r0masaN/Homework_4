package romasan.homework_4.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import romasan.homework_4.repository.BlackListRepository;
import romasan.homework_4.service.BlackListService;

@Service
public class BlackListServiceImpl implements BlackListService {
    private final BlackListRepository repository;

    @Autowired
    public BlackListServiceImpl(final BlackListRepository repository) {
        this.repository = repository;
    }

    @Override
    public boolean addToken(final String token) {
        return this.repository.addToken(token);
    }

    @Override
    public boolean containsToken(final String token) {
        return this.repository.containsToken(token);
    }
}
