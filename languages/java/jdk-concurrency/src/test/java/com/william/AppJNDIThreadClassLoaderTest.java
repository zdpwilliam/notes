package com.william;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * Unit test for simple ThreadTest.
 */
public class AppJNDIThreadClassLoaderTest
    extends TestCase
{
    /**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public AppJNDIThreadClassLoaderTest(String testName )
    {
        super( testName );
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite()
    {
        return new TestSuite( AppJNDIThreadClassLoaderTest.class );
    }

    /**
     * Rigourous JNDIThreadClassLoaderTest :-)
     */
    public void testApp()
    {
        assertTrue( true );
    }
}
