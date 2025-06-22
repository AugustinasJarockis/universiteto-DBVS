package Main;

public class Main {
    public static void main(String[] args) {
        UI _ui = new UI();
        try
        {
            _ui.Begin();
        }
        finally {
            _ui.Close();
        }
    }
}

