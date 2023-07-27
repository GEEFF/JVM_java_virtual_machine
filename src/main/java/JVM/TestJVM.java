package JVM;

public class TestJVM {

    public static void main(String[] args) {
        String str = System.getProperty("user.name");
        if (str == null) {
            System.out.println("itcast");
        } else {
            System.out.println(str);
        }
    }
}