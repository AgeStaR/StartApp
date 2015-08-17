package shirobokov.evgeniy.agestar.startapp.test.rest;

import android.test.AndroidTestCase;

import java.util.List;
import java.util.concurrent.ExecutionException;

import shirobokov.evgeniy.agestar.startapp.models.Tree;
import shirobokov.evgeniy.agestar.startapp.rest.RestRequestTreeTask;

/**
 * Created by Евгений on 17.08.2015.
 */
public class RestRequestTreeTaskTest extends AndroidTestCase {
    private boolean result;

    @Override
    protected void setUp() throws Exception {
        super.setUp();

    }

    public void testTreeTask() {
        whenTreeTaskIsExecuted();
        verifyNoException();
    }

    private void whenTreeTaskIsExecuted() {
        try {
            RestRequestTreeTask requestTask = new RestRequestTreeTask();
            List<Tree> treeList = requestTask.execute().get();
            if (requestTask.getException() != null) {
                throw new Exception(requestTask.getException().getMessage());
            } else {
                this.result = true;
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
            this.result = false;
        } catch (ExecutionException e) {
            e.printStackTrace();
            this.result = false;
        } catch (Exception e) {
            e.printStackTrace();
            this.result = false;
        }
    }

    private void verifyNoException() {
        assertTrue(this.result);
    }

    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
    }
}
