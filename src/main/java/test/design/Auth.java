package test.design;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import static org.mockito.ArgumentMatchers.any;

public class Auth {
    class Credentials {
    }

    class Page {
        final boolean autorizationRequired;
        final String body;

        Page(boolean autorizationRequired, String body) {
            this.autorizationRequired = autorizationRequired;
            this.body = body;
        }
    }

    interface AuthService {
        boolean isAuthorized(Credentials credentials);
    }

    class Controller {
        private AuthService auth;

        Controller(AuthService auth) {
            this.auth = auth;
        }

        String getPage(Credentials credentials, Page page) throws IllegalAccessException {
            if (page.autorizationRequired) {
                if (auth.isAuthorized(credentials))
                    return page.body;
                else
                    throw new IllegalAccessException("Unauthorized access");
            } else
                return page.body;
        }
    }

    private @Mock
    AuthService auth;

    @Before
    public void setUp() {
        Mockito.when(auth.isAuthorized(any(Credentials.class))).thenReturn(false);
    }

    @Test(expected = IllegalAccessException.class)
    public void privatePage() throws IllegalAccessException {
        new Controller(auth).getPage(new Credentials(), new Page(true, "<SECRET/>"));
        fail("IllegalAccessException expected");
    }

    @Test
    public void publicPage() throws IllegalAccessException {
        assertEquals("<HTML/>",
                new Controller(auth).getPage(new Credentials(), new Page(false, "<HTML/>")));
    }

}
