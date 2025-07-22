package romasan.homework_4.repository;

public interface BlackListRepository {
    boolean addToken(String token);

    boolean containsToken(String token);
}
