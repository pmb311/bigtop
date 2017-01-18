package org.apache.bigtop.itest.hive.unittest;

/**
 * Created by pback on 1/17/17.
 */
import org.apache.bigtop.itest.shell.Shell;
import org.junit.AfterClass;
import org.junit.Test;

import static org.junit.Assert.assertTrue;


public class TestHive {

    private static Shell sh = new Shell("/bin/bash -s");

    @AfterClass
    public static void tearDown() {
        // deletion of test table
        sh.exec("hive -e \"DESCRIBE bigtop_test\"").dumpOutput();
        if (sh.getRet() == 0) {
            sh.exec("hive -e \"DROP TABLE bigtop_test\"").dumpOutput();
            assertTrue("Drop table statement failed",
                    sh.getRet() == 0);
        }
    }

    @Test
    public void testCreateTable() {

        sh.exec("hive -e \"CREATE TABLE bigtop_test (id int, value string)\"").dumpOutput();
        assertTrue("Create table statement errored", sh.getRet() == 0);

        sh.exec("hive -e \"DESCRIBE bigtop_test\"").dumpOutput();
        assertTrue("Describe table statement errored", sh.getRet() == 0);

        sh.exec("hive -e \"SELECT * FROM bigtop_test\"").dumpOutput();
        assertTrue("Select statement errored", sh.getRet() == 0);
    }
}
