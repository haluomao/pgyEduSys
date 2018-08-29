// Copyright 2018 Baidu Inc. All rights reserved.

package com.pgy.common;

import java.io.File;
import java.util.LinkedList;
import java.util.Queue;

import org.apache.commons.lang.StringUtils;

/**
 * The util for directory.
 *
 * @author Fagui Mao (maofagui@baidu.com)
 */
public class DirUtils {

    public static String findFile(String dir, String fileName, boolean recursive) {
        File file = new File(dir);
        if (file.isDirectory()) {
            Queue<File> queue = new LinkedList<File>();
            queue.offer(file);
            do {
                File[] files = queue.poll().listFiles();
                for (File f : files) {
                    if (f.isDirectory()) {
                        queue.offer(f);
                    } else if (fileName.equals(f.getName())) {
                        return f.getPath();
                    }
                }
            } while (recursive && !queue.isEmpty());

        }
        return StringUtils.EMPTY;
    }

}
