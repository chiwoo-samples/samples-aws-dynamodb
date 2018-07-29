package org.chiwooplatform.aws.samples.support;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public abstract class Utils {

    public static final String hostname() {
        try {
            Process hostname = Runtime.getRuntime().exec("hostname");
            BufferedReader reader = new BufferedReader(new InputStreamReader(hostname.getInputStream()));
            String s = reader.readLine();
            return s;
        }
        catch (IOException e) {
            return "localhost";
        }
    }

    public static final Integer random(final Integer min, final Integer max) {
        return (int) (Math.floor(Math.random() * (max - min)) + min);
    }

}
