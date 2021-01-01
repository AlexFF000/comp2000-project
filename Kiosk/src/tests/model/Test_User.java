package tests.model;
import java.lang.reflect.Field;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.*;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class Test_User {
    // No need to test all setters for each model class, just test User.setUsername()

    @Mock
    UserManager mockUserManager;
    @Test
    public void test_setUsername_saveToFile(){
        Field manager = UserManager.class.getDeclaredField("instance");
        manager.set(null, mockUserManager);
        User user = new User("JSmith", "12345678");
        user.setUsername("JBrown");
        Mockito.verify(mockUserManager).saveToFile();
    }

    @Mock
    UsersController mockController;
    @Test
    public void test_setUsername_notifiesObserversOfUpdate(){
        User user = new User("JSmith", "12345678");
        user.register(mockController);
        user.setUsername("JBrown");
        Mockito.verify(mockController).updateViewUser();
    }

    @Test
    public void test_notifyObserversOfDelete(){
        User user = new User("JSmith", "12345678");
        user.register(mockController);
        user.notifyObserversOfDelete();
        Mockito.verify(mockController).removeViewUser();
    }
}
