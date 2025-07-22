package romasan.homework_4.service;

public interface BlackListService {
    boolean addToken(String token);

    boolean containsToken(String token);
}
