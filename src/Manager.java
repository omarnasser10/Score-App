public interface Manager{
    void add(Object item);
    boolean isExists(int ID);
    boolean search_by_id(int ID);
    void update(int id, String old, String New);
    void delete(int ID);
    String show_by_id(int ID);
}
